package com.worldcup2030.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "matches")
public class Match {

    public enum MatchPhase {
        GROUP_STAGE,
        ROUND_OF_32,
        ROUND_OF_16,
        QUARTER_FINAL,
        SEMI_FINAL,
        THIRD_PLACE,
        FINAL
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "home_team_id", nullable = false)
    private Team homeTeam;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "away_team_id", nullable = false)
    private Team awayTeam;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stadium_id", nullable = false)
    private Stadium stadium;

    @Column(name = "match_date", nullable = false)
    private LocalDateTime matchDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "match_phase", nullable = false)
    private MatchPhase matchPhase;

    @Column(name = "match_number")
    private Integer matchNumber;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> tickets = new ArrayList<>();

    public Match() {
    }

    public Match(Team homeTeam, Team awayTeam, Stadium stadium, LocalDateTime matchDate, MatchPhase matchPhase) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.stadium = stadium;
        this.matchDate = matchDate;
        this.matchPhase = matchPhase;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Stadium getStadium() {
        return stadium;
    }

    public void setStadium(Stadium stadium) {
        this.stadium = stadium;
    }

    public LocalDateTime getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(LocalDateTime matchDate) {
        this.matchDate = matchDate;
    }

    public MatchPhase getMatchPhase() {
        return matchPhase;
    }

    public void setMatchPhase(MatchPhase matchPhase) {
        this.matchPhase = matchPhase;
    }

    public Integer getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(Integer matchNumber) {
        this.matchNumber = matchNumber;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public String toString() {
        return String.format("⚽ Match #%d: %s vs %s | %s | %s | %s",
                matchNumber != null ? matchNumber : id,
                homeTeam.getFifaCode(),
                awayTeam.getFifaCode(),
                matchPhase.name().replace("_", " "),
                stadium.getCity(),
                matchDate.toString().replace("T", " "));
    }
}
