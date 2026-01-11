// ========================================
// API SERVICE - Communication with Backend
// ========================================

const API_BASE_URL = '/api';

const ApiService = {
    // Generic fetch helper with error handling
    async fetch(endpoint, options = {}) {
        try {
            const response = await fetch(`${API_BASE_URL}${endpoint}`, {
                headers: {
                    'Content-Type': 'application/json',
                    ...options.headers
                },
                ...options
            });

            if (!response.ok) {
                const error = await response.json();
                throw new Error(error.error || `HTTP ${response.status}`);
            }

            return await response.json();
        } catch (error) {
            console.error(`API Error (${endpoint}):`, error);
            throw error;
        }
    },

    // Teams
    async getTeams() {
        return this.fetch('/teams');
    },

    async getTeam(id) {
        return this.fetch(`/teams/${id}`);
    },

    // Stadiums
    async getStadiums() {
        return this.fetch('/stadiums');
    },

    // Matches
    async getMatches() {
        return this.fetch('/matches');
    },

    async getMatch(id) {
        return this.fetch(`/matches/${id}`);
    },

    // Tickets
    async getTicketsForMatch(matchId) {
        return this.fetch(`/tickets/match/${matchId}`);
    },

    async reserveTicket(ticketId) {
        return this.fetch(`/tickets/${ticketId}/reserve`, { method: 'POST' });
    },

    // Auth
    async login(email, password) {
        return this.fetch('/auth/login', {
            method: 'POST',
            body: JSON.stringify({ email, password })
        });
    },

    async register(userData) {
        return this.fetch('/auth/register', {
            method: 'POST',
            body: JSON.stringify(userData)
        });
    },

    // Bookings
    async getUserBookings(userId) {
        return this.fetch(`/bookings/user/${userId}`);
    },

    async getAllBookings() {
        return this.fetch('/bookings');
    },

    async createBooking(userId, ticketIds) {
        return this.fetch('/bookings', {
            method: 'POST',
            body: JSON.stringify({ userId, ticketIds })
        });
    },

    async cancelBooking(bookingId) {
        return this.fetch(`/bookings/${bookingId}`, { method: 'DELETE' });
    },

    // Users (admin)
    async getUsers() {
        return this.fetch('/users');
    }
};

// Flag to control whether to use API or mock data
// Set to true when backend is running
let USE_API = false;

// Try to detect if API is available
async function checkApiAvailability() {
    try {
        await fetch(`${API_BASE_URL}/teams`, { method: 'HEAD' });
        USE_API = true;
        console.log('✅ Backend API detected - using real data');
    } catch (e) {
        USE_API = false;
        console.log('ℹ️ Backend API not available - using mock data');
    }
}

// Export for use in app.js
window.ApiService = ApiService;
window.USE_API = USE_API;
window.checkApiAvailability = checkApiAvailability;
