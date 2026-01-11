package com.worldcup2030.dao;

import com.worldcup2030.entity.Stadium;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class StadiumDAO extends GenericDAO<Stadium> {

    public StadiumDAO() {
        super(Stadium.class);
    }

    public Optional<Stadium> findByName(String name) {
        try (Session session = getSession()) {
            return session.createQuery(
                    "FROM Stadium s WHERE s.name = :name", Stadium.class)
                    .setParameter("name", name)
                    .uniqueResultOptional();
        }
    }

    public List<Stadium> findByCity(String city) {
        try (Session session = getSession()) {
            return session.createQuery(
                    "FROM Stadium s WHERE s.city = :city", Stadium.class)
                    .setParameter("city", city)
                    .list();
        }
    }

    public List<Stadium> findByMinCapacity(int minCapacity) {
        try (Session session = getSession()) {
            return session.createQuery(
                    "FROM Stadium s WHERE s.capacity >= :minCapacity ORDER BY s.capacity DESC",
                    Stadium.class)
                    .setParameter("minCapacity", minCapacity)
                    .list();
        }
    }
}
