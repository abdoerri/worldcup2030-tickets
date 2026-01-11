package com.worldcup2030.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bookings")
public class Booking {

    public enum BookingStatus {
        PENDING,
        CONFIRMED,
        CANCELLED,
        COMPLETED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booking_reference", unique = true, nullable = false, length = 20)
    private String bookingReference;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Ticket> tickets = new ArrayList<>();

    @Column(name = "booking_date", nullable = false)
    private LocalDateTime bookingDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status = BookingStatus.PENDING;

    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice = BigDecimal.ZERO;

    @Column(name = "payment_method", length = 50)
    private String paymentMethod;

    @Column(name = "notes", length = 500)
    private String notes;

    public Booking() {
        this.bookingDate = LocalDateTime.now();
        this.bookingReference = generateBookingReference();
    }

    public Booking(User user) {
        this.user = user;
        this.bookingDate = LocalDateTime.now();
        this.bookingReference = generateBookingReference();
        this.status = BookingStatus.PENDING;
    }

    private String generateBookingReference() {
        return "WC2030-" + System.currentTimeMillis() % 1000000;
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
        ticket.setBooking(this);
        ticket.setStatus(Ticket.TicketStatus.RESERVED);
        calculateTotalPrice();
    }

    public void removeTicket(Ticket ticket) {
        tickets.remove(ticket);
        ticket.setBooking(null);
        ticket.setStatus(Ticket.TicketStatus.AVAILABLE);
        calculateTotalPrice();
    }

    public void calculateTotalPrice() {
        this.totalPrice = tickets.stream()
                .map(Ticket::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void confirmBooking() {
        this.status = BookingStatus.CONFIRMED;
        for (Ticket ticket : tickets) {
            ticket.setStatus(Ticket.TicketStatus.SOLD);
        }
    }

    public void cancelBooking() {
        this.status = BookingStatus.CANCELLED;
        for (Ticket ticket : tickets) {
            ticket.setStatus(Ticket.TicketStatus.AVAILABLE);
            ticket.setBooking(null);
        }
        tickets.clear();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return String.format("📋 Booking %s | %s | %d tickets | $%.2f | %s",
                bookingReference, user.getFullName(), tickets.size(), totalPrice, status);
    }
}
