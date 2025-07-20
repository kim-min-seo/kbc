package com.minse0.kbc.record.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "batters_stats")
public class BattersStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "player_name")
    private String playerName;

    @Column(name = "team")
    private String team;

    @Column(name = "AVG")
    private Float avg;

    @Column(name = "G")
    private Integer g;

    @Column(name = "PA")
    private Integer pa;

    @Column(name = "AB")
    private Integer ab;

    @Column(name = "H")
    private Integer h;

    @Column(name = "2B")
    private Integer twoB;

    @Column(name = "3B")
    private Integer threeB;

    @Column(name = "HR")
    private Integer hr;

    @Column(name = "RBI")
    private Integer rbi;

    @Column(name = "SB")
    private Integer sb;

    @Column(name = "CS")
    private Integer cs;

    @Column(name = "BB")
    private Integer bb;

    @Column(name = "HBP")
    private Integer hbp;

    @Column(name = "SO")
    private Integer so;

    @Column(name = "GDP")
    private Integer gdp;

    @Column(name = "E")
    private Integer e;

    @Column(name = "R")
    private Integer r;

    @Column(name = "TB")
    private Integer tb;

    @Column(name = "SAC")
    private Integer sac;

    @Column(name = "SF")
    private Integer sf;

    @Column(name = "year")
    private Integer year;
}
