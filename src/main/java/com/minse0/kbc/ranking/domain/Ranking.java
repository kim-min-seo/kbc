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

    @Column(name = "games_played")
    private int gamesPlayed;

    private int wins;
    private int draws;
    private int losses;

    @Column(name = "win_percentage")
    private double winPercentage;

    @Column(name = "games_behind")
    private int gamesBehind;

    @Column(name = "last_10")
    private String last10;

    private String streak;

    @Column(name = "home_record")
    private String homeRecord;

    @Column(name = "away_record")
    private String awayRecord;
}
