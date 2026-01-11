-- =====================================================
-- World Cup 2030 Morocco - Database Initialization
-- =====================================================
-- Database: worldcup2030
-- Author: Abderrahman
-- Date: 2026-01-11
-- =====================================================

-- Create database if not exists
CREATE DATABASE IF NOT EXISTS worldcup2030 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE worldcup2030;

-- =====================================================
-- Table: users
-- Description: Stores user information (clients and admins)
-- =====================================================
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20),
    password VARCHAR(255) NOT NULL,
    country VARCHAR(100),
    role ENUM('USER', 'ADMIN') DEFAULT 'USER',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    
    INDEX idx_users_email (email),
    INDEX idx_users_role (role)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- Table: stadiums
-- Description: Stores stadium information for World Cup 2030
-- =====================================================
CREATE TABLE IF NOT EXISTS stadiums (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    city VARCHAR(50) NOT NULL,
    capacity INT NOT NULL,
    description VARCHAR(255),
    
    INDEX idx_stadiums_city (city)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- Table: teams
-- Description: Stores participating team information
-- =====================================================
CREATE TABLE IF NOT EXISTS teams (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    country VARCHAR(100) NOT NULL,
    fifa_code VARCHAR(3) NOT NULL,
    group_name VARCHAR(1),
    flag_emoji VARCHAR(50),
    
    UNIQUE INDEX idx_teams_fifa_code (fifa_code),
    INDEX idx_teams_group (group_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- Table: matches
-- Description: Stores match schedule and details
-- =====================================================
CREATE TABLE IF NOT EXISTS matches (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    home_team_id BIGINT NOT NULL,
    away_team_id BIGINT NOT NULL,
    stadium_id BIGINT NOT NULL,
    match_date DATETIME NOT NULL,
    match_phase ENUM('GROUP_STAGE', 'ROUND_OF_32', 'ROUND_OF_16', 'QUARTER_FINAL', 'SEMI_FINAL', 'THIRD_PLACE', 'FINAL') NOT NULL,
    match_number INT,
    
    FOREIGN KEY (home_team_id) REFERENCES teams(id) ON DELETE CASCADE,
    FOREIGN KEY (away_team_id) REFERENCES teams(id) ON DELETE CASCADE,
    FOREIGN KEY (stadium_id) REFERENCES stadiums(id) ON DELETE CASCADE,
    
    INDEX idx_matches_date (match_date),
    INDEX idx_matches_phase (match_phase),
    INDEX idx_matches_stadium (stadium_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- Table: bookings
-- Description: Stores booking/reservation information
-- =====================================================
CREATE TABLE IF NOT EXISTS bookings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    booking_reference VARCHAR(20) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    booking_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status ENUM('PENDING', 'CONFIRMED', 'CANCELLED', 'COMPLETED') NOT NULL DEFAULT 'PENDING',
    total_price DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    payment_method VARCHAR(50),
    notes VARCHAR(500),
    
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    
    INDEX idx_bookings_reference (booking_reference),
    INDEX idx_bookings_user (user_id),
    INDEX idx_bookings_status (status),
    INDEX idx_bookings_date (booking_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- Table: tickets
-- Description: Stores ticket information for matches
-- =====================================================
CREATE TABLE IF NOT EXISTS tickets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    match_id BIGINT NOT NULL,
    seat_number VARCHAR(20) NOT NULL,
    seat_row VARCHAR(10),
    section VARCHAR(20),
    category ENUM('CATEGORY_1', 'CATEGORY_2', 'CATEGORY_3') NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    status ENUM('AVAILABLE', 'RESERVED', 'SOLD', 'CANCELLED') NOT NULL DEFAULT 'AVAILABLE',
    booking_id BIGINT,
    
    FOREIGN KEY (match_id) REFERENCES matches(id) ON DELETE CASCADE,
    FOREIGN KEY (booking_id) REFERENCES bookings(id) ON DELETE SET NULL,
    
    INDEX idx_tickets_match (match_id),
    INDEX idx_tickets_status (status),
    INDEX idx_tickets_category (category),
    INDEX idx_tickets_booking (booking_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- Initial Data: Admin User
-- =====================================================
-- Insert admin user with password: admin123
INSERT INTO users (first_name, last_name, email, phone, password, country, role, created_at)
VALUES ('Admin', 'Morocco', 'admin@worldcup2030.com', '+212600000000', 'admin123', 'Morocco', 'ADMIN', NOW())
ON DUPLICATE KEY UPDATE password = 'admin123', role = 'ADMIN';

-- Insert demo user
INSERT INTO users (first_name, last_name, email, phone, password, country, role, created_at)
VALUES ('Ahmed', 'Benali', 'demo@worldcup2030.com', '+212611111111', 'demo123', 'Morocco', 'USER', NOW())
ON DUPLICATE KEY UPDATE password = 'demo123';

-- =====================================================
-- Initial Data: Stadiums (Morocco Host Cities)
-- =====================================================
INSERT INTO stadiums (name, city, capacity, description) VALUES
('Grand Stade de Casablanca', 'Casablanca', 93000, 'Le plus grand stade du Maroc, nouveau stade construit pour la Coupe du Monde 2030'),
('Stade Ibn Batouta', 'Tangier', 65000, 'Stade moderne de Tanger, rénové pour la Coupe du Monde'),
('Stade de Marrakech', 'Marrakech', 45000, 'Stade situé au cœur de la ville rouge'),
('Stade Moulay Abdallah', 'Rabat', 52000, 'Stade national situé dans la capitale'),
('Stade de Fès', 'Fez', 45000, 'Stade de la ville impériale de Fès'),
('Stade d''Agadir', 'Agadir', 45000, 'Stade côtier de la ville d''Agadir')
ON DUPLICATE KEY UPDATE capacity = VALUES(capacity);

-- =====================================================
-- Initial Data: Teams (Sample participating teams)
-- =====================================================
INSERT INTO teams (country, fifa_code, group_name, flag_emoji) VALUES
('Morocco', 'MAR', 'A', '🇲🇦'),
('Spain', 'ESP', 'A', '🇪🇸'),
('Portugal', 'POR', 'A', '🇵🇹'),
('Argentina', 'ARG', 'A', '🇦🇷'),
('France', 'FRA', 'B', '🇫🇷'),
('Brazil', 'BRA', 'B', '🇧🇷'),
('Germany', 'GER', 'B', '🇩🇪'),
('England', 'ENG', 'B', '🏴󠁧󠁢󠁥󠁮󠁧󠁿'),
('Italy', 'ITA', 'C', '🇮🇹'),
('Netherlands', 'NED', 'C', '🇳🇱'),
('Belgium', 'BEL', 'C', '🇧🇪'),
('Croatia', 'CRO', 'C', '🇭🇷')
ON DUPLICATE KEY UPDATE group_name = VALUES(group_name);

-- =====================================================
-- Initial Data: Sample Matches
-- =====================================================
INSERT INTO matches (home_team_id, away_team_id, stadium_id, match_date, match_phase, match_number)
SELECT 
    (SELECT id FROM teams WHERE fifa_code = 'MAR'),
    (SELECT id FROM teams WHERE fifa_code = 'ESP'),
    (SELECT id FROM stadiums WHERE city = 'Casablanca'),
    '2030-06-13 21:00:00',
    'GROUP_STAGE',
    1
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM matches WHERE match_number = 1);

INSERT INTO matches (home_team_id, away_team_id, stadium_id, match_date, match_phase, match_number)
SELECT 
    (SELECT id FROM teams WHERE fifa_code = 'POR'),
    (SELECT id FROM teams WHERE fifa_code = 'ARG'),
    (SELECT id FROM stadiums WHERE city = 'Tangier'),
    '2030-06-14 18:00:00',
    'GROUP_STAGE',
    2
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM matches WHERE match_number = 2);

INSERT INTO matches (home_team_id, away_team_id, stadium_id, match_date, match_phase, match_number)
SELECT 
    (SELECT id FROM teams WHERE fifa_code = 'FRA'),
    (SELECT id FROM teams WHERE fifa_code = 'BRA'),
    (SELECT id FROM stadiums WHERE city = 'Marrakech'),
    '2030-06-14 21:00:00',
    'GROUP_STAGE',
    3
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM matches WHERE match_number = 3);

INSERT INTO matches (home_team_id, away_team_id, stadium_id, match_date, match_phase, match_number)
SELECT 
    (SELECT id FROM teams WHERE fifa_code = 'GER'),
    (SELECT id FROM teams WHERE fifa_code = 'ENG'),
    (SELECT id FROM stadiums WHERE city = 'Rabat'),
    '2030-06-15 18:00:00',
    'GROUP_STAGE',
    4
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM matches WHERE match_number = 4);

-- =====================================================
-- Initial Data: Sample Tickets for Matches
-- =====================================================
-- Create tickets for Match 1 (MAR vs ESP)
INSERT INTO tickets (match_id, seat_number, seat_row, section, category, price, status)
SELECT m.id, CONCAT('A', n.num), 'R1', 'A', 'CATEGORY_1', 500.00, 'AVAILABLE'
FROM matches m
CROSS JOIN (SELECT 1 AS num UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5) n
WHERE m.match_number = 1
ON DUPLICATE KEY UPDATE status = status;

INSERT INTO tickets (match_id, seat_number, seat_row, section, category, price, status)
SELECT m.id, CONCAT('B', n.num), 'R2', 'B', 'CATEGORY_2', 300.00, 'AVAILABLE'
FROM matches m
CROSS JOIN (SELECT 1 AS num UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5) n
WHERE m.match_number = 1
ON DUPLICATE KEY UPDATE status = status;

INSERT INTO tickets (match_id, seat_number, seat_row, section, category, price, status)
SELECT m.id, CONCAT('C', n.num), 'R3', 'C', 'CATEGORY_3', 150.00, 'AVAILABLE'
FROM matches m
CROSS JOIN (SELECT 1 AS num UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5) n
WHERE m.match_number = 1
ON DUPLICATE KEY UPDATE status = status;

-- =====================================================
-- Verification Queries
-- =====================================================
-- Show created users
SELECT 'Users Created:' AS info;
SELECT id, first_name, last_name, email, role FROM users;

-- Show stadiums
SELECT 'Stadiums Created:' AS info;
SELECT id, name, city, capacity FROM stadiums;

-- Show teams
SELECT 'Teams Created:' AS info;
SELECT id, country, fifa_code, group_name, flag_emoji FROM teams;

-- Show matches
SELECT 'Matches Created:' AS info;
SELECT m.id, m.match_number, 
       ht.fifa_code AS home_team, 
       at.fifa_code AS away_team, 
       s.city AS stadium,
       m.match_date,
       m.match_phase
FROM matches m
JOIN teams ht ON m.home_team_id = ht.id
JOIN teams at ON m.away_team_id = at.id
JOIN stadiums s ON m.stadium_id = s.id;

-- Show ticket count
SELECT 'Tickets Created:' AS info;
SELECT COUNT(*) AS total_tickets FROM tickets;
