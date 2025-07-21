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

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/player")
public class PlayerRecordController {

    private final RecordService recordService;

    @GetMapping
    public String showPlayer(
            @RequestParam("name") String name,
            @RequestParam(value = "role", defaultValue = "batters") String role,
            Model model) {

        model.addAttribute("name", name);
        model.addAttribute("role", role);

        if ("pitchers".equals(role)) {
            List<PitchersStats> stats = recordService.getYearlyPitchersByPlayer(name);
            model.addAttribute("stats", stats);
        } else {
            List<BattersStats> stats = recordService.getYearlyBattersByPlayer(name);
            model.addAttribute("stats", stats);
        }

        return "post/record/player";
    }
}
