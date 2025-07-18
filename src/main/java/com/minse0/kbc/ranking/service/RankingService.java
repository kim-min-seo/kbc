package com.minse0.kbc.ranking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.minse0.kbc.ranking.domain.Ranking;
import com.minse0.kbc.ranking.repositroy.RankingRepository;

@Service
public class RankingService {
	
	private final RankingRepository rankingRepository;

    public RankingService(RankingRepository rankingRepository) {
        this.rankingRepository = rankingRepository;
    }
    
    public List<Ranking> getRanking2025() {
        return rankingRepository.findByYearOrderByRankAsc(2025);
    }
    
}
