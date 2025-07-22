package com.minse0.kbc.schedule;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.minse0.kbc.schedule.service.ScheduleService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {
	
	private final ScheduleService service;

    @GetMapping
    public String viewSchedule(@RequestParam(value = "year", required = false) Integer year,
                               @RequestParam(value = "month", required = false) Integer month,
                               Model model) {
        LocalDate today = LocalDate.now();

        int currentYear = (year == null ? today.getYear() : year);
        int currentMonth = (month == null ? today.getMonthValue() : month);

        List<List<LocalDate>> weeks = service.generateCalendar(currentYear, currentMonth);
        Map<LocalDate, List<String>> scheduleMap = service.getScheduleMap(currentYear, currentMonth);
        
        model.addAttribute("year", currentYear);
        model.addAttribute("month", currentMonth);
        model.addAttribute("weeks", weeks);
        model.addAttribute("scheduleMap", scheduleMap);
        model.addAttribute("today", today);

        return "/post/schedule";
    }
}