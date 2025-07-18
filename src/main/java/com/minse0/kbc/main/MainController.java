package com.minse0.kbc.main;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.minse0.kbc.ranking.domain.Ranking;
import com.minse0.kbc.ranking.service.RankingService;

@Controller
@RequestMapping("/")
public class MainController {
	
	private final RankingService rankingService;

    public MainController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @GetMapping("/")
    public String showMainPage(Model model) {
        List<Ranking> rankings = rankingService.getRanking2025();
        model.addAttribute("rankings", rankings);
        return "post/main";
    }
}
