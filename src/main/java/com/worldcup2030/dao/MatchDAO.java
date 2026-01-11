package com.worldcup2030.dao;

import com.worldcup2030.entity.Match;
import com.worldcup2030.entity.Stadium;
import org.hibernate.Session;

import java.time.LocalDateTime;
import java.util.List;

public class MatchDAO extends GenericDAO<Match> {

    public MatchDAO() {
        super(Match.class);
    }

    public List<Match> findByStadium(Stadium stadium) {
        try (Session session = getSession()) {
            return session.createQuery(
                    "FROM Match m WHERE m.stadium = :stadium ORDER BY m.matchDate", Match.class)
                    .setParameter("stadium", stadium)
                    .list();
        }
    }

    public List<Match> findByStadiumId(Long stadiumId) {
        try (Session session = getSession()) {
            return session.createQuery(
                    "FROM Match m WHERE m.stadium.id = :stadiumId ORDER BY m.matchDate", Match.class)
                    .setParameter("stadiumId", stadiumId)
                    .list();
        }
    }

    public List<Match> findByPhase(Match.MatchPhase phase) {
        try (Session session = getSession()) {
            return session.createQuery(
                    "FROM Match m WHERE m.matchPhase = :phase ORDER BY m.matchDate", Match.class)
                    .setParameter("phase", phase)
                    .list();
        }
    }

    public List<Match> findByDateRange(LocalDateTime start, LocalDateTime end) {
        try (Session session = getSession()) {
            return session.createQuery(
                    "FROM Match m WHERE m.matchDate BETWEEN :start AND :end ORDER BY m.matchDate",
                    Match.class)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .list();
        }
    }

    public List<Match> findUpcoming() {
        try (Session session = getSession()) {
            return session.createQuery(
                    "FROM Match m WHERE m.matchDate > :now ORDER BY m.matchDate", Match.class)
                    .setParameter("now", LocalDateTime.now())
                    .list();
        }
    }

    public List<Match> findByTeamId(Long teamId) {
        try (Session session = getSession()) {
            return session.createQuery(
                    "FROM Match m WHERE m.homeTeam.id = :teamId OR m.awayTeam.id = :teamId ORDER BY m.matchDate",
                    Match.class)
                    .setParameter("teamId", teamId)
                    .list();
        }
    }

    @Override
    public List<Match> findAll() {
        try (Session session = getSession()) {
            return session.createQuery(
                    "FROM Match m ORDER BY m.matchDate", Match.class)
                    .list();
        }
    }
}
