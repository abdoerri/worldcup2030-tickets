package com.worldcup2030.api;

import com.worldcup2030.dao.StadiumDAO;
import com.worldcup2030.entity.Stadium;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.List;

public class StadiumApi extends ApiHandler {

    private final StadiumDAO stadiumDAO = new StadiumDAO();

    @Override
    protected void handleGet(HttpExchange exchange, String path) throws IOException {
        if (path.equals("/api/stadiums") || path.equals("/api/stadiums/")) {
            List<Stadium> stadiums = stadiumDAO.findAll();
            sendJson(exchange, stadiums);
        } else if (path.startsWith("/api/stadiums/")) {
            Long id = extractIdFromPath(path, "/api/stadiums/");
            if (id != null) {
                stadiumDAO.findById(id).ifPresentOrElse(
                        stadium -> {
                            try {
                                sendJson(exchange, stadium);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        },
                        () -> {
                            try {
                                sendError(exchange, 404, "Stadium not found");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
            } else {
                sendError(exchange, 400, "Invalid stadium ID");
            }
        } else {
            sendError(exchange, 404, "Endpoint not found");
        }
    }
}
