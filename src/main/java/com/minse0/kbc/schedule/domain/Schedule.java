package com.minse0.kbc.schedule.domain;

import java.time.LocalDate;

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

	    @Column(name = "game_date", nullable = false) 
	    private LocalDate gameDate; 

	    @Column(name = "game", nullable = false, length = 64) 
	    private String game; 

	   
}
