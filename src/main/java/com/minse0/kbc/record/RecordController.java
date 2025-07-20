package com.minse0.kbc.record;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.minse0.kbc.record.domain.BattersStats;
import com.minse0.kbc.record.domain.PitchersStats;
import com.minse0.kbc.record.service.RecordService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/record")
public class RecordController {

    private final RecordService recordService;

    @GetMapping("/batters")
    public String getBattersStats(@RequestParam(required = false) Integer year,
                                  @RequestParam(required = false) String team,
                                  @RequestParam(defaultValue = "AVG") String sort,
                                  Model model,
                                  HttpServletRequest request) {

        int targetYear = resolveYear(year);
        String targetTeam = resolveTeam(team);

        List<BattersStats> batters = recordService.getSortedBatters(targetYear, targetTeam, sort);
        List<String> teams = recordService.getAllTeamsB();

        model.addAttribute("batters", batters);
        model.addAttribute("year", targetYear);
        model.addAttribute("team", targetTeam);
        model.addAttribute("sort", sort);
        model.addAttribute("teams", teams);
        model.addAttribute("requestURI", request.getRequestURI());

        return "post/record/batters";
    }

    @GetMapping("/pitchers")
    public String getPitchersStats(@RequestParam(required = false) Integer year,
                                   @RequestParam(required = false) String team,
                                   @RequestParam(defaultValue = "ERA") String sort,
                                   Model model,
                                   HttpServletRequest request) {

        int targetYear = resolveYear(year);
        String targetTeam = resolveTeam(team);

        List<PitchersStats> pitchers = recordService.getSortedPitchers(targetYear, targetTeam, sort);
        List<String> teams = recordService.getAllTeamsP();

        model.addAttribute("pitchers", pitchers);
        model.addAttribute("year", targetYear);
        model.addAttribute("team", targetTeam);
        model.addAttribute("sort", sort);
        model.addAttribute("teams", teams);
        model.addAttribute("requestURI", request.getRequestURI());

        return "post/record/pitchers";
    }

   
    private int resolveYear(Integer year) {
        return (year != null) ? year : 2025;
    }

    private String resolveTeam(String team) {
        return (team != null && !team.isBlank()) ? team : null;
    }
}
