package com.worldcup2030.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String country;

    @Column(name = "fifa_code", nullable = false, length = 3)
    private String fifaCode;

    @Column(name = "group_name", length = 1)
    private String groupName;

    @Column(name = "flag_emoji", length = 20)
    private String flagEmoji;

    public Team() {
    }

    public Team(String country, String fifaCode, String groupName) {
        this.country = country;
        this.fifaCode = fifaCode;
        this.groupName = groupName;
    }

    public Team(String country, String fifaCode, String groupName, String flagEmoji) {
        this.country = country;
        this.fifaCode = fifaCode;
        this.groupName = groupName;
        this.flagEmoji = flagEmoji;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFifaCode() {
        return fifaCode;
    }

    public void setFifaCode(String fifaCode) {
        this.fifaCode = fifaCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getFlagEmoji() {
        return flagEmoji;
    }

    public void setFlagEmoji(String flagEmoji) {
        this.flagEmoji = flagEmoji;
    }

    @Override
    public String toString() {
        return String.format("%s %s (%s) - Group %s",
                flagEmoji != null ? flagEmoji : "🏴", country, fifaCode, groupName);
    }
}
