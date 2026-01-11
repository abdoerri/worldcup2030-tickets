# 🏆 World Cup 2030 Morocco - Ticket System

> **Système de Billetterie pour la Coupe du Monde 2030**  
> Projet Java Avancé - Application Web Full Stack

![Java](https://img.shields.io/badge/Java-17+-orange?logo=openjdk)
![Hibernate](https://img.shields.io/badge/Hibernate-6.4-green?logo=hibernate)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql)
![Docker](https://img.shields.io/badge/Docker-Compose-blue?logo=docker)

---

## 📋 Table des Matières

- [Description](#-description)
- [Fonctionnalités](#-fonctionnalités)
- [Architecture](#-architecture)
- [Technologies & Concepts](#-technologies--concepts)
- [Diagramme de Classes](#-diagramme-de-classes)
- [Structure du Projet](#-structure-du-projet)
- [Installation](#-installation)
- [Utilisation](#-utilisation)
- [API REST](#-api-rest)
- [Auteur](#-auteur)

---

## 📖 Description

Cette application est un système complet de gestion de billetterie pour la Coupe du Monde 2030 qui sera organisée au Maroc. Elle permet aux utilisateurs de :

- Consulter les matchs et les stades
- Réserver des billets pour les matchs
- Gérer leurs réservations
- Accéder à un tableau de bord administrateur

Le projet met en œuvre les concepts clés de **Java Avancé** : Hibernate ORM, pattern DAO, API REST, et architecture en couches.

---

## ✨ Fonctionnalités

### 👤 Utilisateur
- Inscription et connexion
- Consultation des matchs par phase (Groupes, Huitièmes, Quarts, etc.)
- Réservation de billets avec choix de catégorie
- Suivi des réservations personnelles
- Annulation de réservations

### 🔑 Administrateur
- Tableau de bord avec statistiques
- Gestion des utilisateurs
- Gestion des réservations
- Suivi des ventes et revenus

---

## 🏗️ Architecture

L'application suit une **architecture en couches** (Layered Architecture) :

```
┌─────────────────────────────────────────────────────┐
│                   PRÉSENTATION                       │
│            (HTML/CSS/JavaScript Frontend)            │
├─────────────────────────────────────────────────────┤
│                    API REST                          │
│              (Java HTTP Server + JSON)               │
├─────────────────────────────────────────────────────┤
│                   SERVICES                           │
│                (Logique Métier)                      │
├─────────────────────────────────────────────────────┤
│                      DAO                             │
│           (Data Access Objects - CRUD)               │
├─────────────────────────────────────────────────────┤
│                   HIBERNATE                          │
│                  (ORM / JPA)                         │
├─────────────────────────────────────────────────────┤
│                    MySQL                             │
│              (Base de données)                       │
└─────────────────────────────────────────────────────┘
```

---

## 🛠️ Technologies & Concepts

### Backend Java

| Concept | Description | Implémentation |
|---------|-------------|----------------|
| **Hibernate ORM** | Mapping Objet-Relationnel | Annotations JPA sur les entités |
| **Pattern DAO** | Accès aux données | Classes DAO avec GenericDAO |
| **API REST** | Interface HTTP | Java HttpServer + Gson |
| **Singleton** | Instance unique | HibernateUtil.getSessionFactory() |
| **Énumérations** | Types constants | UserRole, BookingStatus, MatchPhase |

### Annotations Hibernate Utilisées

```java
@Entity          // Déclare une classe comme entité JPA
@Table           // Configure le nom de la table
@Id              // Définit la clé primaire
@GeneratedValue  // Auto-incrémentation
@Column          // Configuration des colonnes
@ManyToOne       // Relation plusieurs-à-un
@OneToMany       // Relation un-à-plusieurs
@Enumerated      // Persistance des énumérations
@Temporal        // Gestion des dates
```

### Frontend

| Technologie | Utilisation |
|-------------|-------------|
| **HTML5/CSS3** | Structure et style |
| **JavaScript ES6+** | Logique et appels API |
| **Fetch API** | Communication REST |
| **LocalStorage** | Authentification utilisateur |

### Infrastructure

| Outil | Rôle |
|-------|------|
| **Docker** | Conteneurisation MySQL |
| **Maven** | Gestion des dépendances |
| **MySQL 8.0** | Base de données relationnelle |

---

## 📊 Diagramme de Classes

```mermaid
classDiagram
    direction TB
    
    class User {
        -Long id
        -String firstName
        -String lastName
        -String email
        -String password
        -String phone
        -String country
        -UserRole role
        -LocalDateTime createdAt
        -List~Booking~ bookings
        +getters/setters()
    }
    
    class Booking {
        -Long id
        -String bookingReference
        -User user
        -LocalDateTime bookingDate
        -BookingStatus status
        -BigDecimal totalPrice
        -String paymentMethod
        -List~Ticket~ tickets
        +generateReference()
        +addTicket()
    }
    
    class Ticket {
        -Long id
        -Match match
        -String seatNumber
        -String seatRow
        -String section
        -TicketCategory category
        -BigDecimal price
        -TicketStatus status
        -Booking booking
    }
    
    class Match {
        -Long id
        -Team homeTeam
        -Team awayTeam
        -Stadium stadium
        -LocalDateTime matchDate
        -MatchPhase matchPhase
        -Integer matchNumber
        -List~Ticket~ tickets
    }
    
    class Team {
        -Long id
        -String country
        -String fifaCode
        -String groupName
        -String flagEmoji
    }
    
    class Stadium {
        -Long id
        -String name
        -String city
        -Integer capacity
        -String description
        -List~Match~ matches
    }
    
    class UserRole {
        <<enumeration>>
        USER
        ADMIN
    }
    
    class BookingStatus {
        <<enumeration>>
        PENDING
        CONFIRMED
        CANCELLED
        COMPLETED
    }
    
    class TicketCategory {
        <<enumeration>>
        CATEGORY_1
        CATEGORY_2
        CATEGORY_3
    }
    
    class MatchPhase {
        <<enumeration>>
        GROUP_STAGE
        ROUND_OF_16
        QUARTER_FINAL
        SEMI_FINAL
        FINAL
    }
    
    User "1" --> "*" Booking : possède
    Booking "1" --> "*" Ticket : contient
    Ticket "*" --> "1" Match : pour
    Match "*" --> "1" Stadium : dans
    Match "*" --> "2" Team : oppose
    User --> UserRole
    Booking --> BookingStatus
    Ticket --> TicketCategory
    Match --> MatchPhase
```

### Diagramme DAO

```mermaid
classDiagram
    direction TB
    
    class GenericDAO~T~ {
        #Class~T~ entityClass
        #Session getSession()
        +save(T entity)
        +update(T entity)
        +delete(T entity)
        +findById(Long id) Optional~T~
        +findAll() List~T~
    }
    
    class UserDAO {
        +findByEmail(String email) Optional~User~
        +findByRole(UserRole role) List~User~
    }
    
    class BookingDAO {
        +findByUserId(Long id) List~Booking~
        +findByReference(String ref) Optional~Booking~
        +findByStatus(BookingStatus status) List~Booking~
    }
    
    class TicketDAO {
        +findByMatch(Long matchId) List~Ticket~
        +findAvailable(Long matchId) List~Ticket~
        +countByStatus(TicketStatus status) Long
    }
    
    class MatchDAO {
        +findByPhase(MatchPhase phase) List~Match~
        +findByStadium(Long stadiumId) List~Match~
        +findUpcoming() List~Match~
    }
    
    GenericDAO <|-- UserDAO
    GenericDAO <|-- BookingDAO
    GenericDAO <|-- TicketDAO
    GenericDAO <|-- MatchDAO
    GenericDAO <|-- StadiumDAO
    GenericDAO <|-- TeamDAO
```

---

## 📁 Structure du Projet

```
worldcup2030-tickets/
├── 📁 src/main/java/com/worldcup2030/
│   ├── 📁 entity/                    # Entités JPA
│   │   ├── User.java                 # Utilisateur
│   │   ├── Booking.java              # Réservation
│   │   ├── Ticket.java               # Billet
│   │   ├── Match.java                # Match
│   │   ├── Team.java                 # Équipe
│   │   └── Stadium.java              # Stade
│   │
│   ├── 📁 dao/                       # Data Access Objects
│   │   ├── GenericDAO.java           # DAO générique (CRUD)
│   │   ├── UserDAO.java
│   │   ├── BookingDAO.java
│   │   ├── TicketDAO.java
│   │   ├── MatchDAO.java
│   │   ├── TeamDAO.java
│   │   └── StadiumDAO.java
│   │
│   ├── 📁 service/                   # Services métier
│   │   └── DataInitializerService.java
│   │
│   ├── 📁 api/                       # API REST
│   │   ├── ApiHandler.java           # Handler de base
│   │   ├── UserApi.java              # /api/users
│   │   ├── BookingApi.java           # /api/bookings
│   │   ├── TicketApi.java            # /api/tickets
│   │   ├── MatchApi.java             # /api/matches
│   │   ├── TeamApi.java              # /api/teams
│   │   ├── StadiumApi.java           # /api/stadiums
│   │   └── AuthApi.java              # /api/auth
│   │
│   ├── 📁 util/                      # Utilitaires
│   │   └── HibernateUtil.java        # Configuration Hibernate
│   │
│   └── WebServer.java                # Point d'entrée
│
├── 📁 src/main/resources/
│   ├── 📁 static/                    # Frontend
│   │   ├── index.html
│   │   ├── app.js
│   │   ├── api.js
│   │   └── styles.css
│   └── hibernate.cfg.xml             # Config Hibernate
│
├── docker-compose.yml                # MySQL Docker
├── init-db.sql                       # Script d'initialisation
├── pom.xml                           # Dépendances Maven
└── README.md
```

---

## 🚀 Installation

### Prérequis

- Java 17 ou supérieur
- Maven 3.6+
- Docker & Docker Compose

### Étapes

1. **Cloner le projet**
```bash
cd /path/to/project
```

2. **Démarrer MySQL avec Docker**
```bash
docker-compose up -d
```

3. **Compiler et lancer l'application**
```bash
mvn clean compile exec:java -Dexec.mainClass=com.worldcup2030.WebServer
```

4. **Accéder à l'application**
```
http://localhost:8080
```

---

## 📱 Utilisation

### Comptes par défaut

| Rôle | Email | Mot de passe |
|------|-------|--------------|
| Admin | admin@worldcup2030.com | admin123 |
| User | demo@worldcup2030.com | demo123 |

### Workflow de réservation

1. Se connecter avec un compte utilisateur
2. Aller dans la section "Tickets"
3. Sélectionner un match
4. Choisir les billets (catégorie 1, 2 ou 3)
5. Procéder au paiement
6. Consulter la réservation dans "My Bookings"

---

## 🔌 API REST

### Endpoints principaux

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| `POST` | `/api/auth/login` | Connexion |
| `POST` | `/api/auth/register` | Inscription |
| `GET` | `/api/matches` | Liste des matchs |
| `GET` | `/api/stadiums` | Liste des stades |
| `GET` | `/api/teams` | Liste des équipes |
| `GET` | `/api/tickets/match/{id}` | Billets d'un match |
| `POST` | `/api/bookings` | Créer une réservation |
| `GET` | `/api/bookings/user/{id}` | Réservations d'un utilisateur |
| `DELETE` | `/api/bookings/{id}` | Annuler une réservation |
| `GET` | `/api/users` | Liste des utilisateurs (admin) |

### Exemple de requête

```bash
# Créer une réservation
curl -X POST http://localhost:8080/api/bookings \
  -H "Content-Type: application/json" \
  -d '{"userId": 1, "ticketIds": [1, 2, 3]}'
```

---

## 👨‍💻 Auteur

**Abderrahman Ait Karim**  
Projet Java Avancé - 2025/2026

---

## 📄 Licence

Ce projet est développé dans un cadre éducatif.
