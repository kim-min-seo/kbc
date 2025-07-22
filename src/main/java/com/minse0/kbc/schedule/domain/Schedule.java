package com.minse0.kbc.schedule.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "game_schedule")
@Getter
@Setter
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "game_date")
    private LocalDate gameDate;

    @Column(name = "game")
    private String game;
}
