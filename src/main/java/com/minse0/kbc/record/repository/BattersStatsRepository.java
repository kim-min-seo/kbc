package com.minse0.kbc.record.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.minse0.kbc.record.domain.BattersStats;

public interface BattersStatsRepository extends JpaRepository<BattersStats, Long> {

    
    List<BattersStats> findByYear(int year, Sort sort);
    List<BattersStats> findByYearAndTeam(int year, String team, Sort sort);

   
    List<BattersStats> findByPlayerName(String playerName, Sort sort);

    
    @Query("SELECT DISTINCT b.team FROM BattersStats b")
    List<String> findDistinctTeam();
}
