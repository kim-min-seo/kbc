package com.minse0.kbc.ranking.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "team_rankings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ranking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int year;
    private int rank;
    private String team;
    private int gamesPlayed;
    private int wins;
    private int draws;
    private int losses;
    private double winPercentage;
    private int gamesBehind;

    @Column(name = "last_10")
    private String last10;
    private String streak;
    private String homeRecord;
    private String awayRecord;
}
