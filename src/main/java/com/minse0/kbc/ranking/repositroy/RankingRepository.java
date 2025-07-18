package com.minse0.kbc.ranking.repositroy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.minse0.kbc.ranking.domain.Ranking;

public interface RankingRepository extends JpaRepository<Ranking, Long> {
    public List<Ranking> findByYearOrderByRankAsc(int year);
    
    @Query("SELECT DISTINCT r.year FROM Ranking r ORDER BY r.year DESC")
    public List<Integer> findDistinctYears();
}
