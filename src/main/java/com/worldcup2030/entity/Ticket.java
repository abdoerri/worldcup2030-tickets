package com.worldcup2030.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tickets")
public class Ticket {

    public enum TicketCategory {
        CATEGORY_1("CAT1", "Premium - Best View"),
        CATEGORY_2("CAT2", "Standard - Good View"),
        CATEGORY_3("CAT3", "Economy - Behind Goals");

        private final String code;
        private final String description;

        TicketCategory(String code, String description) {
            this.code = code;
            this.description = description;
        }

        public String getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum TicketStatus {
        AVAILABLE,
        RESERVED,
        SOLD,
        CANCELLED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    @Column(name = "seat_number", nullable = false, length = 20)
    private String seatNumber;

    @Column(name = "seat_row", length = 10)
    private String seatRow;

    @Column(name = "section", length = 20)
    private String section;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketCategory category;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status = TicketStatus.AVAILABLE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    public Ticket() {
    }

    public Ticket(Match match, String seatNumber, TicketCategory category, BigDecimal price) {
        this.match = match;
        this.seatNumber = seatNumber;
        this.category = category;
        this.price = price;
        this.status = TicketStatus.AVAILABLE;
    }

    public Ticket(Match match, String section, String seatRow, String seatNumber,
            TicketCategory category, BigDecimal price) {
        this.match = match;
        this.section = section;
        this.seatRow = seatRow;
        this.seatNumber = seatNumber;
        this.category = category;
        this.price = price;
        this.status = TicketStatus.AVAILABLE;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(String seatRow) {
        this.seatRow = seatRow;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public TicketCategory getCategory() {
        return category;
    }

    public void setCategory(TicketCategory category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public String getFullSeatLocation() {
        StringBuilder sb = new StringBuilder();
        if (section != null)
            sb.append("Section ").append(section).append(" - ");
        if (seatRow != null)
            sb.append("Row ").append(seatRow).append(" - ");
        sb.append("Seat ").append(seatNumber);
        return sb.toString();
    }

    @Override
    public String toString() {
        return String.format("🎟️ Ticket #%d | %s | %s | $%.2f | %s",
                id, getFullSeatLocation(), category.getCode(), price, status);
    }
}
