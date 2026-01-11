package com.worldcup2030.api;

import com.worldcup2030.dao.BookingDAO;
import com.worldcup2030.dao.TicketDAO;
import com.worldcup2030.dao.UserDAO;
import com.worldcup2030.entity.Booking;
import com.worldcup2030.entity.Ticket;
import com.worldcup2030.entity.User;
import com.sun.net.httpserver.HttpExchange;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class BookingApi extends ApiHandler {

    private final BookingDAO bookingDAO = new BookingDAO();
    private final TicketDAO ticketDAO = new TicketDAO();
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void handleGet(HttpExchange exchange, String path) throws IOException {
        if (path.startsWith("/api/bookings/user/")) {
            // Get bookings for a user
            Long userId = extractIdFromPath(path, "/api/bookings/user/");
            if (userId != null) {
                List<Booking> bookings = bookingDAO.findByUserId(userId);
                List<Map<String, Object>> result = bookings.stream().map(this::toApiFormat).toList();
                sendJson(exchange, result);
            } else {
                sendError(exchange, 400, "Invalid user ID");
            }
        } else if (path.startsWith("/api/bookings/")) {
            Long id = extractIdFromPath(path, "/api/bookings/");
            if (id != null) {
                bookingDAO.findById(id).ifPresentOrElse(
                        booking -> {
                            try {
                                sendJson(exchange, toApiFormat(booking));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        },
                        () -> {
                            try {
                                sendError(exchange, 404, "Booking not found");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
            } else {
                sendError(exchange, 400, "Invalid booking ID");
            }
        } else if (path.equals("/api/bookings") || path.equals("/api/bookings/")) {
            List<Booking> bookings = bookingDAO.findAll();
            List<Map<String, Object>> result = bookings.stream().map(this::toApiFormat).toList();
            sendJson(exchange, result);
        } else {
            sendError(exchange, 404, "Endpoint not found");
        }
    }

    @Override
    protected void handlePost(HttpExchange exchange, String path) throws IOException {
        if (path.equals("/api/bookings") || path.equals("/api/bookings/")) {
            createBooking(exchange);
        } else {
            sendError(exchange, 404, "Endpoint not found");
        }
    }

    @Override
    protected void handleDelete(HttpExchange exchange, String path) throws IOException {
        if (path.startsWith("/api/bookings/")) {
            Long id = extractIdFromPath(path, "/api/bookings/");
            if (id != null) {
                bookingDAO.findById(id).ifPresentOrElse(
                        booking -> {
                            try {
                                // Mark as cancelled
                                booking.setStatus(Booking.BookingStatus.CANCELLED);
                                // Return tickets to available
                                for (Ticket ticket : booking.getTickets()) {
                                    ticket.setStatus(Ticket.TicketStatus.AVAILABLE);
                                    ticketDAO.update(ticket);
                                }
                                bookingDAO.update(booking);
                                sendSuccess(exchange, "Booking cancelled successfully");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        },
                        () -> {
                            try {
                                sendError(exchange, 404, "Booking not found");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
            } else {
                sendError(exchange, 400, "Invalid booking ID");
            }
        } else {
            sendError(exchange, 404, "Endpoint not found");
        }
    }

    private void createBooking(HttpExchange exchange) throws IOException {
        String body = readRequestBody(exchange);
        System.out.println("📥 Booking request body: " + body);
        JsonObject json = JsonParser.parseString(body).getAsJsonObject();

        Long userId = json.get("userId").getAsLong();
        JsonArray ticketIds = json.getAsJsonArray("ticketIds");
        System.out.println("📋 Creating booking for userId: " + userId + ", tickets: " + ticketIds);

        User user = userDAO.findById(userId).orElse(null);
        System.out.println("👤 User lookup result: " + (user != null ? user.getEmail() : "NULL"));
        if (user == null) {
            System.out.println("❌ User not found in database for userId: " + userId);
            sendError(exchange, 404, "User not found");
            return;
        }

        List<Ticket> tickets = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (int i = 0; i < ticketIds.size(); i++) {
            Long ticketId = ticketIds.get(i).getAsLong();
            Ticket ticket = ticketDAO.findById(ticketId).orElse(null);
            if (ticket == null) {
                sendError(exchange, 404, "Ticket not found: " + ticketId);
                return;
            }
            if (ticket.getStatus() != Ticket.TicketStatus.AVAILABLE) {
                sendError(exchange, 400, "Ticket not available: " + ticketId);
                return;
            }
            tickets.add(ticket);
            totalPrice = totalPrice.add(ticket.getPrice());
        }

        // Create booking
        Booking booking = new Booking(user);
        booking.setTotalPrice(totalPrice);

        // Add tickets to booking (sets status to RESERVED and establishes relationship)
        for (Ticket ticket : tickets) {
            booking.addTicket(ticket);
        }

        // Confirm booking (sets booking status to CONFIRMED and ticket statuses to
        // SOLD)
        booking.confirmBooking();

        // Save booking (will cascade to tickets due to CascadeType.ALL)
        bookingDAO.save(booking);

        // Also explicitly update each ticket to ensure status is persisted
        for (Ticket ticket : tickets) {
            ticketDAO.update(ticket);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("booking", toApiFormat(booking));
        sendJson(exchange, response);
    }

    private Map<String, Object> toApiFormat(Booking booking) {
        Map<String, Object> data = new HashMap<>();
        data.put("id", booking.getId());
        data.put("reference", booking.getBookingReference());
        data.put("userId", booking.getUser().getId());
        data.put("totalPrice", booking.getTotalPrice());
        data.put("status", booking.getStatus().name());
        data.put("bookingDate", booking.getBookingDate().toString());

        // Tickets
        List<Map<String, Object>> ticketList = new ArrayList<>();
        for (Ticket ticket : booking.getTickets()) {
            Map<String, Object> ticketData = new HashMap<>();
            ticketData.put("id", ticket.getId());
            ticketData.put("matchId", ticket.getMatch().getId());
            ticketData.put("section", ticket.getSection());
            ticketData.put("row", ticket.getSeatRow());
            ticketData.put("seatNumber", ticket.getSeatNumber());
            ticketData.put("category", ticket.getCategory().name());
            ticketData.put("categoryCode", ticket.getCategory().getCode());
            ticketData.put("price", ticket.getPrice());

            // Match info
            Map<String, Object> matchInfo = new HashMap<>();
            matchInfo.put("id", ticket.getMatch().getId());
            if (ticket.getMatch().getHomeTeam() != null) {
                matchInfo.put("homeTeam", ticket.getMatch().getHomeTeam().getFifaCode());
                matchInfo.put("homeFlag", ticket.getMatch().getHomeTeam().getFlagEmoji());
            }
            if (ticket.getMatch().getAwayTeam() != null) {
                matchInfo.put("awayTeam", ticket.getMatch().getAwayTeam().getFifaCode());
                matchInfo.put("awayFlag", ticket.getMatch().getAwayTeam().getFlagEmoji());
            }
            matchInfo.put("stadium", ticket.getMatch().getStadium().getCity());
            matchInfo.put("date", ticket.getMatch().getMatchDate().toString());
            ticketData.put("match", matchInfo);

            ticketList.add(ticketData);
        }
        data.put("tickets", ticketList);

        return data;
    }
}
