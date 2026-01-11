package com.worldcup2030.dao;

import com.worldcup2030.entity.Booking;
import com.worldcup2030.entity.User;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class BookingDAO extends GenericDAO<Booking> {

    public BookingDAO() {
        super(Booking.class);
    }

    public List<Booking> findByUser(User user) {
        try (Session session = getSession()) {
            return session.createQuery(
                    "FROM Booking b WHERE b.user = :user ORDER BY b.bookingDate DESC", Booking.class)
                    .setParameter("user", user)
                    .list();
        }
    }

    public List<Booking> findByUserId(Long userId) {
        try (Session session = getSession()) {
            return session.createQuery(
                    "FROM Booking b WHERE b.user.id = :userId ORDER BY b.bookingDate DESC", Booking.class)
                    .setParameter("userId", userId)
                    .list();
        }
    }

    public Optional<Booking> findByBookingReference(String bookingReference) {
        try (Session session = getSession()) {
            return session.createQuery(
                    "FROM Booking b WHERE b.bookingReference = :bookingReference", Booking.class)
                    .setParameter("bookingReference", bookingReference)
                    .uniqueResultOptional();
        }
    }

    public List<Booking> findByStatus(Booking.BookingStatus status) {
        try (Session session = getSession()) {
            return session.createQuery(
                    "FROM Booking b WHERE b.status = :status ORDER BY b.bookingDate DESC", Booking.class)
                    .setParameter("status", status)
                    .list();
        }
    }

    public List<Booking> findConfirmedByUser(Long userId) {
        try (Session session = getSession()) {
            return session.createQuery(
                    "FROM Booking b WHERE b.user.id = :userId AND b.status = :status ORDER BY b.bookingDate DESC",
                    Booking.class)
                    .setParameter("userId", userId)
                    .setParameter("status", Booking.BookingStatus.CONFIRMED)
                    .list();
        }
    }
}
