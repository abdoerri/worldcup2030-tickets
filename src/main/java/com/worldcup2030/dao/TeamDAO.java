package com.worldcup2030.dao;

import com.worldcup2030.entity.Team;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class TeamDAO extends GenericDAO<Team> {

    public TeamDAO() {
        super(Team.class);
    }

    public Optional<Team> findByFifaCode(String fifaCode) {
        try (Session session = getSession()) {
            return session.createQuery(
                    "FROM Team t WHERE t.fifaCode = :fifaCode", Team.class)
                    .setParameter("fifaCode", fifaCode)
                    .uniqueResultOptional();
        }
    }

    public List<Team> findByGroup(String groupName) {
        try (Session session = getSession()) {
            return session.createQuery(
                    "FROM Team t WHERE t.groupName = :groupName ORDER BY t.country", Team.class)
                    .setParameter("groupName", groupName)
                    .list();
        }
    }

    public Optional<Team> findByCountry(String country) {
        try (Session session = getSession()) {
            return session.createQuery(
                    "FROM Team t WHERE t.country = :country", Team.class)
                    .setParameter("country", country)
                    .uniqueResultOptional();
        }
    }
}
