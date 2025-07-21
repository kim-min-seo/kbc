package com.minse0.kbc.record.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.minse0.kbc.record.domain.PitchersStats;

public interface PitchersStatsRepository extends JpaRepository<PitchersStats, Long> {

   
    List<PitchersStats> findByYear(int year, Sort sort);
    List<PitchersStats> findByYearAndTeam(int year, String team, Sort sort);

   
    List<PitchersStats> findByPlayerName(String playerName, Sort sort);

  
    @Query("SELECT DISTINCT p.team FROM PitchersStats p")
    List<String> findDistinctTeam();
}
