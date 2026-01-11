package com.worldcup2030;

import com.worldcup2030.entity.*;
import com.worldcup2030.service.*;
import com.worldcup2030.util.DataInitializer;
import com.worldcup2030.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final UserService userService = new UserService();
    private static final MatchService matchService = new MatchService();
    private static final TicketService ticketService = new TicketService();
    private static final BookingService bookingService = new BookingService();

    private static User currentUser = null;

    public static void main(String[] args) {
        try {
            System.out.println("\n" + "═".repeat(60));
            System.out.println("🏆 WORLD CUP 2030 MOROCCO - TICKET SYSTEM 🇲🇦");
            System.out.println("═".repeat(60));

            // Initialize sample data
            DataInitializer initializer = new DataInitializer();
            initializer.initializeData();

            System.out.println("\n✅ System ready! Welcome to the World Cup 2030 Ticket System.\n");

            // Main menu loop
            boolean running = true;
            while (running) {
                if (currentUser == null) {
                    running = showLoginMenu();
                } else {
                    running = showMainMenu();
                }
            }

        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
            scanner.close();
            System.out.println("\n👋 Thank you for using World Cup 2030 Ticket System. Goodbye!");
        }
    }

    private static boolean showLoginMenu() {
        System.out.println("\n" + "─".repeat(40));
        System.out.println("       🔐 LOGIN / REGISTER");
        System.out.println("─".repeat(40));
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Quick Demo Login");
        System.out.println("0. Exit");
        System.out.print("\n👉 Choose an option: ");

        int choice = readInt();

        switch (choice) {
            case 1 -> login();
            case 2 -> register();
            case 3 -> demoLogin();
            case 0 -> {
                return false;
            }
            default -> System.out.println("❌ Invalid option. Please try again.");
        }
        return true;
    }

    private static boolean showMainMenu() {
        System.out.println("\n" + "═".repeat(60));
        System.out.println("🏆 WORLD CUP 2030 MOROCCO - MAIN MENU");
        System.out.println("   Logged in as: " + currentUser.getFullName());
        System.out.println("═".repeat(60));
        System.out.println("1. 📅 View All Matches");
        System.out.println("2. 🏟️ View Matches by Stadium");
        System.out.println("3. 🎟️ Search Available Tickets");
        System.out.println("4. 🛒 Book Tickets");
        System.out.println("5. 📋 View My Bookings");
        System.out.println("6. ❌ Cancel Booking");
        System.out.println("7. 👤 Logout");
        System.out.println("0. Exit");
        System.out.print("\n👉 Choose an option: ");

        int choice = readInt();

        switch (choice) {
            case 1 -> viewAllMatches();
            case 2 -> viewMatchesByStadium();
            case 3 -> searchAvailableTickets();
            case 4 -> bookTickets();
            case 5 -> viewMyBookings();
            case 6 -> cancelBooking();
            case 7 -> logout();
            case 0 -> {
                return false;
            }
            default -> System.out.println("❌ Invalid option. Please try again.");
        }
        return true;
    }

    private static void login() {
        System.out.println("\n📧 Enter email: ");
        String email = scanner.nextLine().trim();
        System.out.println("🔑 Enter password: ");
        String password = scanner.nextLine().trim();

        Optional<User> userOpt = userService.login(email, password);
        if (userOpt.isPresent()) {
            currentUser = userOpt.get();
            System.out.println("\n✅ Welcome back, " + currentUser.getFirstName() + "!");
        } else {
            System.out.println("\n❌ Invalid email or password. Please try again.");
        }
    }

    private static void register() {
        System.out.println("\n" + "─".repeat(40));
        System.out.println("       📝 REGISTRATION");
        System.out.println("─".repeat(40));

        System.out.print("First Name: ");
        String firstName = scanner.nextLine().trim();
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine().trim();
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Phone: ");
        String phone = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        System.out.print("Country: ");
        String country = scanner.nextLine().trim();

        try {
            User newUser = new User(firstName, lastName, email, phone, password, country);
            userService.register(newUser);
            System.out.println("\n✅ Registration successful! Please login.");
        } catch (Exception e) {
            System.out.println("\n❌ Registration failed: " + e.getMessage());
        }
    }

    private static void demoLogin() {
        Optional<User> userOpt = userService.login("demo@worldcup2030.com", "demo123");
        if (userOpt.isPresent()) {
            currentUser = userOpt.get();
            System.out.println("\n✅ Logged in as Demo User: " + currentUser.getFullName());
        } else {
            System.out.println("\n❌ Demo user not found. Please restart the application.");
        }
    }

    private static void logout() {
        System.out.println("\n👋 Goodbye, " + currentUser.getFirstName() + "!");
        currentUser = null;
    }

    private static void viewAllMatches() {
        System.out.println("\n" + "─".repeat(60));
        System.out.println("       📅 ALL WORLD CUP 2030 MATCHES");
        System.out.println("─".repeat(60));

        List<Match> matches = matchService.findAllMatches();
        if (matches.isEmpty()) {
            System.out.println("No matches found.");
            return;
        }

        for (Match match : matches) {
            long availableTickets = matchService.getAvailableTicketCount(match.getId());
            System.out.println(match);
            System.out.println("   🎟️ Available tickets: " + availableTickets);
            System.out.println();
        }
    }

    private static void viewMatchesByStadium() {
        System.out.println("\n" + "─".repeat(40));
        System.out.println("       🏟️ SELECT STADIUM");
        System.out.println("─".repeat(40));

        List<Stadium> stadiums = matchService.getAllStadiums();
        for (int i = 0; i < stadiums.size(); i++) {
            System.out.println((i + 1) + ". " + stadiums.get(i));
        }

        System.out.print("\n👉 Select stadium (1-" + stadiums.size() + "): ");
        int choice = readInt();

        if (choice < 1 || choice > stadiums.size()) {
            System.out.println("❌ Invalid selection.");
            return;
        }

        Stadium selectedStadium = stadiums.get(choice - 1);
        List<Match> matches = matchService.findByStadium(selectedStadium.getId());

        System.out.println("\n📅 Matches at " + selectedStadium.getName() + ":");
        System.out.println("─".repeat(50));

        if (matches.isEmpty()) {
            System.out.println("No matches scheduled at this stadium.");
        } else {
            for (Match match : matches) {
                long availableTickets = matchService.getAvailableTicketCount(match.getId());
                System.out.println(match);
                System.out.println("   🎟️ Available tickets: " + availableTickets);
                System.out.println();
            }
        }
    }

    private static void searchAvailableTickets() {
        System.out.println("\n" + "─".repeat(40));
        System.out.println("       🎟️ SEARCH TICKETS");
        System.out.println("─".repeat(40));

        // Show matches
        List<Match> matches = matchService.findAllMatches();
        for (int i = 0; i < matches.size(); i++) {
            Match match = matches.get(i);
            System.out.println((i + 1) + ". " + match);
        }

        System.out.print("\n👉 Select match (1-" + matches.size() + "): ");
        int choice = readInt();

        if (choice < 1 || choice > matches.size()) {
            System.out.println("❌ Invalid selection.");
            return;
        }

        Match selectedMatch = matches.get(choice - 1);
        List<Ticket> tickets = ticketService.findAvailableTickets(selectedMatch.getId());

        System.out.println("\n🎟️ Available Tickets for: " + selectedMatch.getHomeTeam().getFifaCode() +
                " vs " + selectedMatch.getAwayTeam().getFifaCode());
        System.out.println("─".repeat(60));

        if (tickets.isEmpty()) {
            System.out.println("❌ No tickets available for this match.");
        } else {
            System.out.println("\n📊 CATEGORY 1 - Premium ($500):");
            tickets.stream()
                    .filter(t -> t.getCategory() == Ticket.TicketCategory.CATEGORY_1)
                    .limit(5)
                    .forEach(t -> System.out.println("   " + t));

            System.out.println("\n📊 CATEGORY 2 - Standard ($300):");
            tickets.stream()
                    .filter(t -> t.getCategory() == Ticket.TicketCategory.CATEGORY_2)
                    .limit(5)
                    .forEach(t -> System.out.println("   " + t));

            System.out.println("\n📊 CATEGORY 3 - Economy ($150):");
            tickets.stream()
                    .filter(t -> t.getCategory() == Ticket.TicketCategory.CATEGORY_3)
                    .limit(5)
                    .forEach(t -> System.out.println("   " + t));

            System.out.println("\n📈 Total available: " + tickets.size() + " tickets");
        }
    }

    private static void bookTickets() {
        System.out.println("\n" + "─".repeat(40));
        System.out.println("       🛒 BOOK TICKETS");
        System.out.println("─".repeat(40));

        // Show matches
        List<Match> matches = matchService.findAllMatches();
        for (int i = 0; i < matches.size(); i++) {
            Match match = matches.get(i);
            long available = matchService.getAvailableTicketCount(match.getId());
            System.out.println((i + 1) + ". " + match + " [" + available + " available]");
        }

        System.out.print("\n👉 Select match (1-" + matches.size() + "): ");
        int matchChoice = readInt();

        if (matchChoice < 1 || matchChoice > matches.size()) {
            System.out.println("❌ Invalid selection.");
            return;
        }

        Match selectedMatch = matches.get(matchChoice - 1);
        List<Ticket> availableTickets = ticketService.findAvailableTickets(selectedMatch.getId());

        if (availableTickets.isEmpty()) {
            System.out.println("❌ No tickets available for this match.");
            return;
        }

        // Show ticket categories
        System.out.println("\n📋 Select ticket category:");
        System.out.println("1. CATEGORY 1 - Premium ($500)");
        System.out.println("2. CATEGORY 2 - Standard ($300)");
        System.out.println("3. CATEGORY 3 - Economy ($150)");
        System.out.print("\n👉 Select category (1-3): ");
        int catChoice = readInt();

        Ticket.TicketCategory category = switch (catChoice) {
            case 1 -> Ticket.TicketCategory.CATEGORY_1;
            case 2 -> Ticket.TicketCategory.CATEGORY_2;
            case 3 -> Ticket.TicketCategory.CATEGORY_3;
            default -> null;
        };

        if (category == null) {
            System.out.println("❌ Invalid category.");
            return;
        }

        List<Ticket> categoryTickets = ticketService.findAvailableByCategory(selectedMatch.getId(), category);

        if (categoryTickets.isEmpty()) {
            System.out.println("❌ No tickets available in this category.");
            return;
        }

        System.out.println("\n🎟️ Available " + category.getCode() + " tickets:");
        for (int i = 0; i < Math.min(categoryTickets.size(), 10); i++) {
            System.out.println((i + 1) + ". " + categoryTickets.get(i));
        }

        System.out.print("\n👉 How many tickets? (max 4): ");
        int quantity = readInt();

        if (quantity < 1 || quantity > 4 || quantity > categoryTickets.size()) {
            System.out.println("❌ Invalid quantity.");
            return;
        }

        // Select tickets
        List<Long> ticketIds = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            ticketIds.add(categoryTickets.get(i).getId());
        }

        try {
            Booking booking = bookingService.createBooking(currentUser, ticketIds);

            System.out.println("\n" + "═".repeat(50));
            System.out.println("✅ BOOKING CREATED SUCCESSFULLY!");
            System.out.println("═".repeat(50));
            System.out.println("📋 Booking Reference: " + booking.getBookingReference());
            System.out.println("💰 Total Price: $" + booking.getTotalPrice());
            System.out.println("📊 Status: " + booking.getStatus());
            System.out.println("🎟️ Tickets: " + booking.getTickets().size());

            System.out.print("\n👉 Confirm payment? (y/n): ");
            String confirm = scanner.nextLine().trim().toLowerCase();

            if (confirm.equals("y") || confirm.equals("yes")) {
                bookingService.confirmBooking(booking.getId());
                System.out.println("\n✅ Payment confirmed! Your tickets are ready.");
                System.out.println("📧 Confirmation sent to: " + currentUser.getEmail());
            } else {
                System.out.println("\n⚠️ Booking saved as PENDING. Complete payment within 24 hours.");
            }

        } catch (Exception e) {
            System.out.println("\n❌ Booking failed: " + e.getMessage());
        }
    }

    private static void viewMyBookings() {
        System.out.println("\n" + "─".repeat(50));
        System.out.println("       📋 MY BOOKINGS");
        System.out.println("─".repeat(50));

        List<Booking> bookings = bookingService.findByUser(currentUser.getId());

        if (bookings.isEmpty()) {
            System.out.println("📭 You have no bookings yet.");
            return;
        }

        for (Booking booking : bookings) {
            System.out.println("\n" + booking);
            System.out.println("   📅 Booked on: " + booking.getBookingDate().toString().replace("T", " "));
            for (Ticket ticket : booking.getTickets()) {
                System.out.println("   " + ticket);
            }
        }
    }

    private static void cancelBooking() {
        System.out.println("\n" + "─".repeat(40));
        System.out.println("       ❌ CANCEL BOOKING");
        System.out.println("─".repeat(40));

        List<Booking> bookings = bookingService.findByUser(currentUser.getId());
        List<Booking> cancellable = bookings.stream()
                .filter(b -> b.getStatus() != Booking.BookingStatus.CANCELLED)
                .toList();

        if (cancellable.isEmpty()) {
            System.out.println("📭 No bookings available to cancel.");
            return;
        }

        for (int i = 0; i < cancellable.size(); i++) {
            System.out.println((i + 1) + ". " + cancellable.get(i));
        }

        System.out.print("\n👉 Select booking to cancel (1-" + cancellable.size() + "): ");
        int choice = readInt();

        if (choice < 1 || choice > cancellable.size()) {
            System.out.println("❌ Invalid selection.");
            return;
        }

        Booking selectedBooking = cancellable.get(choice - 1);

        System.out.print("⚠️ Are you sure you want to cancel booking " +
                selectedBooking.getBookingReference() + "? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("y") || confirm.equals("yes")) {
            try {
                bookingService.cancelBooking(selectedBooking.getId());
                System.out.println("\n✅ Booking cancelled successfully. Refund will be processed.");
            } catch (Exception e) {
                System.out.println("\n❌ Cancellation failed: " + e.getMessage());
            }
        } else {
            System.out.println("\n↩️ Cancellation aborted.");
        }
    }

    private static int readInt() {
        try {
            String line = scanner.nextLine().trim();
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
