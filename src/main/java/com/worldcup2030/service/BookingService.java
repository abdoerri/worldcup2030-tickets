package com.worldcup2030.service;

import com.worldcup2030.dao.BookingDAO;
import com.worldcup2030.dao.TicketDAO;
import com.worldcup2030.dao.UserDAO;
import com.worldcup2030.entity.Booking;
import com.worldcup2030.entity.Ticket;
import com.worldcup2030.entity.User;

import java.util.List;
import java.util.Optional;

public class BookingService {

    private final BookingDAO bookingDAO;
    private final TicketDAO ticketDAO;
    private final UserDAO userDAO;

    public BookingService() {
        this.bookingDAO = new BookingDAO();
        this.ticketDAO = new TicketDAO();
        this.userDAO = new UserDAO();
    }

    public Booking createBooking(User user, List<Long> ticketIds) {
        // Validate user
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("Valid user is required");
        }

        // Validate tickets
        if (ticketIds == null || ticketIds.isEmpty()) {
            throw new IllegalArgumentException("At least one ticket is required");
        }

        // Create booking
        Booking booking = new Booking(user);

        // Add tickets to booking
        for (Long ticketId : ticketIds) {
            Optional<Ticket> ticketOpt = ticketDAO.findById(ticketId);
            if (ticketOpt.isEmpty()) {
                throw new IllegalArgumentException("Ticket not found with ID: " + ticketId);
            }

            Ticket ticket = ticketOpt.get();
            if (ticket.getStatus() != Ticket.TicketStatus.AVAILABLE) {
                throw new IllegalStateException("Ticket " + ticketId + " is not available");
            }

            booking.addTicket(ticket);
        }

        // Save booking
        Booking savedBooking = bookingDAO.save(booking);

        // Update tickets
        for (Ticket ticket : booking.getTickets()) {
            ticketDAO.update(ticket);
        }

        return savedBooking;
    }

    public Booking confirmBooking(Long bookingId) {
        Optional<Booking> bookingOpt = bookingDAO.findById(bookingId);
        if (bookingOpt.isEmpty()) {
            throw new IllegalArgumentException("Booking not found with ID: " + bookingId);
        }

        Booking booking = bookingOpt.get();
        if (booking.getStatus() != Booking.BookingStatus.PENDING) {
            throw new IllegalStateException("Booking is not in PENDING status");
        }

        booking.confirmBooking();
        Booking updatedBooking = bookingDAO.update(booking);

        // Update tickets to SOLD status
        for (Ticket ticket : booking.getTickets()) {
            ticketDAO.update(ticket);
        }

        return updatedBooking;
    }

    public Booking cancelBooking(Long bookingId) {
        Optional<Booking> bookingOpt = bookingDAO.findById(bookingId);
        if (bookingOpt.isEmpty()) {
            throw new IllegalArgumentException("Booking not found with ID: " + bookingId);
        }

        Booking booking = bookingOpt.get();
        if (booking.getStatus() == Booking.BookingStatus.CANCELLED) {
            throw new IllegalStateException("Booking is already cancelled");
        }

        // Release tickets before cancelling
        for (Ticket ticket : booking.getTickets()) {
            ticket.setStatus(Ticket.TicketStatus.AVAILABLE);
            ticket.setBooking(null);
            ticketDAO.update(ticket);
        }

        booking.setStatus(Booking.BookingStatus.CANCELLED);
        booking.getTickets().clear();

        return bookingDAO.update(booking);
    }

    public Optional<Booking> findById(Long id) {
        return bookingDAO.findById(id);
    }

    public Optional<Booking> findByReference(String bookingReference) {
        return bookingDAO.findByBookingReference(bookingReference);
    }

    public List<Booking> findByUser(Long userId) {
        return bookingDAO.findByUserId(userId);
    }

    public List<Booking> findConfirmedByUser(Long userId) {
        return bookingDAO.findConfirmedByUser(userId);
    }

    public List<Booking> findByStatus(Booking.BookingStatus status) {
        return bookingDAO.findByStatus(status);
    }

    public List<Booking> findAllBookings() {
        return bookingDAO.findAll();
    }
}
