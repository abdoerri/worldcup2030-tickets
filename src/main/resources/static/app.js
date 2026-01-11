/* ========================================
   World Cup 2030 Morocco - Enhanced JavaScript
   ======================================== */

// ========================================
// State Management
// ========================================
const state = {
    currentUser: null,
    matches: [],
    stadiums: [],
    teams: [],
    tickets: [],
    bookings: [],
    selectedTickets: []
};

// ========================================
// Sample Data with Team Logos, Lineups, Probabilities & World Cup History
// ========================================
const sampleData = {
    stadiums: [
        { id: 1, name: "Grand Stade de Casablanca", city: "Casablanca", capacity: 93000 },
        { id: 2, name: "Stade Ibn Batouta", city: "Tangier", capacity: 65000 },
        { id: 3, name: "Stade de Marrakech", city: "Marrakech", capacity: 45000 },
        { id: 4, name: "Stade Moulay Abdallah", city: "Rabat", capacity: 52000 },
        { id: 5, name: "Stade de Fès", city: "Fez", capacity: 45000 },
        { id: 6, name: "Stade d'Agadir", city: "Agadir", capacity: 45000 }
    ],
    teams: {
        MAR: {
            country: "Morocco", fifaCode: "MAR", flagEmoji: "🇲🇦", groupName: "A",
            logo: "🇲🇦", color: "#C1272D",
            worldCupTitles: 0,
            bestFinish: "4th Place (2022)",
            appearances: 7,
            lastAppearance: 2022,
            players: [
                { number: 1, name: "Yassine Bounou", position: "GK" },
                { number: 2, name: "Achraf Hakimi", position: "RB" },
                { number: 5, name: "Nayef Aguerd", position: "CB" },
                { number: 6, name: "Romain Saïss", position: "CB" },
                { number: 3, name: "Noussair Mazraoui", position: "LB" },
                { number: 4, name: "Sofyan Amrabat", position: "CDM" },
                { number: 8, name: "Azzedine Ounahi", position: "CM" },
                { number: 7, name: "Hakim Ziyech", position: "RW" },
                { number: 10, name: "Sofiane Boufal", position: "CAM" },
                { number: 17, name: "Youssef En-Nesyri", position: "ST" },
                { number: 19, name: "Abde Ezzalzouli", position: "LW" }
            ]
        },
        ESP: {
            country: "Spain", fifaCode: "ESP", flagEmoji: "🇪🇸", groupName: "A",
            logo: "🇪🇸", color: "#C60B1E",
            worldCupTitles: 1,
            bestFinish: "🏆 Winner (2010)",
            appearances: 16,
            lastAppearance: 2022,
            players: [
                { number: 1, name: "Unai Simón", position: "GK" },
                { number: 2, name: "Dani Carvajal", position: "RB" },
                { number: 4, name: "Pau Torres", position: "CB" },
                { number: 5, name: "Aymeric Laporte", position: "CB" },
                { number: 3, name: "Jordi Alba", position: "LB" },
                { number: 6, name: "Rodri", position: "CDM" },
                { number: 8, name: "Pedri", position: "CM" },
                { number: 10, name: "Gavi", position: "CM" },
                { number: 7, name: "Lamine Yamal", position: "RW" },
                { number: 9, name: "Álvaro Morata", position: "ST" },
                { number: 11, name: "Nico Williams", position: "LW" }
            ]
        },
        POR: {
            country: "Portugal", fifaCode: "POR", flagEmoji: "🇵🇹", groupName: "A",
            logo: "🇵🇹", color: "#006600",
            worldCupTitles: 0,
            bestFinish: "3rd Place (1966)",
            appearances: 8,
            lastAppearance: 2022,
            players: [
                { number: 1, name: "Diogo Costa", position: "GK" },
                { number: 2, name: "João Cancelo", position: "RB" },
                { number: 4, name: "Rúben Dias", position: "CB" },
                { number: 3, name: "Pepe", position: "CB" },
                { number: 5, name: "Nuno Mendes", position: "LB" },
                { number: 6, name: "Vitinha", position: "CM" },
                { number: 8, name: "Bruno Fernandes", position: "CM" },
                { number: 10, name: "Bernardo Silva", position: "RW" },
                { number: 7, name: "Cristiano Ronaldo", position: "ST" },
                { number: 17, name: "Rafael Leão", position: "LW" },
                { number: 11, name: "João Félix", position: "CAM" }
            ]
        },
        FRA: {
            country: "France", fifaCode: "FRA", flagEmoji: "🇫🇷", groupName: "B",
            logo: "🇫🇷", color: "#003399",
            worldCupTitles: 2,
            bestFinish: "🏆 Winner (1998, 2018)",
            appearances: 17,
            lastAppearance: 2022,
            players: [
                { number: 1, name: "Mike Maignan", position: "GK" },
                { number: 2, name: "Benjamin Pavard", position: "RB" },
                { number: 4, name: "Dayot Upamecano", position: "CB" },
                { number: 5, name: "Ibrahima Konaté", position: "CB" },
                { number: 3, name: "Theo Hernandez", position: "LB" },
                { number: 6, name: "Aurélien Tchouaméni", position: "CDM" },
                { number: 8, name: "Antoine Griezmann", position: "CM" },
                { number: 7, name: "Ousmane Dembélé", position: "RW" },
                { number: 10, name: "Kylian Mbappé", position: "ST" },
                { number: 11, name: "Marcus Thuram", position: "LW" },
                { number: 14, name: "Adrien Rabiot", position: "CM" }
            ]
        },
        BRA: {
            country: "Brazil", fifaCode: "BRA", flagEmoji: "🇧🇷", groupName: "B",
            logo: "🇧🇷", color: "#009739",
            worldCupTitles: 5,
            bestFinish: "🏆 Winner (1958, 1962, 1970, 1994, 2002)",
            appearances: 22,
            lastAppearance: 2022,
            players: [
                { number: 1, name: "Alisson", position: "GK" },
                { number: 2, name: "Danilo", position: "RB" },
                { number: 4, name: "Marquinhos", position: "CB" },
                { number: 3, name: "Thiago Silva", position: "CB" },
                { number: 6, name: "Alex Sandro", position: "LB" },
                { number: 5, name: "Casemiro", position: "CDM" },
                { number: 7, name: "Lucas Paquetá", position: "CM" },
                { number: 11, name: "Raphinha", position: "RW" },
                { number: 10, name: "Neymar Jr", position: "CAM" },
                { number: 9, name: "Richarlison", position: "ST" },
                { number: 20, name: "Vinicius Jr", position: "LW" }
            ]
        },
        ARG: {
            country: "Argentina", fifaCode: "ARG", flagEmoji: "🇦🇷", groupName: "B",
            logo: "🇦🇷", color: "#74ACDF",
            worldCupTitles: 3,
            bestFinish: "🏆 Winner (1978, 1986, 2022)",
            appearances: 18,
            lastAppearance: 2022,
            players: [
                { number: 23, name: "Emiliano Martínez", position: "GK" },
                { number: 26, name: "Nahuel Molina", position: "RB" },
                { number: 13, name: "Cristian Romero", position: "CB" },
                { number: 19, name: "Nicolás Otamendi", position: "CB" },
                { number: 3, name: "Nicolás Tagliafico", position: "LB" },
                { number: 5, name: "Leandro Paredes", position: "CDM" },
                { number: 7, name: "Rodrigo De Paul", position: "CM" },
                { number: 20, name: "Enzo Fernández", position: "CM" },
                { number: 10, name: "Lionel Messi", position: "RW" },
                { number: 9, name: "Julián Álvarez", position: "ST" },
                { number: 11, name: "Ángel Di María", position: "LW" }
            ]
        },
        GER: {
            country: "Germany", fifaCode: "GER", flagEmoji: "🇩🇪", groupName: "C",
            logo: "🇩🇪", color: "#000000",
            worldCupTitles: 4,
            bestFinish: "🏆 Winner (1954, 1974, 1990, 2014)",
            appearances: 20,
            lastAppearance: 2022,
            players: [
                { number: 1, name: "Manuel Neuer", position: "GK" },
                { number: 2, name: "Joshua Kimmich", position: "RB" },
                { number: 4, name: "Jonathan Tah", position: "CB" },
                { number: 5, name: "Antonio Rüdiger", position: "CB" },
                { number: 3, name: "David Raum", position: "LB" },
                { number: 6, name: "Toni Kroos", position: "CDM" },
                { number: 8, name: "İlkay Gündoğan", position: "CM" },
                { number: 10, name: "Jamal Musiala", position: "CAM" },
                { number: 7, name: "Kai Havertz", position: "RW" },
                { number: 9, name: "Niclas Füllkrug", position: "ST" },
                { number: 17, name: "Florian Wirtz", position: "LW" }
            ]
        },
        ENG: {
            country: "England", fifaCode: "ENG", flagEmoji: "🏴󠁧󠁢󠁥󠁮󠁧󠁿", groupName: "C",
            logo: "🏴󠁧󠁢󠁥󠁮󠁧󠁿", color: "#FFFFFF",
            worldCupTitles: 1,
            bestFinish: "🏆 Winner (1966)",
            appearances: 16,
            lastAppearance: 2022,
            players: [
                { number: 1, name: "Jordan Pickford", position: "GK" },
                { number: 2, name: "Kyle Walker", position: "RB" },
                { number: 5, name: "John Stones", position: "CB" },
                { number: 6, name: "Harry Maguire", position: "CB" },
                { number: 3, name: "Luke Shaw", position: "LB" },
                { number: 4, name: "Declan Rice", position: "CDM" },
                { number: 8, name: "Jude Bellingham", position: "CM" },
                { number: 7, name: "Bukayo Saka", position: "RW" },
                { number: 10, name: "Phil Foden", position: "CAM" },
                { number: 9, name: "Harry Kane", position: "ST" },
                { number: 11, name: "Marcus Rashford", position: "LW" }
            ]
        }
    },
    // Group Stage matches have real teams, Knockout matches show placeholders
    matches: [
        // GROUP STAGE - Real Teams
        {
            id: 1, matchNumber: 1,
            homeTeam: "MAR", awayTeam: "ESP",
            stadium: { id: 1, name: "Grand Stade de Casablanca", city: "Casablanca" },
            matchDate: "2030-06-13T21:00:00",
            matchPhase: "GROUP_STAGE",
            probabilities: { home: 35, draw: 28, away: 37 },
            availableTickets: 30
        },
        {
            id: 2, matchNumber: 2,
            homeTeam: "POR", awayTeam: "FRA",
            stadium: { id: 2, name: "Stade Ibn Batouta", city: "Tangier" },
            matchDate: "2030-06-14T18:00:00",
            matchPhase: "GROUP_STAGE",
            probabilities: { home: 32, draw: 26, away: 42 },
            availableTickets: 30
        },
        {
            id: 3, matchNumber: 3,
            homeTeam: "BRA", awayTeam: "ARG",
            stadium: { id: 1, name: "Grand Stade de Casablanca", city: "Casablanca" },
            matchDate: "2030-06-15T21:00:00",
            matchPhase: "GROUP_STAGE",
            probabilities: { home: 38, draw: 27, away: 35 },
            availableTickets: 30
        },
        {
            id: 4, matchNumber: 4,
            homeTeam: "GER", awayTeam: "ENG",
            stadium: { id: 3, name: "Stade de Marrakech", city: "Marrakech" },
            matchDate: "2030-06-16T18:00:00",
            matchPhase: "GROUP_STAGE",
            probabilities: { home: 36, draw: 28, away: 36 },
            availableTickets: 30
        },
        {
            id: 5, matchNumber: 5,
            homeTeam: "MAR", awayTeam: "POR",
            stadium: { id: 4, name: "Stade Moulay Abdallah", city: "Rabat" },
            matchDate: "2030-06-18T21:00:00",
            matchPhase: "GROUP_STAGE",
            probabilities: { home: 40, draw: 30, away: 30 },
            availableTickets: 30
        },
        // KNOCKOUT STAGE - Placeholder Teams (Winner/Runner-up format)
        {
            id: 6, matchNumber: 49,
            homeTeam: null, awayTeam: null,
            knockoutLabel: { home: "Winner Group A", away: "Runner-up Group B" },
            stadium: { id: 1, name: "Grand Stade de Casablanca", city: "Casablanca" },
            matchDate: "2030-06-28T18:00:00",
            matchPhase: "ROUND_OF_16",
            probabilities: { home: 50, draw: 0, away: 50 },
            availableTickets: 30
        },
        {
            id: 7, matchNumber: 50,
            homeTeam: null, awayTeam: null,
            knockoutLabel: { home: "Winner Group B", away: "Runner-up Group A" },
            stadium: { id: 2, name: "Stade Ibn Batouta", city: "Tangier" },
            matchDate: "2030-06-28T21:00:00",
            matchPhase: "ROUND_OF_16",
            probabilities: { home: 50, draw: 0, away: 50 },
            availableTickets: 30
        },
        {
            id: 8, matchNumber: 57,
            homeTeam: null, awayTeam: null,
            knockoutLabel: { home: "Winner Match 49", away: "Winner Match 50" },
            stadium: { id: 1, name: "Grand Stade de Casablanca", city: "Casablanca" },
            matchDate: "2030-07-05T21:00:00",
            matchPhase: "QUARTER_FINAL",
            probabilities: { home: 50, draw: 0, away: 50 },
            availableTickets: 30
        },
        {
            id: 9, matchNumber: 61,
            homeTeam: null, awayTeam: null,
            knockoutLabel: { home: "Winner QF1", away: "Winner QF2" },
            stadium: { id: 1, name: "Grand Stade de Casablanca", city: "Casablanca" },
            matchDate: "2030-07-09T21:00:00",
            matchPhase: "SEMI_FINAL",
            probabilities: { home: 50, draw: 0, away: 50 },
            availableTickets: 30
        },
        {
            id: 10, matchNumber: 64,
            homeTeam: null, awayTeam: null,
            knockoutLabel: { home: "Winner SF1", away: "Winner SF2" },
            stadium: { id: 1, name: "Grand Stade de Casablanca", city: "Casablanca" },
            matchDate: "2030-07-13T21:00:00",
            matchPhase: "FINAL",
            probabilities: { home: 50, draw: 0, away: 50 },
            availableTickets: 30
        }
    ],
    users: [
        { id: 1, firstName: "Ahmed", lastName: "Benali", email: "demo@worldcup2030.com", password: "demo123", role: "user" },
        { id: 2, firstName: "Admin", lastName: "Morocco", email: "admin@worldcup2030.com", password: "admin123", role: "admin" }
    ],
    // Head-to-head history between teams
    headToHead: {
        "MAR-ESP": {
            totalMatches: 3,
            homeWins: 1, awayWins: 1, draws: 1,
            lastMeetings: [
                { date: "2022-12-06", competition: "World Cup 2022", score: "0-0 (3-0 pen)", venue: "Qatar" },
                { date: "2018-06-25", competition: "World Cup 2018", score: "2-2", venue: "Russia" },
                { date: "1961-03-15", competition: "Friendly", score: "1-0", venue: "Morocco" }
            ],
            significance: "Morocco made history by eliminating Spain in the 2022 World Cup Round of 16 via penalty shootout."
        },
        "MAR-POR": {
            totalMatches: 2,
            homeWins: 1, awayWins: 1, draws: 0,
            lastMeetings: [
                { date: "2022-12-10", competition: "World Cup 2022 QF", score: "1-0", venue: "Qatar" },
                { date: "2018-06-20", competition: "World Cup 2018", score: "0-1", venue: "Russia" }
            ],
            significance: "Morocco's 1-0 victory in 2022 made them the first African nation to reach a World Cup semi-final."
        },
        "POR-FRA": {
            totalMatches: 28,
            homeWins: 9, awayWins: 12, draws: 7,
            lastMeetings: [
                { date: "2021-06-23", competition: "Euro 2020", score: "2-2", venue: "Hungary" },
                { date: "2016-07-10", competition: "Euro 2016 Final", score: "1-0 (aet)", venue: "France" },
                { date: "2014-10-11", competition: "Friendly", score: "2-1", venue: "France" }
            ],
            significance: "Portugal defeated France in the Euro 2016 Final with Eder's extra-time goal."
        },
        "BRA-ARG": {
            totalMatches: 111,
            homeWins: 46, awayWins: 40, draws: 25,
            lastMeetings: [
                { date: "2021-07-10", competition: "Copa America Final", score: "0-1", venue: "Brazil" },
                { date: "2021-09-05", competition: "WC Qualifiers", score: "0-0", venue: "Brazil" },
                { date: "2019-07-02", competition: "Copa America SF", score: "2-0", venue: "Brazil" }
            ],
            significance: "The 'Superclásico de las Américas' - one of football's greatest rivalries spanning over 100 years."
        },
        "GER-ENG": {
            totalMatches: 37,
            homeWins: 15, awayWins: 15, draws: 7,
            lastMeetings: [
                { date: "2021-06-29", competition: "Euro 2020 R16", score: "0-2", venue: "England" },
                { date: "2017-03-22", competition: "Friendly", score: "0-1", venue: "Germany" },
                { date: "2010-06-27", competition: "World Cup 2010", score: "4-1", venue: "South Africa" }
            ],
            significance: "Historic rivalry dating back to 1930, featuring legendary World Cup encounters including the 1966 Final."
        }
    }
};

// Generate tickets
function generateTickets() {
    const tickets = [];
    let ticketId = 1;
    const categories = [
        { name: "CATEGORY_1", code: "CAT1", price: 500 },
        { name: "CATEGORY_2", code: "CAT2", price: 300 },
        { name: "CATEGORY_3", code: "CAT3", price: 150 }
    ];
    const sections = ["A", "B", "C", "D"];

    sampleData.matches.forEach(match => {
        categories.forEach(category => {
            for (let i = 1; i <= 10; i++) {
                tickets.push({
                    id: ticketId++,
                    matchId: match.id,
                    section: sections[i % 4],
                    row: String(Math.floor(i / 4) + 1),
                    seatNumber: String(i),
                    category: category.name,
                    categoryCode: category.code,
                    price: category.price,
                    status: "AVAILABLE"
                });
            }
        });
    });
    return tickets;
}

sampleData.tickets = generateTickets();

// ========================================
// Initialize Application
// ========================================
document.addEventListener('DOMContentLoaded', async () => {
    // Check if backend API is available
    await checkApiAvailability();

    // Load data (from API if available, otherwise mock data)
    await loadData();

    setupEventListeners();
    renderHome();
    checkStoredUser();
});

async function loadData() {
    if (window.USE_API) {
        try {
            // Load from backend API
            console.log('📡 Loading data from API...');

            const [teamsResponse, matchesResponse] = await Promise.all([
                ApiService.getTeams(),
                ApiService.getMatches()
            ]);

            // Transform API data to match frontend format
            state.stadiums = sampleData.stadiums; // Keep sample stadiums for now

            // Build teams lookup from API
            const apiTeams = {};
            teamsResponse.forEach(team => {
                apiTeams[team.fifaCode] = {
                    ...team,
                    logo: team.flagEmoji,
                    color: '#C1272D',
                    players: sampleData.teams[team.fifaCode]?.players || []
                };
            });
            state.teams = apiTeams;

            // Transform matches
            state.matches = matchesResponse.map(m => ({
                ...m,
                homeTeamData: m.homeTeam ? (apiTeams[m.homeTeam.fifaCode] || m.homeTeam) : null,
                awayTeamData: m.awayTeam ? (apiTeams[m.awayTeam.fifaCode] || m.awayTeam) : null,
                stadium: m.stadium
            }));

            // Tickets will be loaded per-match when needed
            state.tickets = [];

            console.log('✅ Data loaded from API!');
        } catch (error) {
            console.error('Failed to load from API, using mock data:', error);
            loadMockData();
        }
    } else {
        loadMockData();
    }

    updateStats();
}

function loadMockData() {
    console.log('📦 Using mock data...');
    state.stadiums = sampleData.stadiums;
    state.matches = sampleData.matches.map(m => ({
        ...m,
        homeTeamData: sampleData.teams[m.homeTeam],
        awayTeamData: sampleData.teams[m.awayTeam]
    }));
    state.tickets = sampleData.tickets;
}

function updateStats() {
    document.getElementById('totalMatches').textContent = state.matches.length;
    document.getElementById('totalStadiums').textContent = state.stadiums.length;
    document.getElementById('totalTeams').textContent = Object.keys(sampleData.teams).length;
    document.getElementById('availableTickets').textContent =
        state.tickets.filter(t => t.status === 'AVAILABLE').length;
}

async function checkStoredUser() {
    // Use sessionStorage instead of localStorage for security
    // Session clears when browser closes
    const storedUser = sessionStorage.getItem('worldcup2030_user');
    if (storedUser) {
        const user = JSON.parse(storedUser);
        // Validate user has an id (required for API calls)
        if (user && user.id) {
            // Validate session against database
            if (window.USE_API) {
                try {
                    // Verify user still exists in database
                    const dbUser = await fetch(`/api/users/${user.id}`).then(r => r.ok ? r.json() : null);
                    if (dbUser) {
                        state.currentUser = { ...user, ...dbUser };
                        updateUserUI();
                        console.log('✅ Session validated against database');
                    } else {
                        // User no longer exists in database
                        console.warn('User not found in database, clearing session');
                        sessionStorage.removeItem('worldcup2030_user');
                    }
                } catch (error) {
                    console.warn('Could not validate session, using cached data:', error);
                    state.currentUser = user;
                    updateUserUI();
                }
            } else {
                state.currentUser = user;
                updateUserUI();
            }
        } else {
            // Invalid stored user, clear it
            console.warn('Stored user missing ID, clearing session');
            sessionStorage.removeItem('worldcup2030_user');
        }
    }
    // Bookings are now fetched from database API, not sessionStorage
}

function renderHome() {
    renderTeams();
    renderStadiums();
    renderAllMatches();
    populateMatchSelect();
}

// ========================================
// Event Listeners
// ========================================
function setupEventListeners() {
    // Navigation
    document.querySelectorAll('.nav-link').forEach(link => {
        link.addEventListener('click', (e) => {
            e.preventDefault();
            const section = e.target.closest('.nav-link').dataset.section;
            showSection(section);
        });
    });

    // Auth buttons
    document.getElementById('loginBtn').addEventListener('click', () => openModal('loginModal'));
    document.getElementById('registerBtn').addEventListener('click', () => openModal('registerModal'));
    document.getElementById('logoutBtn').addEventListener('click', logout);

    // Admin tabs
    setupAdminTabs();

    // Forms
    document.getElementById('loginForm').addEventListener('submit', handleLogin);
    document.getElementById('registerForm').addEventListener('submit', handleRegister);

    // Filter tabs
    document.querySelectorAll('.filter-tab').forEach(tab => {
        tab.addEventListener('click', (e) => {
            document.querySelectorAll('.filter-tab').forEach(t => t.classList.remove('active'));
            e.target.classList.add('active');
            filterMatches(e.target.dataset.filter);
        });
    });

    // Match select
    document.getElementById('matchSelect').addEventListener('change', (e) => {
        if (e.target.value) {
            loadTicketsForMatch(parseInt(e.target.value));
        }
    });

    // Confirm booking
    document.getElementById('confirmBookingBtn').addEventListener('click', confirmBooking);

    // Close modals on outside click
    document.querySelectorAll('.modal').forEach(modal => {
        modal.addEventListener('click', (e) => {
            if (e.target === modal) closeModal(modal.id);
        });
    });
}

// ========================================
// Navigation
// ========================================
function showSection(sectionName) {
    // Check admin access
    if (sectionName === 'admin' && (!state.currentUser || state.currentUser.role !== 'admin')) {
        showToast('Admin access required', 'error');
        return;
    }

    document.querySelectorAll('.nav-link').forEach(link => {
        link.classList.toggle('active', link.dataset.section === sectionName);
    });

    document.querySelectorAll('.section').forEach(section => {
        section.classList.remove('active');
    });
    document.getElementById(sectionName + 'Section').classList.add('active');

    // Toggle between customer and admin navigation
    const mainNav = document.querySelector('.main-nav');
    const userSection = document.querySelector('.user-section');
    const adminExitBtn = document.getElementById('exitAdminBtn');

    if (sectionName === 'admin') {
        // Hide entire navigation in admin mode (admin is not a customer)
        if (mainNav) mainNav.style.display = 'none';
        if (userSection) userSection.style.display = 'none';
        if (adminExitBtn) adminExitBtn.style.display = 'inline-flex';
        document.body.classList.add('admin-mode');
    } else {
        // Show navigation when not in admin mode
        if (mainNav) mainNav.style.display = '';
        if (userSection) userSection.style.display = '';
        if (adminExitBtn) adminExitBtn.style.display = 'none';
        document.body.classList.remove('admin-mode');
        // Make sure admin link visibility is correct
        const adminLink = document.getElementById('adminNavLink');
        if (adminLink) {
            adminLink.style.display = state.currentUser?.role === 'admin' ? '' : 'none';
        }
    }

    if (sectionName === 'bookings' && state.currentUser) {
        renderBookings();
    }
    if (sectionName === 'admin' && state.currentUser?.role === 'admin') {
        renderAdminDashboard();
    }
}

function exitAdminMode() {
    showSection('home');
}

// ========================================
// Rendering Functions
// ========================================
function renderTeams() {
    const container = document.getElementById('teamsGrid');
    const teams = Object.values(sampleData.teams);

    container.innerHTML = teams.map(team => `
        <div class="team-history-card">
            <div class="team-history-flag">${team.flagEmoji}</div>
            <div class="team-history-info">
                <h4 class="team-history-name">${team.country}</h4>
                <div class="team-history-code">${team.fifaCode} • Group ${team.groupName}</div>
            </div>
            <div class="team-history-stats">
                <div class="history-stat">
                    <span class="history-stat-value">${team.worldCupTitles > 0 ? '🏆'.repeat(team.worldCupTitles) : '—'}</span>
                    <span class="history-stat-label">${team.worldCupTitles} Title${team.worldCupTitles !== 1 ? 's' : ''}</span>
                </div>
                <div class="history-stat">
                    <span class="history-stat-value">${team.appearances}</span>
                    <span class="history-stat-label">Appearances</span>
                </div>
            </div>
            <div class="team-best-finish">${team.bestFinish}</div>
        </div>
    `).join('');
}

function renderAllMatches(filter = 'all') {
    const container = document.getElementById('matchesList');
    let matches = state.matches;

    if (filter !== 'all') {
        matches = matches.filter(m => m.matchPhase === filter);
    }

    container.innerHTML = matches.map(match => createMatchCard(match)).join('');
}

function filterMatches(filter) {
    renderAllMatches(filter);
}

function createMatchCard(match) {
    const date = new Date(match.matchDate);
    const formattedDate = date.toLocaleDateString('en-US', {
        weekday: 'short', month: 'short', day: 'numeric'
    });
    const formattedTime = date.toLocaleTimeString('en-US', {
        hour: '2-digit', minute: '2-digit'
    });

    // Generate random barcode heights
    const barcode = Array.from({ length: 8 }, () => Math.floor(Math.random() * 20) + 10);

    // Check if this is a knockout match with placeholder teams
    const isKnockout = match.homeTeam === null || match.awayTeam === null;

    let homeDisplay, awayDisplay;
    if (isKnockout) {
        homeDisplay = {
            flagEmoji: '❓',
            country: match.knockoutLabel.home,
            fifaCode: 'TBD'
        };
        awayDisplay = {
            flagEmoji: '❓',
            country: match.knockoutLabel.away,
            fifaCode: 'TBD'
        };
    } else {
        homeDisplay = match.homeTeamData;
        awayDisplay = match.awayTeamData;
    }

    return `
        <div class="match-card ${isKnockout ? 'knockout-match' : ''}" data-match-id="${match.id}">
            <div class="ticket-body">
                <div class="match-card-header">
                    <span class="match-phase">${match.matchPhase.replace(/_/g, ' ')}</span>
                    <span class="match-number">#${match.matchNumber}</span>
                </div>
                <div class="match-teams-container">
                    <div class="match-teams">
                        <div class="team">
                            <div class="team-logo-container ${isKnockout ? 'tbd' : ''}">
                                <span class="team-flag">${homeDisplay.flagEmoji}</span>
                            </div>
                            <div class="team-name">${homeDisplay.country}</div>
                            <div class="team-code">${homeDisplay.fifaCode}</div>
                        </div>
                        <div class="match-vs">
                            <span class="vs-text">VS</span>
                            ${!isKnockout ? `
                            <div class="win-probability">
                                <span class="prob-home">${match.probabilities.home}%</span>
                                <span class="prob-away">${match.probabilities.away}%</span>
                            </div>
                            ` : `<div class="knockout-badge">🏆 Knockout</div>`}
                        </div>
                        <div class="team">
                            <div class="team-logo-container ${isKnockout ? 'tbd' : ''}">
                                <span class="team-flag">${awayDisplay.flagEmoji}</span>
                            </div>
                            <div class="team-name">${awayDisplay.country}</div>
                            <div class="team-code">${awayDisplay.fifaCode}</div>
                        </div>
                    </div>
                </div>
                <div class="match-info">
                    <div class="match-info-item">
                        <span>🏟️</span><span>${match.stadium.city}</span>
                    </div>
                    <div class="match-info-item">
                        <span>📅</span><span>${formattedDate}</span>
                    </div>
                    <div class="match-info-item">
                        <span>🕐</span><span>${formattedTime}</span>
                    </div>
                </div>
                <div class="match-footer">
                    <div class="tickets-available">
                        <span>🎟️</span>
                        <span>${match.availableTickets} tickets</span>
                    </div>
                    <div class="match-actions">
                        ${!isKnockout ? `
                        <button class="btn-details" onclick="showMatchDetails(${match.id})">
                            Details
                        </button>
                        ` : ''}
                        <button class="btn-book-ticket" onclick="selectMatchForBooking(${match.id})">
                            <span>🎫</span> Book
                        </button>
                    </div>
                </div>
            </div>
            <div class="ticket-stub">
                <span class="stub-icon">🎫</span>
                <span class="stub-text">ADMIT ONE</span>
                <div class="stub-barcode">
                    ${barcode.map(h => `<span style="height:${h}px;"></span>`).join('')}
                </div>
            </div>
        </div>
    `;
}

function showMatchDetails(matchId) {
    const match = state.matches.find(m => m.id === matchId);
    if (!match) return;

    const home = match.homeTeamData;
    const away = match.awayTeamData;

    // Can't show details for knockout matches with TBD teams
    if (!home || !away) {
        showToast('Match details will be available once teams are determined', 'info');
        return;
    }

    const date = new Date(match.matchDate);

    // Get head-to-head history
    const h2hKey = `${home.fifaCode}-${away.fifaCode}`;
    const h2hKeyReverse = `${away.fifaCode}-${home.fifaCode}`;
    const h2h = sampleData.headToHead[h2hKey] || sampleData.headToHead[h2hKeyReverse] || null;

    const content = `
        <div class="detail-teams">
            <div class="detail-team">
                <span class="detail-team-flag">${home.flagEmoji}</span>
                <div class="detail-team-name">${home.country}</div>
                <div class="team-code">${home.fifaCode}</div>
            </div>
            <div class="detail-vs">
                <span class="detail-vs-text">VS</span>
                <div class="detail-probabilities">
                    <span class="prob-badge home">${match.probabilities.home}%</span>
                    <span class="prob-badge draw">${match.probabilities.draw}%</span>
                    <span class="prob-badge away">${match.probabilities.away}%</span>
                </div>
            </div>
            <div class="detail-team">
                <span class="detail-team-flag">${away.flagEmoji}</span>
                <div class="detail-team-name">${away.country}</div>
                <div class="team-code">${away.fifaCode}</div>
            </div>
        </div>

        <div class="match-info" style="justify-content: center; background: transparent; padding: 1rem 0;">
            <div class="match-info-item">
                <span>🏟️</span><span>${match.stadium.name}</span>
            </div>
            <div class="match-info-item">
                <span>📅</span><span>${date.toLocaleDateString('en-US', { weekday: 'long', month: 'long', day: 'numeric', year: 'numeric' })}</span>
            </div>
            <div class="match-info-item">
                <span>🕐</span><span>${date.toLocaleTimeString('en-US', { hour: '2-digit', minute: '2-digit' })}</span>
            </div>
        </div>
        
        ${h2h ? `
        <div class="h2h-section">
            <div class="lineup-title">⚔️ Head-to-Head History</div>
            <div class="h2h-stats">
                <div class="h2h-stat">
                    <span class="h2h-value">${h2h.totalMatches}</span>
                    <span class="h2h-label">Matches</span>
                </div>
                <div class="h2h-stat home-wins">
                    <span class="h2h-value">${h2h.homeWins}</span>
                    <span class="h2h-label">${home.fifaCode} Wins</span>
                </div>
                <div class="h2h-stat draws">
                    <span class="h2h-value">${h2h.draws}</span>
                    <span class="h2h-label">Draws</span>
                </div>
                <div class="h2h-stat away-wins">
                    <span class="h2h-value">${h2h.awayWins}</span>
                    <span class="h2h-label">${away.fifaCode} Wins</span>
                </div>
            </div>
            <div class="h2h-meetings">
                <h5>📜 Recent Meetings</h5>
                ${h2h.lastMeetings.map(m => `
                    <div class="h2h-meeting">
                        <span class="meeting-date">${m.date}</span>
                        <span class="meeting-competition">${m.competition}</span>
                        <span class="meeting-score">${m.score}</span>
                    </div>
                `).join('')}
            </div>
            <div class="h2h-significance">
                <strong>📌 Historical Significance:</strong> ${h2h.significance}
            </div>
        </div>
        ` : ''}

        <div class="lineup-section">
            <div class="lineup-title">📋 Starting Lineups</div>
            <div class="lineups-container">
                <div class="lineup-team">
                    <h4>${home.flagEmoji} ${home.country}</h4>
                    <div class="lineup-players">
                        ${home.players.map(p => `
                            <div class="lineup-player">
                                <span class="player-number">${p.number}</span>
                                <span class="player-name">${p.name}</span>
                                <span class="player-position">${p.position}</span>
                            </div>
                        `).join('')}
                    </div>
                </div>
                <div class="lineup-team">
                    <h4>${away.flagEmoji} ${away.country}</h4>
                    <div class="lineup-players">
                        ${away.players.map(p => `
                            <div class="lineup-player">
                                <span class="player-number">${p.number}</span>
                                <span class="player-name">${p.name}</span>
                                <span class="player-position">${p.position}</span>
                            </div>
                        `).join('')}
                    </div>
                </div>
            </div>
        </div>

        <div style="text-align: center; margin-top: 2rem;">
            <button class="btn-book-ticket" onclick="closeModal('matchDetailModal'); selectMatchForBooking(${match.id});" style="font-size: 1rem; padding: 0.75rem 2rem;">
                <span>🎫</span> Book Tickets for this Match
            </button>
        </div>
    `;

    document.getElementById('matchDetailContent').innerHTML = content;
    openModal('matchDetailModal');
}

function renderStadiums() {
    const container = document.getElementById('stadiumsGrid');
    container.innerHTML = state.stadiums.map(stadium => `
        <div class="stadium-card">
            <span class="stadium-icon">🏟️</span>
            <h4>${stadium.name}</h4>
            <div class="stadium-city">📍 ${stadium.city}</div>
            <div class="stadium-capacity">Capacity: ${stadium.capacity.toLocaleString()}</div>
        </div>
    `).join('');
}

function populateMatchSelect() {
    const select = document.getElementById('matchSelect');
    // Only show group stage matches with real teams for booking
    const bookableMatches = state.matches.filter(m => m.homeTeamData && m.awayTeamData);

    const options = bookableMatches.map(match => {
        const home = match.homeTeamData;
        const away = match.awayTeamData;
        return `<option value="${match.id}">
            ${home.flagEmoji} ${home.fifaCode} vs ${away.fifaCode} ${away.flagEmoji} - ${match.stadium.city}
        </option>`;
    }).join('');
    select.innerHTML = '<option value="">-- Choose a match --</option>' + options;

    // Add change event listener
    select.addEventListener('change', (e) => {
        const matchId = parseInt(e.target.value);
        if (matchId) {
            loadTicketsForMatch(matchId);
        }
    });
}

async function loadTicketsForMatch(matchId) {
    const container = document.getElementById('ticketsGrid');

    // Show loading state
    container.innerHTML = `<div class="empty-state"><div class="empty-ticket"><span>⏳</span></div><p>Loading tickets...</p></div>`;

    try {
        // Fetch tickets from API if available
        if (window.USE_API) {
            const apiTickets = await ApiService.getTicketsForMatch(matchId);
            // Add to state.tickets (avoid duplicates)
            apiTickets.forEach(ticket => {
                const existingIndex = state.tickets.findIndex(t => t.id === ticket.id);
                if (existingIndex >= 0) {
                    state.tickets[existingIndex] = ticket;
                } else {
                    state.tickets.push(ticket);
                }
            });
        }
    } catch (error) {
        console.error('Failed to load tickets from API:', error);
    }

    const matchTickets = state.tickets.filter(t => t.matchId === matchId && t.status === 'AVAILABLE');

    if (matchTickets.length === 0) {
        container.innerHTML = `
            <div class="empty-state">
                <div class="empty-ticket"><span>😢</span></div>
                <p>No tickets available for this match</p>
            </div>
        `;
        return;
    }

    const categories = ['CATEGORY_1', 'CATEGORY_2', 'CATEGORY_3'];
    const categoryLabels = {
        'CATEGORY_1': '🌟 Premium - $500',
        'CATEGORY_2': '✨ Standard - $300',
        'CATEGORY_3': '🎫 Economy - $150'
    };

    let html = '';
    categories.forEach(category => {
        const categoryTickets = matchTickets.filter(t => t.category === category);
        if (categoryTickets.length > 0) {
            html += `<h4 style="grid-column: 1/-1; margin: 1rem 0 0.5rem; color: var(--morocco-gold);">
                ${categoryLabels[category]}
            </h4>`;
            html += categoryTickets.slice(0, 4).map(ticket => createTicketCard(ticket)).join('');
        }
    });

    container.innerHTML = html;
    state.selectedTickets = [];
}

function createTicketCard(ticket) {
    return `
        <div class="ticket-card" id="ticket-${ticket.id}" onclick="toggleTicketSelection(${ticket.id})">
            <div class="ticket-header">
                <span class="ticket-category ${ticket.category.toLowerCase()}">${ticket.categoryCode}</span>
                <span class="ticket-price">$${ticket.price}</span>
            </div>
            <div class="ticket-details">
                Section ${ticket.section} • Row ${ticket.row} • Seat ${ticket.seatNumber}
            </div>
            <div class="ticket-actions">
                <button class="btn btn-primary btn-sm btn-full" onclick="event.stopPropagation(); addToBooking(${ticket.id})">
                    🎫 Select Ticket
                </button>
            </div>
        </div>
    `;
}

function toggleTicketSelection(ticketId) {
    const ticketCard = document.getElementById(`ticket-${ticketId}`);
    const index = state.selectedTickets.indexOf(ticketId);

    if (index > -1) {
        state.selectedTickets.splice(index, 1);
        ticketCard.classList.remove('selected');
    } else {
        if (state.selectedTickets.length >= 4) {
            showToast('Maximum 4 tickets per booking', 'error');
            return;
        }
        state.selectedTickets.push(ticketId);
        ticketCard.classList.add('selected');
    }
}

function addToBooking(ticketId) {
    if (!state.currentUser) {
        showToast('Please login to book tickets', 'info');
        openModal('loginModal');
        return;
    }
    state.selectedTickets = [ticketId];
    showBookingModal();
}

function selectMatchForBooking(matchId) {
    if (!state.currentUser) {
        showToast('Please login to book tickets', 'info');
        openModal('loginModal');
        return;
    }
    showSection('tickets');
    document.getElementById('matchSelect').value = matchId;
    loadTicketsForMatch(matchId);
}

function showBookingModal() {
    if (state.selectedTickets.length === 0) {
        showToast('Please select at least one ticket', 'error');
        return;
    }

    const tickets = state.selectedTickets.map(id => {
        const ticket = state.tickets.find(t => t.id === id);
        const match = state.matches.find(m => m.id === ticket.matchId);
        return { ...ticket, match };
    });

    const total = tickets.reduce((sum, t) => sum + t.price, 0);
    const match = tickets[0].match;

    // Handle knockout matches with null teams
    const matchLabel = match.homeTeamData ?
        `${match.homeTeamData.flagEmoji} ${match.homeTeamData.fifaCode} vs ${match.awayTeamData.fifaCode} ${match.awayTeamData.flagEmoji}` :
        `${match.knockoutLabel.home} vs ${match.knockoutLabel.away}`;

    // Store booking data for later steps
    state.pendingBooking = { tickets, total, match, matchLabel };

    document.getElementById('bookingSummary').innerHTML = `
        <div style="text-align: center; margin-bottom: 1.5rem;">
            <h4 style="margin-bottom: 0.5rem;">
                ${matchLabel}
            </h4>
            <p style="color: var(--text-secondary);">
                📍 ${match.stadium.city} • 📅 ${new Date(match.matchDate).toLocaleDateString()}
            </p>
        </div>
        ${tickets.map(ticket => `
            <div class="summary-item">
                <span>🎫 ${ticket.categoryCode} - Section ${ticket.section}, Seat ${ticket.seatNumber}</span>
                <span style="color: var(--morocco-gold);">$${ticket.price}</span>
            </div>
        `).join('')}
        <div class="summary-total">
            <span>Total</span>
            <span>$${total}</span>
        </div>
    `;

    // Reset to step 1 and generate captcha
    goToStep(1);
    generateCaptcha();
    openModal('bookingModal');
}

// Captcha state
let captchaAnswer = 0;

function generateCaptcha() {
    const operations = ['+', '-', '×'];
    const op = operations[Math.floor(Math.random() * 2)]; // Only + and - for simplicity
    let a, b;

    if (op === '+') {
        a = Math.floor(Math.random() * 20) + 1;
        b = Math.floor(Math.random() * 20) + 1;
        captchaAnswer = a + b;
    } else {
        a = Math.floor(Math.random() * 20) + 10;
        b = Math.floor(Math.random() * 10) + 1;
        captchaAnswer = a - b;
    }

    document.getElementById('captchaQuestion').textContent = `${a} ${op} ${b} = ?`;
    document.getElementById('captchaAnswer').value = '';
}

function refreshCaptcha() {
    generateCaptcha();
    showToast('New question generated!', 'info');
}

function verifyCaptcha() {
    const userAnswer = parseInt(document.getElementById('captchaAnswer').value);

    if (isNaN(userAnswer)) {
        showToast('Please enter a number', 'error');
        return;
    }

    if (userAnswer === captchaAnswer) {
        showToast('✓ Verification successful!', 'success');
        goToStep(2);
    } else {
        showToast('❌ Wrong answer. Try again!', 'error');
        generateCaptcha();
    }
}

function goToStep(stepNumber) {
    // Update step indicators
    for (let i = 1; i <= 3; i++) {
        const indicator = document.getElementById(`step${i}Indicator`);
        const content = document.getElementById(`bookingStep${i}`);

        indicator.classList.remove('active', 'completed');
        content.classList.remove('active');

        if (i < stepNumber) {
            indicator.classList.add('completed');
        } else if (i === stepNumber) {
            indicator.classList.add('active');
            content.classList.add('active');
        }
    }
}

function validatePaymentAndConfirm() {
    const cardName = document.getElementById('cardName').value.trim();
    const cardNumber = document.getElementById('cardNumber').value.trim();
    const cardExpiry = document.getElementById('cardExpiry').value.trim();
    const cardCvv = document.getElementById('cardCvv').value.trim();

    if (!cardName || cardName.length < 2) {
        showToast('Please enter cardholder name', 'error');
        return;
    }

    if (!cardNumber || cardNumber.replace(/\s/g, '').length < 13) {
        showToast('Please enter a valid card number', 'error');
        return;
    }

    if (!cardExpiry || !cardExpiry.match(/^\d{2}\/\d{2}$/)) {
        showToast('Please enter expiry date (MM/YY)', 'error');
        return;
    }

    if (!cardCvv || cardCvv.length < 3) {
        showToast('Please enter CVV', 'error');
        return;
    }

    // Store payment info (masked for display)
    state.pendingBooking.paymentInfo = {
        cardName,
        cardLast4: cardNumber.slice(-4),
        cardExpiry
    };

    // Show confirmation summary
    const { tickets, total, matchLabel, match, paymentInfo } = state.pendingBooking;

    document.getElementById('confirmationSummary').innerHTML = `
        <h4>✅ Ready to Complete Your Booking</h4>
        <div class="confirmation-item">
            <span>Match</span>
            <span>${matchLabel}</span>
        </div>
        <div class="confirmation-item">
            <span>Date</span>
            <span>${new Date(match.matchDate).toLocaleDateString()}</span>
        </div>
        <div class="confirmation-item">
            <span>Tickets</span>
            <span>${tickets.length} ticket(s)</span>
        </div>
        <div class="confirmation-item">
            <span>Payment Method</span>
            <span>💳 •••• ${paymentInfo.cardLast4}</span>
        </div>
        <div class="confirmation-item">
            <span><strong>Total Amount</strong></span>
            <span style="color: var(--morocco-gold);"><strong>$${total}</strong></span>
        </div>
    `;

    goToStep(3);
}

async function confirmBooking() {
    if (state.selectedTickets.length === 0) return;

    const ticketIds = state.selectedTickets;
    const userId = state.currentUser.id;

    try {
        // Call backend API to create booking in database
        const result = await ApiService.createBooking(userId, ticketIds);

        if (result.success) {
            // Update local ticket status
            state.selectedTickets.forEach(id => {
                const ticket = state.tickets.find(t => t.id === id);
                if (ticket) ticket.status = 'SOLD';
            });

            // Update available tickets count
            const ticket = state.tickets.find(t => t.id === ticketIds[0]);
            if (ticket) {
                const match = state.matches.find(m => m.id === ticket.matchId);
                if (match) match.availableTickets -= ticketIds.length;
            }

            updateStats();
            state.selectedTickets = [];
            state.pendingBooking = null;

            // Reset payment form
            document.getElementById('cardName').value = '';
            document.getElementById('cardNumber').value = '';
            document.getElementById('cardExpiry').value = '';
            document.getElementById('cardCvv').value = '';

            closeModal('bookingModal');
            showToast(`✅ Booking confirmed! Ref: ${result.booking.reference}`, 'success');

            const matchId = parseInt(document.getElementById('matchSelect').value);
            if (matchId) loadTicketsForMatch(matchId);
        } else {
            showToast('❌ Booking failed. Please try again.', 'error');
        }
    } catch (error) {
        console.error('Booking error:', error);
        showToast(`❌ Booking failed: ${error.message}`, 'error');
    }
}

async function renderBookings() {
    const container = document.getElementById('bookingsList');
    if (!container) return;

    if (!state.currentUser) {
        container.innerHTML = `
            <div class="empty-state">
                <p>Please login to view your bookings</p>
            </div>
        `;
        return;
    }

    // Validate user id exists
    if (!state.currentUser.id) {
        console.warn('User ID missing, clearing stored user');
        // Clear invalid stored user and prompt re-login
        sessionStorage.removeItem('worldcup2030_user');
        container.innerHTML = `
            <div class="empty-state">
                <p>Session expired. Please login again to view your bookings.</p>
            </div>
        `;
        return;
    }

    // Show loading state
    container.innerHTML = `<div class="loading">Loading your bookings...</div>`;

    try {
        // Fetch bookings from database API
        const bookings = await ApiService.getUserBookings(state.currentUser.id);


        if (bookings.length === 0) {
            container.innerHTML = `
                <div class="empty-state" id="noBookings">
                    <div class="empty-ticket"><span>📋</span></div>
                    <p>No bookings yet. Book your first ticket!</p>
                </div>
            `;
            return;
        }

        container.innerHTML = bookings.map(booking => `
            <div class="booking-card">
                <div class="booking-header">
                    <div>
                        <div class="booking-ref">📋 ${booking.reference}</div>
                        <div class="booking-date">${new Date(booking.bookingDate).toLocaleDateString()}</div>
                    </div>
                    <span class="booking-status ${booking.status.toLowerCase()}">${booking.status}</span>
                </div>
                <div class="booking-content">
                    <div class="booking-tickets">
                        ${booking.tickets.map(ticket => {
            const matchLabel = ticket.match.homeTeam && ticket.match.awayTeam ?
                `${ticket.match.homeFlag || '🏳️'} ${ticket.match.homeTeam} vs ${ticket.match.awayTeam} ${ticket.match.awayFlag || '🏳️'}` :
                'TBD vs TBD';
            return `
                                <div class="booking-ticket-item">
                                    <div>
                                        <strong>${matchLabel}</strong>
                                        <div style="font-size: 0.85rem; color: var(--text-secondary); margin-top: 0.25rem;">
                                            ${ticket.categoryCode} - Section ${ticket.section || 'N/A'}, Seat ${ticket.seatNumber}
                                        </div>
                                    </div>
                                    <span style="color: var(--morocco-gold); font-weight: 600;">$${ticket.price}</span>
                                </div>
                            `;
        }).join('')}
                    </div>
                </div>
                <div class="booking-total">
                    <span class="booking-total-label">Total</span>
                    <span class="booking-total-amount">$${booking.totalPrice}</span>
                </div>
                ${booking.status !== 'CANCELLED' ? `
                    <div class="booking-actions">
                        <button class="btn btn-danger btn-sm" onclick="cancelBooking(${booking.id})">
                            Cancel Booking
                        </button>
                    </div>
                ` : ''}
            </div>
        `).join('');
    } catch (error) {
        console.error('Error loading bookings:', error);
        container.innerHTML = `
            <div class="empty-state">
                <p>Failed to load bookings. Please try again.</p>
            </div>
        `;
    }
}

async function cancelBooking(bookingId) {
    if (!confirm('Are you sure you want to cancel this booking?')) return;

    try {
        // Call backend API to cancel booking
        await ApiService.cancelBooking(bookingId);

        showToast('Booking cancelled successfully', 'success');

        // Refresh the bookings list
        renderBookings();
    } catch (error) {
        console.error('Cancel booking error:', error);
        showToast(`Failed to cancel booking: ${error.message}`, 'error');
    }
}

// ========================================
// Admin Dashboard
// ========================================
async function renderAdminDashboard() {
    try {
        // Fetch data from API
        const [users, bookings] = await Promise.all([
            ApiService.getUsers(),
            ApiService.getAllBookings()
        ]);

        // Store for use in other admin functions
        state.adminUsers = users;
        state.adminBookings = bookings;

        // Stats
        const totalUsers = users.length;
        const totalBookings = bookings.length;
        const ticketsSold = state.tickets.filter(t => t.status === 'SOLD').length;
        const ticketsAvailable = state.tickets.filter(t => t.status === 'AVAILABLE').length;
        const revenue = bookings.reduce((sum, b) => sum + (parseFloat(b.totalPrice) || 0), 0);

        document.getElementById('adminTotalUsers').textContent = totalUsers;
        document.getElementById('adminTotalBookings').textContent = totalBookings;
        document.getElementById('adminTicketsSold').textContent = ticketsSold;
        document.getElementById('adminTicketsAvailable').textContent = ticketsAvailable;
        document.getElementById('adminRevenue').textContent = `$${revenue.toLocaleString()}`;

        // Matches table (legacy - now handled by tabs)
        const matchesBody = document.getElementById('adminMatchesBody');
        if (!matchesBody) return;

        matchesBody.innerHTML = state.matches.map(match => {
            const isKnockout = match.homeTeam === null;
            const matchLabel = isKnockout ?
                `${match.knockoutLabel?.home || 'TBD'} vs ${match.knockoutLabel?.away || 'TBD'}` :
                `${match.homeTeamData?.flagEmoji || '🏳️'} ${match.homeTeamData?.fifaCode || 'TBD'} vs ${match.awayTeamData?.fifaCode || 'TBD'} ${match.awayTeamData?.flagEmoji || '🏳️'}`;
            return `
            <tr>
                <td>${match.matchNumber || match.id}</td>
                <td>${matchLabel}</td>
                <td>${match.stadium?.city || 'TBD'}</td>
                <td>${new Date(match.matchDate).toLocaleDateString()}</td>
                <td>
                    <span style="color: var(--morocco-green-light);">${match.availableTickets || 0}</span> / 30
                </td>
                <td>
                    <button class="btn btn-outline btn-sm" onclick="showMatchDetails(${match.id})">View</button>
                </td>
            </tr>
        `;
        }).join('');

        // Render all admin tabs
        renderAdminInventory();
        renderAdminBookings();
        renderAdminUsers();
        renderAdminMatches();
        populateInventoryMatchFilter();
    } catch (error) {
        console.error('Error loading admin dashboard:', error);
        showToast('Failed to load admin data', 'error');
    }
}

// Admin Tab Switching
function setupAdminTabs() {
    document.querySelectorAll('.admin-tab').forEach(tab => {
        tab.addEventListener('click', () => {
            // Update active tab
            document.querySelectorAll('.admin-tab').forEach(t => t.classList.remove('active'));
            tab.classList.add('active');

            // Update content
            const tabName = tab.dataset.adminTab;
            document.querySelectorAll('.admin-tab-content').forEach(content => {
                content.classList.remove('active');
            });
            document.getElementById(`adminTab${tabName.charAt(0).toUpperCase() + tabName.slice(1)}`).classList.add('active');
        });
    });
}

// Populate Inventory Match Filter
function populateInventoryMatchFilter() {
    const select = document.getElementById('inventoryMatchFilter');
    if (!select) return;

    const groupStageMatches = state.matches.filter(m => m.matchPhase === 'GROUP_STAGE');
    select.innerHTML = '<option value="all">All Matches</option>' +
        groupStageMatches.map(m => {
            const label = m.homeTeamData ?
                `${m.homeTeamData.fifaCode} vs ${m.awayTeamData.fifaCode}` :
                'TBD vs TBD';
            return `<option value="${m.id}">#${m.matchNumber} - ${label}</option>`;
        }).join('');
}

// Render Ticket Inventory
function renderAdminInventory() {
    const inventoryBody = document.getElementById('adminInventoryBody');
    if (!inventoryBody) return;

    // Group tickets by match and category
    const inventory = {};
    state.tickets.forEach(ticket => {
        const key = `${ticket.matchId}-${ticket.category}`;
        if (!inventory[key]) {
            const match = state.matches.find(m => m.id === ticket.matchId);
            inventory[key] = {
                matchId: ticket.matchId,
                match: match,
                category: ticket.category,
                categoryCode: ticket.categoryCode,
                price: ticket.price,
                available: 0,
                sold: 0,
                total: 0
            };
        }
        inventory[key].total++;
        if (ticket.status === 'AVAILABLE') {
            inventory[key].available++;
        } else {
            inventory[key].sold++;
        }
    });

    const rows = Object.values(inventory).map(inv => {
        const match = inv.match;
        const matchLabel = match.homeTeamData ?
            `${match.homeTeamData.flagEmoji} ${match.homeTeamData.fifaCode} vs ${match.awayTeamData.fifaCode} ${match.awayTeamData.flagEmoji}` :
            match.knockoutLabel.home + ' vs ' + match.knockoutLabel.away;
        const percentage = Math.round((inv.sold / inv.total) * 100);

        return `
            <tr>
                <td>${matchLabel}</td>
                <td><span class="status-badge">${inv.categoryCode}</span></td>
                <td>A, B, C, D</td>
                <td style="font-weight: 600;">$${inv.price}</td>
                <td class="inventory-cell">
                    <span class="inventory-count available">${inv.available}</span>
                    <div class="inventory-progress">
                        <div class="inventory-progress-bar" style="width: ${100 - percentage}%;"></div>
                    </div>
                </td>
                <td class="inventory-cell">
                    <span class="inventory-count sold">${inv.sold}</span>
                </td>
                <td>
                    <div class="action-btns">
                        <button class="action-btn restock" onclick="restockTickets(${inv.matchId}, '${inv.category}')" title="Add 5 tickets">📦</button>
                        <button class="action-btn edit" onclick="editTicketPrice(${inv.matchId}, '${inv.category}')" title="Edit price">✏️</button>
                    </div>
                </td>
            </tr>
        `;
    });

    inventoryBody.innerHTML = rows.join('');
}

// Render Admin Bookings
function renderAdminBookings() {
    const bookingsBody = document.getElementById('adminBookingsBody');
    if (!bookingsBody) return;

    const bookings = state.adminBookings || [];
    const users = state.adminUsers || [];

    if (bookings.length === 0) {
        bookingsBody.innerHTML = '<tr><td colspan="8" style="text-align: center; color: var(--text-muted);">No bookings yet</td></tr>';
        return;
    }

    bookingsBody.innerHTML = bookings.map(booking => {
        // Find user from API data
        const user = users.find(u => u.id === booking.userId);
        const ticket = booking.tickets?.[0];
        const matchLabel = ticket?.match ?
            `${ticket.match.homeTeam || 'TBD'} vs ${ticket.match.awayTeam || 'TBD'}` :
            'N/A';

        return `
            <tr>
                <td style="color: var(--morocco-gold); font-family: monospace;">${booking.reference}</td>
                <td>${user?.firstName || 'User'} ${user?.lastName || ''}</td>
                <td>${matchLabel}</td>
                <td>${booking.tickets?.length || 0}</td>
                <td style="font-weight: 600;">$${booking.totalPrice}</td>
                <td>${new Date(booking.bookingDate).toLocaleDateString()}</td>
                <td><span class="status-badge ${booking.status.toLowerCase()}">${booking.status}</span></td>
                <td>
                    <div class="action-btns">
                        <button class="action-btn view" onclick="viewBookingDetails('${booking.reference}')" title="View">👁️</button>
                        ${booking.status === 'CONFIRMED' ? `
                            <button class="action-btn delete" onclick="cancelBookingAdmin(${booking.id})" title="Cancel">❌</button>
                        ` : ''}
                    </div>
                </td>
            </tr>
        `;
    }).join('');
}

// Render Admin Users
function renderAdminUsers() {
    const usersBody = document.getElementById('adminUsersBody');
    if (!usersBody) return;

    const users = state.adminUsers || [];
    const bookings = state.adminBookings || [];

    usersBody.innerHTML = users.map(user => {
        // Match bookings by userId
        const userBookings = bookings.filter(b => b.userId === user.id);
        const totalSpent = userBookings.reduce((sum, b) => sum + (parseFloat(b.totalPrice) || 0), 0);

        return `
            <tr>
                <td>#${user.id}</td>
                <td>${user.firstName} ${user.lastName}</td>
                <td>${user.email}</td>
                <td><span class="role-badge ${user.role?.toLowerCase() || 'user'}">${user.role || 'USER'}</span></td>
                <td>${userBookings.length}</td>
                <td style="font-weight: 600; color: var(--morocco-gold);">$${totalSpent}</td>
                <td>
                    <div class="action-btns">
                        <button class="action-btn view" onclick="viewUserDetails(${user.id})" title="View">👁️</button>
                        <button class="action-btn edit" onclick="editUser(${user.id})" title="Edit">✏️</button>
                        ${user.role !== 'ADMIN' ? `
                            <button class="action-btn delete" onclick="deleteUser(${user.id})" title="Delete">🗑️</button>
                        ` : ''}
                    </div>
                </td>
            </tr>
        `;
    }).join('');
}

// Render Admin Matches
function renderAdminMatches() {
    const matchesBody = document.getElementById('adminMatchesBody');
    if (!matchesBody) return;

    matchesBody.innerHTML = state.matches.map(match => {
        const isKnockout = match.homeTeam === null;
        const matchLabel = isKnockout ?
            `${match.knockoutLabel.home} vs ${match.knockoutLabel.away}` :
            `${match.homeTeamData.flagEmoji} ${match.homeTeamData.fifaCode} vs ${match.awayTeamData.fifaCode} ${match.awayTeamData.flagEmoji}`;

        const matchTickets = state.tickets.filter(t => t.matchId === match.id);
        const soldTickets = matchTickets.filter(t => t.status === 'SOLD');
        const revenue = soldTickets.reduce((sum, t) => sum + t.price, 0);

        return `
            <tr>
                <td>#${match.matchNumber}</td>
                <td>${matchLabel}</td>
                <td><span class="match-phase" style="font-size: 0.7rem; padding: 0.2rem 0.5rem;">${match.matchPhase.replace(/_/g, ' ')}</span></td>
                <td>${match.stadium.city}</td>
                <td>${new Date(match.matchDate).toLocaleDateString()}</td>
                <td class="inventory-cell"><span class="inventory-count available">${match.availableTickets}</span></td>
                <td class="inventory-cell"><span class="inventory-count sold">${soldTickets.length}</span></td>
                <td style="font-weight: 600; color: var(--morocco-gold);">$${revenue}</td>
                <td>
                    <div class="action-btns">
                        <button class="action-btn view" onclick="showMatchDetails(${match.id})" title="View">👁️</button>
                        <button class="action-btn edit" onclick="editMatch(${match.id})" title="Edit">✏️</button>
                        <button class="action-btn restock" onclick="restockMatchTickets(${match.id})" title="Add Tickets">📦</button>
                    </div>
                </td>
            </tr>
        `;
    }).join('');
}

// Admin Actions
function restockAllTickets() {
    state.matches.forEach(match => {
        for (let i = 0; i < 10; i++) {
            state.tickets.push({
                id: state.tickets.length + 1,
                matchId: match.id,
                section: ['A', 'B', 'C', 'D'][i % 4],
                row: String(Math.floor(i / 4) + 10),
                seatNumber: String(100 + i),
                category: ['CATEGORY_1', 'CATEGORY_2', 'CATEGORY_3'][i % 3],
                categoryCode: ['CAT1', 'CAT2', 'CAT3'][i % 3],
                price: [500, 300, 150][i % 3],
                status: 'AVAILABLE'
            });
        }
        match.availableTickets += 10;
    });
    updateStats();
    renderAdminDashboard();
    showToast('✅ Added 10 tickets to each match!', 'success');
}

function restockTickets(matchId, category) {
    const prices = { 'CATEGORY_1': 500, 'CATEGORY_2': 300, 'CATEGORY_3': 150 };
    const codes = { 'CATEGORY_1': 'CAT1', 'CATEGORY_2': 'CAT2', 'CATEGORY_3': 'CAT3' };

    for (let i = 0; i < 5; i++) {
        state.tickets.push({
            id: state.tickets.length + 1,
            matchId: matchId,
            section: ['A', 'B', 'C', 'D'][i % 4],
            row: String(Math.floor(Math.random() * 20) + 1),
            seatNumber: String(Math.floor(Math.random() * 100) + 1),
            category: category,
            categoryCode: codes[category],
            price: prices[category],
            status: 'AVAILABLE'
        });
    }

    const match = state.matches.find(m => m.id === matchId);
    if (match) match.availableTickets += 5;

    updateStats();
    renderAdminInventory();
    showToast('✅ Added 5 tickets!', 'success');
}

function restockMatchTickets(matchId) {
    restockTickets(matchId, 'CATEGORY_1');
    restockTickets(matchId, 'CATEGORY_2');
    restockTickets(matchId, 'CATEGORY_3');

    const match = state.matches.find(m => m.id === matchId);
    match.availableTickets += 10; // Adjust for extra
    renderAdminMatches();
}

function editTicketPrice(matchId, category) {
    const newPrice = prompt(`Enter new price for ${category}:`, category === 'CATEGORY_1' ? 500 : category === 'CATEGORY_2' ? 300 : 150);
    if (newPrice && !isNaN(newPrice)) {
        state.tickets.forEach(ticket => {
            if (ticket.matchId === matchId && ticket.category === category) {
                ticket.price = parseInt(newPrice);
            }
        });
        renderAdminInventory();
        showToast('✅ Price updated!', 'success');
    }
}

async function cancelBookingAdmin(bookingId) {
    if (!confirm('Are you sure you want to cancel this booking?')) return;

    try {
        // Call backend API to cancel booking
        await ApiService.cancelBooking(bookingId);
        showToast('✅ Booking cancelled!', 'success');
        // Refresh admin dashboard
        renderAdminDashboard();
    } catch (error) {
        console.error('Cancel booking error:', error);
        showToast(`Failed to cancel booking: ${error.message}`, 'error');
    }
}

function viewBookingDetails(reference) {
    const bookings = state.adminBookings || [];
    const booking = bookings.find(b => b.reference === reference);
    if (booking) {
        alert(`Booking: ${booking.reference}\nTickets: ${booking.tickets?.length || 0}\nTotal: $${booking.totalPrice}\nStatus: ${booking.status}`);
    }
}

function viewUserDetails(userId) {
    const users = state.adminUsers || [];
    const bookings = state.adminBookings || [];
    const user = users.find(u => u.id === userId);
    const userBookings = bookings.filter(b => b.userId === userId);
    if (user) {
        alert(`User: ${user.firstName} ${user.lastName}\nEmail: ${user.email}\nRole: ${user.role}\nBookings: ${userBookings.length}`);
    }
}

function editUser(userId) {
    showToast('User edit modal coming soon!', 'info');
}

function deleteUser(userId) {
    if (confirm('Are you sure you want to delete this user?')) {
        showToast('User deletion is disabled in demo mode', 'info');
    }
}

function editMatch(matchId) {
    showToast('Match edit modal coming soon!', 'info');
}

function exportBookings() {
    const csv = 'Reference,User,Match,Tickets,Total,Date,Status\n' +
        state.bookings.map(b => {
            // Find user by ID or by email match (for API-created bookings)
            let user = sampleData.users.find(u => u.id === b.userId);
            if (!user && state.currentUser && b.userId === state.currentUser.id) {
                user = sampleData.users.find(u => u.email === state.currentUser.email);
            }
            const match = b.tickets[0]?.match;
            return `${b.reference},${user?.email || 'N/A'},${match?.homeTeamData?.fifaCode || 'TBD'} vs ${match?.awayTeamData?.fifaCode || 'TBD'},${b.tickets.length},$${b.totalPrice},${new Date(b.bookingDate).toLocaleDateString()},${b.status}`;
        }).join('\n');

    const blob = new Blob([csv], { type: 'text/csv' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = 'bookings_export.csv';
    a.click();
    showToast('✅ Bookings exported!', 'success');
}

function savePricing() {
    showToast('✅ Pricing settings saved!', 'success');
}

function saveNotifications() {
    showToast('✅ Notification settings saved!', 'success');
}

function saveSecurity() {
    showToast('✅ Security settings saved!', 'success');
}

function clearAllData() {
    if (confirm('This will clear local cached data. Database data will remain. Continue?')) {
        state.adminBookings = [];
        state.adminUsers = [];
        renderAdminDashboard();
        showToast('✅ Local cache cleared! Refresh to reload from database.', 'success');
    }
}

function resetSystem() {
    if (confirm('This will reset the entire system. Continue?')) {
        location.reload();
    }
}

// ========================================
// Authentication
// ========================================
async function handleLogin(e) {
    e.preventDefault();
    const email = document.getElementById('loginEmail').value;
    const password = document.getElementById('loginPassword').value;

    if (window.USE_API) {
        try {
            const result = await ApiService.login(email, password);
            if (result.success) {
                state.currentUser = result.user;
                sessionStorage.setItem('worldcup2030_user', JSON.stringify(result.user));
                updateUserUI(true);
                closeModal('loginModal');
                showToast(`Welcome, ${result.user.firstName}!`, 'success');
                document.getElementById('loginForm').reset();
            }
        } catch (error) {
            showToast(error.message || 'Invalid email or password', 'error');
        }
    } else {
        // Fallback to mock data
        const user = sampleData.users.find(u => u.email === email && u.password === password);
        if (user) {
            state.currentUser = user;
            sessionStorage.setItem('worldcup2030_user', JSON.stringify(user));
            updateUserUI(true);
            closeModal('loginModal');
            showToast(`Welcome, ${user.firstName}!`, 'success');
            document.getElementById('loginForm').reset();
        } else {
            showToast('Invalid email or password', 'error');
        }
    }
}

async function demoLogin(type) {
    const email = type === 'admin' ? 'admin@worldcup2030.com' : 'demo@worldcup2030.com';
    const password = type === 'admin' ? 'admin123' : 'demo123';

    if (window.USE_API) {
        try {
            const result = await ApiService.login(email, password);
            if (result.success) {
                state.currentUser = result.user;
                sessionStorage.setItem('worldcup2030_user', JSON.stringify(result.user));
                updateUserUI(true);
                closeModal('loginModal');
                showToast(`Welcome, ${result.user.firstName}!`, 'success');
            }
        } catch (error) {
            showToast(error.message || 'Login failed', 'error');
        }
    } else {
        // Fallback to mock data
        const user = sampleData.users.find(u => u.email === email && u.password === password);
        if (user) {
            state.currentUser = user;
            sessionStorage.setItem('worldcup2030_user', JSON.stringify(user));
            updateUserUI(true);
            closeModal('loginModal');
            showToast(`Welcome, ${user.firstName}!`, 'success');
        }
    }
}

async function handleRegister(e) {
    e.preventDefault();

    const firstName = document.getElementById('regFirstName').value;
    const lastName = document.getElementById('regLastName').value;
    const email = document.getElementById('regEmail').value;
    const phone = document.getElementById('regPhone').value;
    const password = document.getElementById('regPassword').value;

    if (window.USE_API) {
        try {
            const result = await ApiService.register({ firstName, lastName, email, phone, password });
            if (result.success) {
                state.currentUser = result.user;
                sessionStorage.setItem('worldcup2030_user', JSON.stringify(result.user));
                updateUserUI();
                closeModal('registerModal');
                showToast('Account created successfully!', 'success');
                document.getElementById('registerForm').reset();
            }
        } catch (error) {
            showToast(error.message || 'Registration failed', 'error');
        }
    } else {
        // Fallback to mock data
        if (sampleData.users.find(u => u.email === email)) {
            showToast('Email already registered', 'error');
            return;
        }

        const newUser = {
            id: sampleData.users.length + 1,
            firstName, lastName, email, phone, password,
            role: 'user'
        };

        sampleData.users.push(newUser);
        state.currentUser = newUser;
        sessionStorage.setItem('worldcup2030_user', JSON.stringify(newUser));

        updateUserUI();
        closeModal('registerModal');
        showToast('Account created successfully!', 'success');
        document.getElementById('registerForm').reset();
    }
}

function logout() {
    state.currentUser = null;
    sessionStorage.removeItem('worldcup2030_user');
    // Bookings are stored in database, no need to save session data
    updateUserUI();
    showSection('home');
    showToast('Logged out successfully', 'info');
}

function updateUserUI(isLoginEvent = false) {
    const userInfo = document.getElementById('userInfo');
    const authButtons = document.getElementById('authButtons');
    const userName = document.getElementById('userName');
    const userInitial = document.getElementById('userInitial');
    const adminNav = document.getElementById('adminNavLink');

    if (state.currentUser) {
        userInfo.style.display = 'flex';
        authButtons.style.display = 'none';
        userName.textContent = state.currentUser.firstName;
        userInitial.textContent = state.currentUser.firstName[0].toUpperCase();

        // Show admin link if admin
        adminNav.style.display = state.currentUser.role === 'admin' ? 'flex' : 'none';

        // If admin just logged in, automatically go to admin section
        if (isLoginEvent && state.currentUser.role === 'admin') {
            showSection('admin');
        }
    } else {
        userInfo.style.display = 'none';
        authButtons.style.display = 'flex';
        adminNav.style.display = 'none';
    }
}

// ========================================
// Modal Functions
// ========================================
function openModal(modalId) {
    document.getElementById(modalId).classList.add('active');
}

function closeModal(modalId) {
    document.getElementById(modalId).classList.remove('active');
}

// ========================================
// Toast Notifications
// ========================================
function showToast(message, type = 'info') {
    const container = document.getElementById('toastContainer');
    const toast = document.createElement('div');
    toast.className = `toast ${type}`;

    const icons = { success: '✅', error: '❌', info: 'ℹ️' };
    toast.innerHTML = `<span>${icons[type]}</span><span>${message}</span>`;
    container.appendChild(toast);

    setTimeout(() => {
        toast.style.opacity = '0';
        toast.style.transform = 'translateX(100%)';
        setTimeout(() => toast.remove(), 300);
    }, 4000);
}
