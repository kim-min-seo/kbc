package com.minse0.kbc.ranking;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.minse0.kbc.ranking.domain.Ranking;
import com.minse0.kbc.ranking.service.RankingService;

@Controller
@RequestMapping("/ranking")
public class RankingController {

    private final RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }
    
    @GetMapping()
    public String getRankingByYear(@RequestParam(value = "year", required = false) Integer year, Model model) {
        List<Integer> years = rankingService.getAvailableYears(); 
        model.addAttribute("years", years);

        if (year == null && !years.isEmpty()) {
            year = years.get(0); 
        }

        if (year != null) {
            List<Ranking> rankings = rankingService.getRankingByYear(year);
            model.addAttribute("rankings", rankings);
            model.addAttribute("selectedYear", year);
        }

        return "/post/ranking";
    }

    
}
