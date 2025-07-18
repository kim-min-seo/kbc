package com.minse0.kbc.ranking.repositroy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.minse0.kbc.ranking.domain.Ranking;

public interface RankingRepository extends JpaRepository<Ranking, Long> {
    List<Ranking> findByYearOrderByRankAsc(int year);
}
