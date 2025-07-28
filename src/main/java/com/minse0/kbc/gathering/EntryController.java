package com.minse0.kbc.gathering;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.minse0.kbc.gathering.domain.Gathering;
import com.minse0.kbc.gathering.service.EntryService;
import com.minse0.kbc.gathering.service.GatheringService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/entry")
public class EntryController {

    private final EntryService entryService;
    private final GatheringService gatheringService;

    @PostMapping("/apply")
    public String apply(@RequestParam Long gatheringId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        String nickname = (String) session.getAttribute("nickname");

        Gathering gathering = gatheringService.getById(gatheringId);
        if (gathering == null) {
            throw new IllegalArgumentException("모임을 찾을 수 없습니다.");
        }


        entryService.createEntry(gathering, userId, nickname);
        return "redirect:/gathering/detail?id=" + gatheringId;
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long entryId, @RequestParam Long gatheringId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        entryService.deleteEntry(entryId, userId);
        return "redirect:/gathering/detail?id=" + gatheringId;
    }
}    
