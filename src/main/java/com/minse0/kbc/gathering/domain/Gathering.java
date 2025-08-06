package com.minse0.kbc.gathering.domain;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "gatherings")
@AllArgsConstructor
public class Gathering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GatheringType type;

    @Column(length = 100, nullable = false)
    private String title;  

    @Lob
    @Column(nullable = false)
    private String content;  

    @Column(name = "home_team", length = 50)
    private String homeTeam;

    @Column(name = "away_team", length = 50)
    private String awayTeam;

    @Column(length = 100, nullable = false)
    private String location;

    @Column(name = "meeting_time", nullable = false)
    private LocalDateTime meetingTime;

    private Double latitude;
    private Double longitude;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public enum GatheringType {
        DIRECT, PUB
    }
}
