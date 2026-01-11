package com.worldcup2030.api;

import com.worldcup2030.dao.TeamDAO;
import com.worldcup2030.entity.Team;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.List;

public class TeamApi extends ApiHandler {

    private final TeamDAO teamDAO = new TeamDAO();

    @Override
    protected void handleGet(HttpExchange exchange, String path) throws IOException {
        if (path.equals("/api/teams") || path.equals("/api/teams/")) {
            // Get all teams
            List<Team> teams = teamDAO.findAll();
            sendJson(exchange, teams);
        } else if (path.startsWith("/api/teams/")) {
            // Get team by ID
            Long id = extractIdFromPath(path, "/api/teams/");
            if (id != null) {
                teamDAO.findById(id).ifPresentOrElse(
                        team -> {
                            try {
                                sendJson(exchange, team);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        },
                        () -> {
                            try {
                                sendError(exchange, 404, "Team not found");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
            } else {
                sendError(exchange, 400, "Invalid team ID");
            }
        } else {
            sendError(exchange, 404, "Endpoint not found");
        }
    }
}
