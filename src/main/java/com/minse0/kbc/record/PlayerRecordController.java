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
@RequestMapping("/record")
public class PlayerRecordController {

    private final RecordService recordService;

    @GetMapping("/player")
    public String showPlayer(
            @RequestParam(value = "name", required = false) String name,
            Model model) {

        
        if (name == null || name.isBlank()) {
            return "post/record/player";
        }

       
        List<BattersStats> batters  = recordService.getYearlyBattersByPlayer(name);
        List<PitchersStats> pitchers = recordService.getYearlyPitchersByPlayer(name);

        model.addAttribute("name",     name);
        model.addAttribute("batters",  batters);
        model.addAttribute("pitchers", pitchers);

        return "post/record/player";
    }
}
