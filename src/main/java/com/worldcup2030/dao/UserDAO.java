package com.worldcup2030.dao;

import com.worldcup2030.entity.User;
import org.hibernate.Session;

import java.util.Optional;

public class UserDAO extends GenericDAO<User> {

    public UserDAO() {
        super(User.class);
    }

    public Optional<User> findByEmail(String email) {
        try (Session session = getSession()) {
            return session.createQuery(
                    "FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .uniqueResultOptional();
        }
    }

    public Optional<User> findByEmailAndPassword(String email, String password) {
        try (Session session = getSession()) {
            return session.createQuery(
                    "FROM User u WHERE u.email = :email AND u.password = :password", User.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .uniqueResultOptional();
        }
    }

    public boolean existsByEmail(String email) {
        try (Session session = getSession()) {
            Long count = session.createQuery(
                    "SELECT COUNT(u) FROM User u WHERE u.email = :email", Long.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return count > 0;
        }
    }
}
