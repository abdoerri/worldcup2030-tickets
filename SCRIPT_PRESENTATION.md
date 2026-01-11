# 🎤 Script de Présentation - Système de Billetterie Coupe du Monde 2030

> **Projet :** World Cup 2030 Morocco - Ticket System  
> **Durée estimée :** 10-15 minutes  
> **Auteur :** Abderrahman Ait Karim

---

## 📌 INTRODUCTION (2 min)

### Ce que vous montrez :
> Ouvrir la page d'accueil de l'application dans le navigateur : `http://localhost:8080`

### Ce que vous dites :

> « Bonjour à tous. Aujourd'hui, je vais vous présenter mon projet de Java Avancé : un système de billetterie pour la Coupe du Monde 2030 qui sera organisée au Maroc.
>
> Ce projet met en œuvre plusieurs concepts clés du développement Java moderne :
> - **Hibernate ORM** pour la persistance des données
> - L'architecture **DAO** (Data Access Object) pour l'accès aux données
> - Une **API REST** construite avec le serveur HTTP intégré de Java
> - Le pattern **MVC** côté frontend avec JavaScript
> - **Docker** pour la conteneurisation de la base de données MySQL
>
> Commençons par explorer l'architecture technique du projet. »

---

## 🏗️ PARTIE 1 : ARCHITECTURE DU PROJET (3 min)

### Concept : Structure MVC / Architecture en couches

### Fichiers à montrer :
```
📁 src/main/java/com/worldcup2030/
├── 📁 entity/        ← Modèles (Entités JPA)
├── 📁 dao/           ← Data Access Objects
├── 📁 service/       ← Logique métier
├── 📁 api/           ← Contrôleurs REST
└── 📁 util/          ← Utilitaires (HibernateUtil)
```

### Ce que vous dites :

> « Voici la structure de mon projet. J'ai adopté une **architecture en couches** qui sépare clairement les responsabilités :
>
> - Le dossier **entity** contient les classes modèles annotées avec JPA/Hibernate
> - Le dossier **dao** implémente le pattern DAO pour l'accès aux données
> - Le dossier **service** contient la logique métier
> - Le dossier **api** expose les endpoints REST
> - Le dossier **util** contient les classes utilitaires comme la configuration Hibernate
>
> Cette architecture facilite la maintenance et permet de tester chaque couche indépendamment. »

---

## 🗄️ PARTIE 2 : HIBERNATE & ENTITÉS JPA (3 min)

### Concept : Hibernate ORM, Annotations JPA, Relations entre entités

### Fichier à montrer :
`src/main/java/com/worldcup2030/entity/User.java`

### Ce que vous dites :

> « Regardons maintenant la couche persistance. Voici l'entité **User** qui représente un utilisateur du système.
>
> Vous pouvez voir les annotations Hibernate :
> - `@Entity` déclare cette classe comme une entité JPA
> - `@Table(name = "users")` spécifie le nom de la table en base
> - `@Id` et `@GeneratedValue` définissent la clé primaire auto-incrémentée
> - `@Column` configure les propriétés des colonnes
> - `@Enumerated` permet de persister les énumérations comme USER ou ADMIN
> - `@OneToMany` définit la relation un-à-plusieurs avec les réservations »

### Fichier à montrer :
`src/main/java/com/worldcup2030/entity/Booking.java`

### Ce que vous dites :

> « L'entité **Booking** illustre les relations bidirectionnelles :
> - `@ManyToOne` relie la réservation à un utilisateur
> - `@OneToMany` avec `cascade = CascadeType.ALL` lie les tickets à la réservation
>
> Le cascade permet de sauvegarder automatiquement les tickets associés quand on persiste une réservation. »

---

## 🔧 PARTIE 3 : CONFIGURATION HIBERNATE (2 min)

### Concept : SessionFactory, Configuration Hibernate, Connexion MySQL

### Fichier à montrer :
`src/main/java/com/worldcup2030/util/HibernateUtil.java`

### Ce que vous dites :

> « La classe **HibernateUtil** implémente le pattern Singleton pour la SessionFactory.
>
> Points importants :
> - La SessionFactory est créée une seule fois au démarrage
> - Toutes les entités sont enregistrées avec `addAnnotatedClass()`
> - La configuration se connecte à MySQL via JDBC
> - L'option `hbm2ddl.auto = update` permet à Hibernate de créer ou mettre à jour automatiquement le schéma de la base »

### Fichier à montrer :
`docker-compose.yml`

### Ce que vous dites :

> « La base de données MySQL est conteneurisée avec **Docker Compose**. Cela permet de :
> - Démarrer la base avec une simple commande `docker-compose up`
> - Initialiser automatiquement les tables avec le script `init-db.sql`
> - Persister les données dans un volume Docker »

---

## 📊 PARTIE 4 : PATTERN DAO (2 min)

### Concept : Data Access Object, GenericDAO, Requêtes HQL

### Fichier à montrer :
`src/main/java/com/worldcup2030/dao/GenericDAO.java`

### Ce que vous dites :

> « J'ai implémenté un **DAO générique** qui factorise les opérations CRUD :
> - `save()` persiste une nouvelle entité
> - `update()` met à jour une entité existante
> - `delete()` supprime une entité
> - `findById()` recherche par identifiant
> - `findAll()` récupère toutes les entités
>
> Ce pattern évite la duplication de code. Chaque DAO spécifique hérite de cette classe. »

### Fichier à montrer :
`src/main/java/com/worldcup2030/dao/BookingDAO.java`

### Ce que vous dites :

> « Le **BookingDAO** étend le GenericDAO et ajoute des méthodes spécifiques :
> - `findByUserId()` pour récupérer les réservations d'un utilisateur
> - `findByBookingReference()` pour la recherche par référence
>
> Les requêtes utilisent **HQL** (Hibernate Query Language), un langage orienté objet plus lisible que le SQL natif. »

---

## 🌐 PARTIE 5 : API REST (2 min)

### Concept : HTTP Server Java, Endpoints REST, JSON

### Fichier à montrer :
`src/main/java/com/worldcup2030/api/BookingApi.java`

### Ce que vous dites :

> « L'API REST est construite avec le serveur HTTP intégré de Java.
>
> Cette classe gère les endpoints de réservation :
> - `GET /api/bookings` retourne toutes les réservations
> - `GET /api/bookings/user/{id}` retourne les réservations d'un utilisateur
> - `POST /api/bookings` crée une nouvelle réservation
> - `DELETE /api/bookings/{id}` annule une réservation
>
> Les données sont sérialisées en **JSON** avec la bibliothèque Gson. »

---

## 💻 PARTIE 6 : FRONTEND (2 min)

### Concept : JavaScript, Appels API Fetch, Interface utilisateur

### Fichier à montrer :
`src/main/resources/static/app.js` (fonction `confirmBooking`)

### Ce que vous dites :

> « Le frontend est développé en JavaScript vanilla.
>
> Voici la fonction `confirmBooking()` qui :
> 1. Appelle l'API avec `ApiService.createBooking()`
> 2. Envoie les données en POST au serveur
> 3. Affiche une confirmation à l'utilisateur
> 4. Met à jour l'interface dynamiquement
>
> Toutes les données sont persistées en base de données, pas en localStorage. »

---

## 🎬 PARTIE 7 : DÉMONSTRATION (3 min)

### Ce que vous montrez :
1. L'application dans le navigateur
2. Connexion avec `demo@worldcup2030.com` / `demo123`
3. Réservation d'un billet
4. Vérification dans "My Bookings"
5. Connexion admin et vérification du tableau de bord

### Ce que vous dites :

> « Je vais maintenant faire une démonstration en direct.
>
> Je me connecte avec le compte utilisateur demo...
> Je sélectionne un match et je réserve un billet...
> La réservation est bien enregistrée en base de données...
>
> Maintenant, je me connecte en tant qu'administrateur...
> Dans le tableau de bord admin, je peux voir toutes les réservations...
> Je peux voir les statistiques : nombre d'utilisateurs, réservations, revenus...
>
> Tout fonctionne grâce à l'intégration entre le frontend JavaScript et le backend Java/Hibernate. »

---

## 📌 CONCLUSION (1 min)

### Ce que vous dites :

> « En conclusion, ce projet m'a permis de mettre en pratique :
>
> - **Hibernate ORM** pour le mapping objet-relationnel
> - Le pattern **DAO** pour structurer l'accès aux données
> - Une **API REST** fonctionnelle avec le serveur HTTP Java
> - L'architecture **MVC** pour séparer les responsabilités
> - **Docker** pour la gestion de l'environnement de base de données
>
> Ce système pourrait être étendu avec :
> - Un système de paiement réel
> - L'envoi de billets par email
> - Une application mobile
>
> Je suis maintenant prêt à répondre à vos questions. Merci de votre attention. »

---

## 📋 AIDE-MÉMOIRE RAPIDE

| Concept | Fichier(s) | Ligne clé |
|---------|------------|-----------|
| Hibernate Entity | `entity/User.java` | `@Entity`, `@Table` |
| Relations JPA | `entity/Booking.java` | `@ManyToOne`, `@OneToMany` |
| SessionFactory | `util/HibernateUtil.java` | `Configuration.buildSessionFactory()` |
| DAO Générique | `dao/GenericDAO.java` | `save()`, `findAll()` |
| DAO Spécifique | `dao/BookingDAO.java` | `findByUserId()` |
| API REST | `api/BookingApi.java` | `handleGet()`, `handlePost()` |
| Docker MySQL | `docker-compose.yml` | `image: mysql:8.0` |
| Frontend API | `static/api.js` | `ApiService.createBooking()` |

---

## ⚠️ QUESTIONS POTENTIELLES

**Q: Pourquoi Hibernate plutôt que JDBC ?**
> « Hibernate simplifie le code en éliminant le SQL répétitif et gère automatiquement les relations entre objets. »

**Q: Comment gérez-vous les transactions ?**
> « Hibernate gère les transactions via la Session. Chaque opération DAO utilise try-with-resources pour garantir la fermeture de la session. »

**Q: Pourquoi Docker pour MySQL ?**
> « Docker permet de déployer la base facilement sur n'importe quel environnement sans installation manuelle. Le script init-db.sql est exécuté automatiquement au premier lancement. »

**Q: Le projet est-il sécurisé ?**
> « Pour une version production, il faudrait ajouter : hash des mots de passe avec BCrypt, tokens JWT pour l'authentification, et validation des entrées. »
