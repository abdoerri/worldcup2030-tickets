package com.worldcup2030.api;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.HashMap;

public abstract class ApiHandler implements HttpHandler {

    protected static final Gson gson = new GsonBuilder()
            .setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    // Avoid circular references
                    return f.getName().equals("tickets") || f.getName().equals("bookings");
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            })
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Add CORS headers
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");
        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");

        // Handle preflight
        if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
            exchange.sendResponseHeaders(200, -1);
            return;
        }

        try {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();

            switch (method) {
                case "GET":
                    handleGet(exchange, path);
                    break;
                case "POST":
                    handlePost(exchange, path);
                    break;
                case "PUT":
                    handlePut(exchange, path);
                    break;
                case "DELETE":
                    handleDelete(exchange, path);
                    break;
                default:
                    sendError(exchange, 405, "Method not allowed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendError(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    protected void handleGet(HttpExchange exchange, String path) throws IOException {
        sendError(exchange, 405, "GET not supported");
    }

    protected void handlePost(HttpExchange exchange, String path) throws IOException {
        sendError(exchange, 405, "POST not supported");
    }

    protected void handlePut(HttpExchange exchange, String path) throws IOException {
        sendError(exchange, 405, "PUT not supported");
    }

    protected void handleDelete(HttpExchange exchange, String path) throws IOException {
        sendError(exchange, 405, "DELETE not supported");
    }

    protected void sendJson(HttpExchange exchange, Object data) throws IOException {
        String json = gson.toJson(data);
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(200, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    protected void sendError(HttpExchange exchange, int code, String message) throws IOException {
        Map<String, String> error = new HashMap<>();
        error.put("error", message);
        String json = gson.toJson(error);
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(code, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    protected void sendSuccess(HttpExchange exchange, String message) throws IOException {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", message);
        sendJson(exchange, response);
    }

    protected String readRequestBody(HttpExchange exchange) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))) {
            StringBuilder body = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                body.append(line);
            }
            return body.toString();
        }
    }

    protected Long extractIdFromPath(String path, String prefix) {
        try {
            String idStr = path.substring(prefix.length());
            if (idStr.contains("/")) {
                idStr = idStr.substring(0, idStr.indexOf("/"));
            }
            return Long.parseLong(idStr);
        } catch (Exception e) {
            return null;
        }
    }
}
