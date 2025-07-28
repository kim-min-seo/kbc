package com.minse0.kbc.gathering;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.minse0.kbc.gathering.domain.Gathering;
import com.minse0.kbc.gathering.domain.Gathering.GatheringType;
import com.minse0.kbc.gathering.service.GatheringService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/gathering")
public class GatheringController {

    private final GatheringService gatheringService;

    @GetMapping("/{type}")
    public String list(@PathVariable GatheringType type, Model model) {
        List<Gathering> gatherings = gatheringService.getAllByType(type);
        model.addAttribute("gatherings", gatherings);
        model.addAttribute("type", type);
        return "gathering/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("gathering", new Gathering());
        return "gathering/form";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute Gathering gathering, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        gathering.setUserId(userId);
        gatheringService.save(gathering);
        return "redirect:/gathering/" + gathering.getType().name();
    }

    @GetMapping("/detail")
    public String detail(@RequestParam Long id, Model model) {
        Gathering g = gatheringService.getById(id);
        model.addAttribute("gathering", g);
        return "gathering/detail";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        gatheringService.delete(id, userId);
        return "redirect:/gathering";
    }

    @GetMapping("/edit")
    public String editForm(@RequestParam Long id, Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        Gathering g = gatheringService.getById(id);
        if (g == null || !g.getUserId().equals(userId)) {
            return "redirect:/gathering";
        }
        model.addAttribute("gathering", g);
        return "gathering/form";
    }

    @PostMapping("/edit")
    public String editSubmit(@ModelAttribute Gathering gathering, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        gatheringService.update(gathering, userId);
        return "redirect:/gathering/detail?id=" + gathering.getId();
    }
}
