package com.worldcup2030.service;

import com.worldcup2030.dao.MatchDAO;
import com.worldcup2030.dao.TicketDAO;
import com.worldcup2030.entity.Match;
import com.worldcup2030.entity.Ticket;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class TicketService {

    private final TicketDAO ticketDAO;
    private final MatchDAO matchDAO;

    public TicketService() {
        this.ticketDAO = new TicketDAO();
        this.matchDAO = new MatchDAO();
    }

    public Ticket createTicket(Ticket ticket) {
        return ticketDAO.save(ticket);
    }

    public List<Ticket> createTickets(List<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            ticketDAO.save(ticket);
        }
        return tickets;
    }

    public Optional<Ticket> findById(Long id) {
        return ticketDAO.findById(id);
    }

    public List<Ticket> findAllTickets() {
        return ticketDAO.findAll();
    }

    public List<Ticket> findTicketsByMatch(Long matchId) {
        return ticketDAO.findByMatchId(matchId);
    }

    public List<Ticket> findAvailableTickets(Long matchId) {
        return ticketDAO.findAvailableByMatchId(matchId);
    }

    public List<Ticket> findAvailableByCategory(Long matchId, Ticket.TicketCategory category) {
        return ticketDAO.findAvailableByMatchAndCategory(matchId, category);
    }

    public List<Ticket> findAvailableByPriceRange(Long matchId, BigDecimal minPrice, BigDecimal maxPrice) {
        return ticketDAO.findAvailableByPriceRange(matchId, minPrice, maxPrice);
    }

    public long countAvailableTickets(Long matchId) {
        return ticketDAO.countAvailableByMatch(matchId);
    }

    public Ticket reserveTicket(Long ticketId) {
        Optional<Ticket> ticketOpt = ticketDAO.findById(ticketId);
        if (ticketOpt.isPresent()) {
            Ticket ticket = ticketOpt.get();
            if (ticket.getStatus() == Ticket.TicketStatus.AVAILABLE) {
                ticket.setStatus(Ticket.TicketStatus.RESERVED);
                return ticketDAO.update(ticket);
            }
            throw new IllegalStateException("Ticket is not available for reservation");
        }
        throw new IllegalArgumentException("Ticket not found with ID: " + ticketId);
    }

    public Ticket releaseTicket(Long ticketId) {
        Optional<Ticket> ticketOpt = ticketDAO.findById(ticketId);
        if (ticketOpt.isPresent()) {
            Ticket ticket = ticketOpt.get();
            if (ticket.getStatus() == Ticket.TicketStatus.RESERVED) {
                ticket.setStatus(Ticket.TicketStatus.AVAILABLE);
                ticket.setBooking(null);
                return ticketDAO.update(ticket);
            }
            throw new IllegalStateException("Ticket is not reserved");
        }
        throw new IllegalArgumentException("Ticket not found with ID: " + ticketId);
    }

    public Ticket updateTicket(Ticket ticket) {
        return ticketDAO.update(ticket);
    }

    public void deleteTicket(Long ticketId) {
        ticketDAO.deleteById(ticketId);
    }

    public void generateTicketsForMatch(Match match, int countPerCategory) {
        String[] sections = { "A", "B", "C", "D" };

        // Category 1 - Premium ($500)
        for (int i = 1; i <= countPerCategory; i++) {
            Ticket ticket = new Ticket(
                    match,
                    sections[i % 4],
                    String.valueOf((i / 10) + 1),
                    String.valueOf(i),
                    Ticket.TicketCategory.CATEGORY_1,
                    new BigDecimal("500.00"));
            ticketDAO.save(ticket);
        }

        // Category 2 - Standard ($300)
        for (int i = 1; i <= countPerCategory; i++) {
            Ticket ticket = new Ticket(
                    match,
                    sections[i % 4],
                    String.valueOf((i / 10) + 5),
                    String.valueOf(i),
                    Ticket.TicketCategory.CATEGORY_2,
                    new BigDecimal("300.00"));
            ticketDAO.save(ticket);
        }

        // Category 3 - Economy ($150)
        for (int i = 1; i <= countPerCategory; i++) {
            Ticket ticket = new Ticket(
                    match,
                    sections[i % 4],
                    String.valueOf((i / 10) + 10),
                    String.valueOf(i),
                    Ticket.TicketCategory.CATEGORY_3,
                    new BigDecimal("150.00"));
            ticketDAO.save(ticket);
        }
    }
}
