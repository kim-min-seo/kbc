package com.minse0.kbc.gathering;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.minse0.kbc.gathering.domain.Entry;
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

    /** 신청 */
    @PostMapping("/apply")
    public String apply(@RequestParam Long gatheringId,
                        HttpSession session,
                        RedirectAttributes ra) {

        Long userId = (Long) session.getAttribute("userId");
        String nickname = (String) session.getAttribute("userNickname");

        if (userId == null || nickname == null) {
            ra.addFlashAttribute("errorMessage", "신청하려면 로그인하세요.");
            return "redirect:/user/login-view";
        }

        Gathering gathering = gatheringService.getById(gatheringId);
        if (gathering == null) {
            ra.addFlashAttribute("errorMessage", "모임을 찾을 수 없습니다.");
            return "redirect:/community";
        }
        
        
        if (gathering.getUserId() != null && gathering.getUserId().equals(userId)) {
            ra.addFlashAttribute("errorMessage", "본인 모임에는 신청할 수 없습니다.");
            return "redirect:/community";
        }

        try {
            Entry saved = entryService.createEntry(gathering, userId, nickname);
            ra.addFlashAttribute("successMessage", "신청 완료!");
            ra.addFlashAttribute("appliedGatheringId", gatheringId);
        } catch (IllegalStateException e) {
            // 중복 신청도 성공처럼 처리
            if (e.getMessage() != null && e.getMessage().toLowerCase().contains("already")) {
                ra.addFlashAttribute("successMessage", "이미 신청한 모임입니다.");
                ra.addFlashAttribute("appliedGatheringId", gatheringId);
            } else {
                ra.addFlashAttribute("errorMessage", e.getMessage());
            }
        }

        return "redirect:/community";
    }

    /** 신청 취소(본인만) */
    @PostMapping("/delete")
    public String delete(@RequestParam Long entryId,
                         @RequestParam Long gatheringId,
                         HttpSession session,
                         RedirectAttributes ra) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            ra.addFlashAttribute("errorMessage", "로그인이 필요합니다.");
            return "redirect:/user/login-view";
        }

        try {
            entryService.deleteEntry(entryId, userId);
            ra.addFlashAttribute("successMessage", "신청이 취소되었습니다.");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/community";
    }

    /** 작성자용: 특정 모임의 신청 목록 확인 */
    @GetMapping("/list")
    public String list(@RequestParam Long gatheringId,
                       HttpSession session,
                       Model model,
                       RedirectAttributes ra) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            ra.addFlashAttribute("errorMessage", "로그인이 필요합니다.");
            return "redirect:/user/login-view";
        }

        Gathering gathering = gatheringService.getById(gatheringId);
        if (gathering == null) {
            ra.addFlashAttribute("errorMessage", "모임을 찾을 수 없습니다.");
            return "redirect:/gathering";
        }

        // Gathering 작성자 확인 (필드명 프로젝트에 맞게 조정)
        if (!gathering.getUserId().equals(userId)) {
            ra.addFlashAttribute("errorMessage", "모임 작성자만 신청 목록을 볼 수 있습니다.");
            return "redirect:/gathering";
        }

        model.addAttribute("gathering", gathering);
        model.addAttribute("entries", entryService.getEntriesByGathering(gathering));
        return "gathering/entries";
    }
    
    @PostMapping("/approve")
    public String approve(@RequestParam Long entryId,
                          @RequestParam Long gatheringId,
                          HttpSession session,
                          RedirectAttributes ra) {
        Long ownerId = (Long) session.getAttribute("userId");
        if (ownerId == null) return "redirect:/user/login-view";

        Gathering g = gatheringService.getById(gatheringId);
        if (g == null) return "redirect:/gathering";

        try {
            entryService.approveEntry(g, ownerId, entryId);
            ra.addFlashAttribute("successMessage", "신청을 수락했습니다.");
        } catch (RuntimeException ex) {
            ra.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/entry/list?gatheringId=" + gatheringId;
    }

    /** 주최자: 신청 거절 */
    @PostMapping("/reject")
    public String reject(@RequestParam Long entryId,
                         @RequestParam Long gatheringId,
                         HttpSession session,
                         RedirectAttributes ra) {
        Long ownerId = (Long) session.getAttribute("userId");
        if (ownerId == null) return "redirect:/user/login-view";

        Gathering g = gatheringService.getById(gatheringId);
        if (g == null) return "redirect:/gathering";

        try {
            entryService.rejectEntry(g, ownerId, entryId);
            ra.addFlashAttribute("successMessage", "신청을 거절했습니다.");
        } catch (RuntimeException ex) {
            ra.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/entry/list?gatheringId=" + gatheringId;
    }
}
