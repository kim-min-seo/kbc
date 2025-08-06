package com.minse0.kbc.gathering;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String apply(@RequestParam Long gatheringId,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {

        Long userId = (Long) session.getAttribute("userId");
        String nickname = (String) session.getAttribute("userNickname");

        if (userId == null || nickname == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "You must be logged in to apply.");
            return "redirect:/login";
        }

        Gathering gathering = gatheringService.getById(gatheringId);
        if (gathering == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Gathering not found.");
            return "redirect:/gathering";
        }

        try {
            entryService.createEntry(gathering, userId, nickname);
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/gathering";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long entryId,
                         @RequestParam Long gatheringId,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Login required.");
            return "redirect:/login";
        }

        entryService.deleteEntry(entryId, userId);
        return "redirect:/gathering";
    }
}
