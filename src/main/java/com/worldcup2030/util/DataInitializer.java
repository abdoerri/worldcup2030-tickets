package com.worldcup2030.util;

import com.worldcup2030.dao.*;
import com.worldcup2030.entity.*;
import com.worldcup2030.service.TicketService;

import java.time.LocalDateTime;

public class DataInitializer {

        private final StadiumDAO stadiumDAO;
        private final TeamDAO teamDAO;
        private final MatchDAO matchDAO;
        private final UserDAO userDAO;
        private final TicketService ticketService;

        public DataInitializer() {
                this.stadiumDAO = new StadiumDAO();
                this.teamDAO = new TeamDAO();
                this.matchDAO = new MatchDAO();
                this.userDAO = new UserDAO();
                this.ticketService = new TicketService();
        }

        public void initializeData() {
                System.out.println("🔄 Initializing World Cup 2030 Morocco data...");

                // Only initialize if database is empty
                if (stadiumDAO.count() > 0) {
                        System.out.println("✅ Data already initialized!");
                        return;
                }

                initializeStadiums();
                initializeTeams();
                initializeMatches();
                initializeDemoUser();

                System.out.println("✅ Data initialization complete!");
        }

        private void initializeStadiums() {
                System.out.println("   🏟️ Creating stadiums...");

                Stadium[] stadiums = {
                                new Stadium("Grand Stade de Casablanca", "Casablanca", 93000,
                                                "The largest stadium in Morocco, hosting the World Cup Final"),
                                new Stadium("Stade Ibn Batouta", "Tangier", 65000,
                                                "Modern stadium in the north of Morocco"),
                                new Stadium("Stade de Marrakech", "Marrakech", 45000,
                                                "Beautiful stadium in the Red City"),
                                new Stadium("Stade Moulay Abdallah", "Rabat", 52000,
                                                "Main stadium in the capital city"),
                                new Stadium("Stade de Fès", "Fez", 45000,
                                                "Stadium in the cultural heart of Morocco"),
                                new Stadium("Stade d'Agadir", "Agadir", 45000,
                                                "Coastal stadium in southern Morocco")
                };

                for (Stadium stadium : stadiums) {
                        stadiumDAO.save(stadium);
                }
        }

        private void initializeTeams() {
                System.out.println("   ⚽ Creating teams...");

                Team[] teams = {
                                // Group A
                                new Team("Morocco", "MAR", "A", "🇲🇦"),
                                new Team("Spain", "ESP", "A", "🇪🇸"),
                                new Team("Portugal", "POR", "A", "🇵🇹"),
                                new Team("Croatia", "CRO", "A", "🇭🇷"),

                                // Group B
                                new Team("France", "FRA", "B", "🇫🇷"),
                                new Team("Germany", "GER", "B", "🇩🇪"),
                                new Team("Brazil", "BRA", "B", "🇧🇷"),
                                new Team("Argentina", "ARG", "B", "🇦🇷"),

                                // Group C
                                new Team("England", "ENG", "C", "🏴󠁧󠁢󠁥󠁮󠁧󠁿"),
                                new Team("Netherlands", "NED", "C", "🇳🇱"),
                                new Team("Italy", "ITA", "C", "🇮🇹"),
                                new Team("Belgium", "BEL", "C", "🇧🇪"),

                                // Group D
                                new Team("Japan", "JPN", "D", "🇯🇵"),
                                new Team("South Korea", "KOR", "D", "🇰🇷"),
                                new Team("USA", "USA", "D", "🇺🇸"),
                                new Team("Mexico", "MEX", "D", "🇲🇽")
                };

                for (Team team : teams) {
                        teamDAO.save(team);
                }
        }

        private void initializeMatches() {
                System.out.println("   📅 Creating matches...");

                // Get stadiums
                Stadium casablanca = stadiumDAO.findByName("Grand Stade de Casablanca").orElseThrow();
                Stadium tangier = stadiumDAO.findByName("Stade Ibn Batouta").orElseThrow();
                Stadium marrakech = stadiumDAO.findByName("Stade de Marrakech").orElseThrow();
                Stadium rabat = stadiumDAO.findByName("Stade Moulay Abdallah").orElseThrow();

                // Get teams
                Team morocco = teamDAO.findByFifaCode("MAR").orElseThrow();
                Team spain = teamDAO.findByFifaCode("ESP").orElseThrow();
                Team portugal = teamDAO.findByFifaCode("POR").orElseThrow();
                Team france = teamDAO.findByFifaCode("FRA").orElseThrow();
                Team brazil = teamDAO.findByFifaCode("BRA").orElseThrow();
                Team argentina = teamDAO.findByFifaCode("ARG").orElseThrow();
                Team germany = teamDAO.findByFifaCode("GER").orElseThrow();
                Team england = teamDAO.findByFifaCode("ENG").orElseThrow();

                // Opening Match - Morocco vs Spain (June 13, 2030)
                Match openingMatch = new Match(morocco, spain, casablanca,
                                LocalDateTime.of(2030, 6, 13, 21, 0), Match.MatchPhase.GROUP_STAGE);
                openingMatch.setMatchNumber(1);
                matchDAO.save(openingMatch);
                ticketService.generateTicketsForMatch(openingMatch, 10);

                // Group Stage matches
                Match match2 = new Match(portugal, france, tangier,
                                LocalDateTime.of(2030, 6, 14, 18, 0), Match.MatchPhase.GROUP_STAGE);
                match2.setMatchNumber(2);
                matchDAO.save(match2);
                ticketService.generateTicketsForMatch(match2, 10);

                Match match3 = new Match(brazil, argentina, casablanca,
                                LocalDateTime.of(2030, 6, 15, 21, 0), Match.MatchPhase.GROUP_STAGE);
                match3.setMatchNumber(3);
                matchDAO.save(match3);
                ticketService.generateTicketsForMatch(match3, 10);

                Match match4 = new Match(germany, england, marrakech,
                                LocalDateTime.of(2030, 6, 16, 18, 0), Match.MatchPhase.GROUP_STAGE);
                match4.setMatchNumber(4);
                matchDAO.save(match4);
                ticketService.generateTicketsForMatch(match4, 10);

                Match match5 = new Match(morocco, portugal, rabat,
                                LocalDateTime.of(2030, 6, 18, 21, 0), Match.MatchPhase.GROUP_STAGE);
                match5.setMatchNumber(5);
                matchDAO.save(match5);
                ticketService.generateTicketsForMatch(match5, 10);

                // Quarter Final
                Match quarterFinal = new Match(morocco, france, casablanca,
                                LocalDateTime.of(2030, 7, 5, 21, 0), Match.MatchPhase.QUARTER_FINAL);
                quarterFinal.setMatchNumber(57);
                matchDAO.save(quarterFinal);
                ticketService.generateTicketsForMatch(quarterFinal, 10);

                // Semi Final
                Match semiFinal = new Match(morocco, brazil, casablanca,
                                LocalDateTime.of(2030, 7, 9, 21, 0), Match.MatchPhase.SEMI_FINAL);
                semiFinal.setMatchNumber(61);
                matchDAO.save(semiFinal);
                ticketService.generateTicketsForMatch(semiFinal, 10);

                // Final
                Match finalMatch = new Match(morocco, argentina, casablanca,
                                LocalDateTime.of(2030, 7, 13, 21, 0), Match.MatchPhase.FINAL);
                finalMatch.setMatchNumber(64);
                matchDAO.save(finalMatch);
                ticketService.generateTicketsForMatch(finalMatch, 10);
        }

        private void initializeDemoUser() {
                System.out.println("   👤 Creating demo users...");

                // Create regular demo user
                if (!userDAO.existsByEmail("demo@worldcup2030.com")) {
                        User demoUser = new User(
                                        "Ahmed",
                                        "Benali",
                                        "demo@worldcup2030.com",
                                        "+212 6 12 34 56 78",
                                        "demo123",
                                        "Morocco");
                        userDAO.save(demoUser);
                }

                // Create admin user
                if (!userDAO.existsByEmail("admin@worldcup2030.com")) {
                        User adminUser = new User(
                                        "Admin",
                                        "User",
                                        "admin@worldcup2030.com",
                                        "+212 5 00 00 00 00",
                                        "admin123",
                                        "Morocco");
                        adminUser.setRole(User.UserRole.ADMIN);
                        userDAO.save(adminUser);
                }
        }
}
