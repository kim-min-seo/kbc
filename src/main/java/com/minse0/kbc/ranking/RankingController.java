package com.minse0.kbc.ranking;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.minse0.kbc.ranking.domain.Ranking;
import com.minse0.kbc.ranking.service.RankingService;

@Controller
public class RankingController {

    private final RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    
}
