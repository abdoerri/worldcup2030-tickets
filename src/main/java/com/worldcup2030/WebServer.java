package com.worldcup2030;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.worldcup2030.util.DataInitializer;
import com.worldcup2030.util.HibernateUtil;
import com.worldcup2030.api.*;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

public class WebServer {

    private static final int PORT = 8080;
    private static final Map<String, String> MIME_TYPES = new HashMap<>();

    static {
        MIME_TYPES.put("html", "text/html");
        MIME_TYPES.put("css", "text/css");
        MIME_TYPES.put("js", "application/javascript");
        MIME_TYPES.put("json", "application/json");
        MIME_TYPES.put("png", "image/png");
        MIME_TYPES.put("jpg", "image/jpeg");
        MIME_TYPES.put("ico", "image/x-icon");
        MIME_TYPES.put("svg", "image/svg+xml");
    }

    public static void main(String[] args) throws Exception {
        System.out.println("\n" + "═".repeat(60));
        System.out.println("🏆 WORLD CUP 2030 MOROCCO - WEB SERVER 🇲🇦");
        System.out.println("═".repeat(60));

        // Initialize database
        System.out.println("\n🔄 Initializing database...");
        try {
            DataInitializer initializer = new DataInitializer();
            initializer.initializeData();
            System.out.println("✅ Database initialized!");
        } catch (Exception e) {
            System.out.println("⚠️ Database initialization skipped: " + e.getMessage());
            e.printStackTrace();
        }

        // Create HTTP server
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        // Register API endpoints
        System.out.println("\n📡 Registering API endpoints...");
        server.createContext("/api/teams", new TeamApi());
        server.createContext("/api/stadiums", new StadiumApi());
        server.createContext("/api/matches", new MatchApi());
        server.createContext("/api/tickets", new TicketApi());
        server.createContext("/api/users", new UserApi());
        server.createContext("/api/auth", new UserApi());
        server.createContext("/api/bookings", new BookingApi());
        System.out.println("✅ API endpoints registered!");

        // Static file handler (must be last - catches all other routes)
        server.createContext("/", new StaticFileHandler());

        // Set executor
        server.setExecutor(null);

        // Start server
        server.start();

        System.out.println("\n" + "─".repeat(60));
        System.out.println("🚀 Web server started successfully!");
        System.out.println("─".repeat(60));
        System.out.println("\n📍 Open your browser and navigate to:");
        System.out.println("\n   👉 http://localhost:" + PORT);
        System.out.println("\n─".repeat(60));
        System.out.println("Press Ctrl+C to stop the server");
        System.out.println("─".repeat(60));

        // Add shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n\n👋 Shutting down server...");
            server.stop(0);
            HibernateUtil.shutdown();
            System.out.println("✅ Server stopped. Goodbye!");
        }));
    }

    static class StaticFileHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();

            // Default to index.html
            if (path.equals("/") || path.isEmpty()) {
                path = "/index.html";
            }

            // Try to find the file in resources/static
            String resourcePath = "/static" + path;
            InputStream inputStream = getClass().getResourceAsStream(resourcePath);

            if (inputStream == null) {
                // Try without static prefix (for development)
                inputStream = getClass().getResourceAsStream(path);
            }

            if (inputStream == null) {
                // File not found
                String response = "404 Not Found: " + path;
                exchange.sendResponseHeaders(404, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
                return;
            }

            // Determine content type
            String extension = getExtension(path);
            String contentType = MIME_TYPES.getOrDefault(extension, "application/octet-stream");

            // Read file content
            byte[] content = inputStream.readAllBytes();
            inputStream.close();

            // Send response
            exchange.getResponseHeaders().set("Content-Type", contentType + "; charset=UTF-8");
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.sendResponseHeaders(200, content.length);

            OutputStream os = exchange.getResponseBody();
            os.write(content);
            os.close();
        }

        private String getExtension(String path) {
            int lastDot = path.lastIndexOf('.');
            if (lastDot > 0) {
                return path.substring(lastDot + 1).toLowerCase();
            }
            return "";
        }
    }
}
