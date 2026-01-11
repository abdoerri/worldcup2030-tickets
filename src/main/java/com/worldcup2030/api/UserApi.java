package com.worldcup2030.api;

import com.worldcup2030.dao.UserDAO;
import com.worldcup2030.entity.User;
import com.sun.net.httpserver.HttpExchange;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserApi extends ApiHandler {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void handlePost(HttpExchange exchange, String path) throws IOException {
        if (path.equals("/api/auth/login")) {
            handleLogin(exchange);
        } else if (path.equals("/api/auth/register")) {
            handleRegister(exchange);
        } else {
            sendError(exchange, 404, "Endpoint not found");
        }
    }

    @Override
    protected void handleGet(HttpExchange exchange, String path) throws IOException {
        if (path.startsWith("/api/users/")) {
            Long id = extractIdFromPath(path, "/api/users/");
            if (id != null) {
                userDAO.findById(id).ifPresentOrElse(
                        user -> {
                            try {
                                sendJson(exchange, toApiFormat(user));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        },
                        () -> {
                            try {
                                sendError(exchange, 404, "User not found");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
            } else {
                sendError(exchange, 400, "Invalid user ID");
            }
        } else if (path.equals("/api/users") || path.equals("/api/users/")) {
            sendJson(exchange, userDAO.findAll().stream().map(this::toApiFormat).toList());
        } else {
            sendError(exchange, 404, "Endpoint not found");
        }
    }

    private void handleLogin(HttpExchange exchange) throws IOException {
        String body = readRequestBody(exchange);
        JsonObject json = JsonParser.parseString(body).getAsJsonObject();

        String email = json.get("email").getAsString();
        String password = json.get("password").getAsString();

        userDAO.findByEmail(email).ifPresentOrElse(
                user -> {
                    try {
                        if (user.getPassword().equals(password)) {
                            Map<String, Object> response = new HashMap<>();
                            response.put("success", true);
                            response.put("user", toApiFormat(user));
                            sendJson(exchange, response);
                        } else {
                            sendError(exchange, 401, "Invalid password");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                },
                () -> {
                    try {
                        sendError(exchange, 404, "User not found");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    private void handleRegister(HttpExchange exchange) throws IOException {
        String body = readRequestBody(exchange);
        JsonObject json = JsonParser.parseString(body).getAsJsonObject();

        String email = json.get("email").getAsString();

        if (userDAO.existsByEmail(email)) {
            sendError(exchange, 400, "Email already registered");
            return;
        }

        User user = new User(
                json.get("firstName").getAsString(),
                json.get("lastName").getAsString(),
                email,
                json.has("phone") ? json.get("phone").getAsString() : "",
                json.get("password").getAsString(),
                json.has("country") ? json.get("country").getAsString() : "");

        userDAO.save(user);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("user", toApiFormat(user));
        sendJson(exchange, response);
    }

    private Map<String, Object> toApiFormat(User user) {
        Map<String, Object> data = new HashMap<>();
        data.put("id", user.getId());
        data.put("firstName", user.getFirstName());
        data.put("lastName", user.getLastName());
        data.put("email", user.getEmail());
        data.put("phone", user.getPhone());
        data.put("country", user.getCountry());
        data.put("role", user.getRole().name().toLowerCase());
        return data;
    }
}
