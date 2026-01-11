package com.worldcup2030.service;

import com.worldcup2030.dao.MatchDAO;
import com.worldcup2030.dao.StadiumDAO;
import com.worldcup2030.dao.TicketDAO;
import com.worldcup2030.entity.Match;
import com.worldcup2030.entity.Stadium;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class MatchService {

    private final MatchDAO matchDAO;
    private final StadiumDAO stadiumDAO;
    private final TicketDAO ticketDAO;

    public MatchService() {
        this.matchDAO = new MatchDAO();
        this.stadiumDAO = new StadiumDAO();
        this.ticketDAO = new TicketDAO();
    }

    public Match createMatch(Match match) {
        return matchDAO.save(match);
    }

    public Optional<Match> findById(Long id) {
        return matchDAO.findById(id);
    }

    public List<Match> findAllMatches() {
        return matchDAO.findAll();
    }

    public List<Match> findByStadium(Long stadiumId) {
        return matchDAO.findByStadiumId(stadiumId);
    }

    public List<Match> findByPhase(Match.MatchPhase phase) {
        return matchDAO.findByPhase(phase);
    }

    public List<Match> findUpcomingMatches() {
        return matchDAO.findUpcoming();
    }

    public List<Match> findByDateRange(LocalDateTime start, LocalDateTime end) {
        return matchDAO.findByDateRange(start, end);
    }

    public List<Match> findByTeam(Long teamId) {
        return matchDAO.findByTeamId(teamId);
    }

    public long getAvailableTicketCount(Long matchId) {
        return ticketDAO.countAvailableByMatch(matchId);
    }

    public Match updateMatch(Match match) {
        return matchDAO.update(match);
    }

    public void deleteMatch(Long matchId) {
        matchDAO.deleteById(matchId);
    }

    public List<Stadium> getAllStadiums() {
        return stadiumDAO.findAll();
    }

    public Optional<Stadium> getStadiumById(Long stadiumId) {
        return stadiumDAO.findById(stadiumId);
    }
}
