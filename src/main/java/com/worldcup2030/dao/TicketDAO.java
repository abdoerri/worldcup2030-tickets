package com.worldcup2030.dao;

import com.worldcup2030.entity.Match;
import com.worldcup2030.entity.Ticket;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.List;

public class TicketDAO extends GenericDAO<Ticket> {

    public TicketDAO() {
        super(Ticket.class);
    }

    public List<Ticket> findByMatch(Match match) {
        try (Session session = getSession()) {
            return session.createQuery(
                    "FROM Ticket t WHERE t.match = :match ORDER BY t.category, t.seatNumber",
                    Ticket.class)
                    .setParameter("match", match)
                    .list();
        }
    }

    public List<Ticket> findByMatchId(Long matchId) {
        try (Session session = getSession()) {
            return session.createQuery(
                    "FROM Ticket t WHERE t.match.id = :matchId ORDER BY t.category, t.seatNumber",
                    Ticket.class)
                    .setParameter("matchId", matchId)
                    .list();
        }
    }

    public List<Ticket> findAvailableByMatch(Match match) {
        try (Session session = getSession()) {
            return session.createQuery(
                    "FROM Ticket t WHERE t.match = :match AND t.status = :status ORDER BY t.category, t.price",
                    Ticket.class)
                    .setParameter("match", match)
                    .setParameter("status", Ticket.TicketStatus.AVAILABLE)
                    .list();
        }
    }

    public List<Ticket> findAvailableByMatchId(Long matchId) {
        try (Session session = getSession()) {
            return session.createQuery(
                    "FROM Ticket t WHERE t.match.id = :matchId AND t.status = :status ORDER BY t.category, t.price",
                    Ticket.class)
                    .setParameter("matchId", matchId)
                    .setParameter("status", Ticket.TicketStatus.AVAILABLE)
                    .list();
        }
    }

    public List<Ticket> findAvailableByMatchAndCategory(Long matchId, Ticket.TicketCategory category) {
        try (Session session = getSession()) {
            return session.createQuery(
                    "FROM Ticket t WHERE t.match.id = :matchId AND t.status = :status AND t.category = :category ORDER BY t.price",
                    Ticket.class)
                    .setParameter("matchId", matchId)
                    .setParameter("status", Ticket.TicketStatus.AVAILABLE)
                    .setParameter("category", category)
                    .list();
        }
    }

    public List<Ticket> findAvailableByPriceRange(Long matchId, BigDecimal minPrice, BigDecimal maxPrice) {
        try (Session session = getSession()) {
            return session.createQuery(
                    "FROM Ticket t WHERE t.match.id = :matchId AND t.status = :status " +
                            "AND t.price BETWEEN :minPrice AND :maxPrice ORDER BY t.price",
                    Ticket.class)
                    .setParameter("matchId", matchId)
                    .setParameter("status", Ticket.TicketStatus.AVAILABLE)
                    .setParameter("minPrice", minPrice)
                    .setParameter("maxPrice", maxPrice)
                    .list();
        }
    }

    public long countAvailableByMatch(Long matchId) {
        try (Session session = getSession()) {
            return session.createQuery(
                    "SELECT COUNT(t) FROM Ticket t WHERE t.match.id = :matchId AND t.status = :status",
                    Long.class)
                    .setParameter("matchId", matchId)
                    .setParameter("status", Ticket.TicketStatus.AVAILABLE)
                    .getSingleResult();
        }
    }

    public List<Ticket> findByStatus(Ticket.TicketStatus status) {
        try (Session session = getSession()) {
            return session.createQuery(
                    "FROM Ticket t WHERE t.status = :status", Ticket.class)
                    .setParameter("status", status)
                    .list();
        }
    }
}
