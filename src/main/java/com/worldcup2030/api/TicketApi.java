package com.worldcup2030.api;

import com.worldcup2030.dao.TicketDAO;
import com.worldcup2030.entity.Ticket;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class TicketApi extends ApiHandler {

    private final TicketDAO ticketDAO = new TicketDAO();

    @Override
    protected void handleGet(HttpExchange exchange, String path) throws IOException {
        if (path.startsWith("/api/tickets/match/")) {
            // Get tickets for a match
            Long matchId = extractIdFromPath(path, "/api/tickets/match/");
            if (matchId != null) {
                List<Ticket> tickets = ticketDAO.findAvailableByMatchId(matchId);
                List<Map<String, Object>> result = tickets.stream().map(this::toApiFormat).toList();
                sendJson(exchange, result);
            } else {
                sendError(exchange, 400, "Invalid match ID");
            }
        } else if (path.equals("/api/tickets") || path.equals("/api/tickets/")) {
            List<Ticket> tickets = ticketDAO.findAll();
            List<Map<String, Object>> result = tickets.stream().map(this::toApiFormat).toList();
            sendJson(exchange, result);
        } else {
            sendError(exchange, 404, "Endpoint not found");
        }
    }

    @Override
    protected void handlePost(HttpExchange exchange, String path) throws IOException {
        if (path.startsWith("/api/tickets/") && path.endsWith("/reserve")) {
            // Reserve a ticket
            String idPart = path.replace("/api/tickets/", "").replace("/reserve", "");
            try {
                Long ticketId = Long.parseLong(idPart);
                ticketDAO.findById(ticketId).ifPresentOrElse(
                        ticket -> {
                            try {
                                if (ticket.getStatus() == Ticket.TicketStatus.AVAILABLE) {
                                    ticket.setStatus(Ticket.TicketStatus.RESERVED);
                                    ticketDAO.update(ticket);
                                    sendSuccess(exchange, "Ticket reserved successfully");
                                } else {
                                    sendError(exchange, 400, "Ticket is not available");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        },
                        () -> {
                            try {
                                sendError(exchange, 404, "Ticket not found");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
            } catch (NumberFormatException e) {
                sendError(exchange, 400, "Invalid ticket ID");
            }
        } else {
            sendError(exchange, 404, "Endpoint not found");
        }
    }

    private Map<String, Object> toApiFormat(Ticket ticket) {
        Map<String, Object> data = new HashMap<>();
        data.put("id", ticket.getId());
        data.put("matchId", ticket.getMatch().getId());
        data.put("section", ticket.getSection());
        data.put("row", ticket.getSeatRow());
        data.put("seatNumber", ticket.getSeatNumber());
        data.put("category", ticket.getCategory().name());
        data.put("categoryCode", ticket.getCategory().getCode());
        data.put("price", ticket.getPrice());
        data.put("status", ticket.getStatus().name());
        return data;
    }
}
