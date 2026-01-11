package com.worldcup2030.api;

import com.worldcup2030.dao.MatchDAO;
import com.worldcup2030.entity.Match;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class MatchApi extends ApiHandler {

    private final MatchDAO matchDAO = new MatchDAO();

    @Override
    protected void handleGet(HttpExchange exchange, String path) throws IOException {
        if (path.equals("/api/matches") || path.equals("/api/matches/")) {
            List<Match> matches = matchDAO.findAll();
            // Convert to API-friendly format
            List<Map<String, Object>> result = matches.stream().map(this::toApiFormat).toList();
            sendJson(exchange, result);
        } else if (path.startsWith("/api/matches/")) {
            Long id = extractIdFromPath(path, "/api/matches/");
            if (id != null) {
                matchDAO.findById(id).ifPresentOrElse(
                        match -> {
                            try {
                                sendJson(exchange, toApiFormat(match));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        },
                        () -> {
                            try {
                                sendError(exchange, 404, "Match not found");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
            } else {
                sendError(exchange, 400, "Invalid match ID");
            }
        } else {
            sendError(exchange, 404, "Endpoint not found");
        }
    }

    private Map<String, Object> toApiFormat(Match match) {
        Map<String, Object> data = new HashMap<>();
        data.put("id", match.getId());
        data.put("matchNumber", match.getMatchNumber());
        data.put("matchPhase", match.getMatchPhase().name());
        data.put("matchDate", match.getMatchDate().toString());

        // Stadium info
        Map<String, Object> stadiumInfo = new HashMap<>();
        stadiumInfo.put("id", match.getStadium().getId());
        stadiumInfo.put("name", match.getStadium().getName());
        stadiumInfo.put("city", match.getStadium().getCity());
        stadiumInfo.put("capacity", match.getStadium().getCapacity());
        data.put("stadium", stadiumInfo);

        // Home team
        if (match.getHomeTeam() != null) {
            Map<String, Object> homeTeam = new HashMap<>();
            homeTeam.put("id", match.getHomeTeam().getId());
            homeTeam.put("country", match.getHomeTeam().getCountry());
            homeTeam.put("fifaCode", match.getHomeTeam().getFifaCode());
            homeTeam.put("flagEmoji", match.getHomeTeam().getFlagEmoji());
            homeTeam.put("groupName", match.getHomeTeam().getGroupName());
            data.put("homeTeam", homeTeam);
        }

        // Away team
        if (match.getAwayTeam() != null) {
            Map<String, Object> awayTeam = new HashMap<>();
            awayTeam.put("id", match.getAwayTeam().getId());
            awayTeam.put("country", match.getAwayTeam().getCountry());
            awayTeam.put("fifaCode", match.getAwayTeam().getFifaCode());
            awayTeam.put("flagEmoji", match.getAwayTeam().getFlagEmoji());
            awayTeam.put("groupName", match.getAwayTeam().getGroupName());
            data.put("awayTeam", awayTeam);
        }

        return data;
    }
}
