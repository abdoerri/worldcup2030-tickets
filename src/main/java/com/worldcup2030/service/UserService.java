package com.worldcup2030.service;

import com.worldcup2030.dao.UserDAO;
import com.worldcup2030.entity.User;

import java.util.List;
import java.util.Optional;

public class UserService {

    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public User register(String firstName, String lastName, String email, String password) {
        // Check if email already exists
        if (userDAO.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already registered: " + email);
        }

        User user = new User(firstName, lastName, email, password);
        return userDAO.save(user);
    }

    public User register(User user) {
        // Check if email already exists
        if (userDAO.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already registered: " + user.getEmail());
        }
        return userDAO.save(user);
    }

    public Optional<User> login(String email, String password) {
        return userDAO.findByEmailAndPassword(email, password);
    }

    public Optional<User> findById(Long id) {
        return userDAO.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    public List<User> findAllUsers() {
        return userDAO.findAll();
    }

    public User updateUser(User user) {
        return userDAO.update(user);
    }

    public void deleteUser(Long userId) {
        userDAO.deleteById(userId);
    }

    public boolean emailExists(String email) {
        return userDAO.existsByEmail(email);
    }
}
