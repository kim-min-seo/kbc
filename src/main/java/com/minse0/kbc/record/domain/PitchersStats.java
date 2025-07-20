package com.minse0.kbc.record.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "pitchers_stats")
public class PitchersStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "player_name")
    private String playerName;

    @Column(name = "team")
    private String team;

    @Column(name = "ERA")
    private Float era;

    @Column(name = "WHIP")
    private Float whip;

    @Column(name = "G")
    private Integer g;

    @Column(name = "W")
    private Integer w;

    @Column(name = "L")
    private Integer l;

    @Column(name = "SV")
    private Integer sv;

    @Column(name = "HLD")
    private Integer hld;

    @Column(name = "H")
    private Integer h;

    @Column(name = "HR")
    private Integer hr;

    @Column(name = "BB")
    private Integer bb;

    @Column(name = "HBP")
    private Integer hbp;

    @Column(name = "SO")
    private Integer so;

    @Column(name = "R")
    private Integer r;

    @Column(name = "ER")
    private Integer er;

    @Column(name = "WPCT")
    private Float wpct;

    @Column(name = "IP")
    private Float ip;

    @Column(name = "year")
    private Integer year;
}
