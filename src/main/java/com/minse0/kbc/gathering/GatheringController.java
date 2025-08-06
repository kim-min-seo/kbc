package com.minse0.kbc.gathering;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.minse0.kbc.gathering.domain.Gathering;
import com.minse0.kbc.gathering.domain.Gathering.GatheringType;
import com.minse0.kbc.gathering.service.GatheringService;
import com.minse0.kbc.post.domain.Post;
import com.minse0.kbc.post.service.PostService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/gathering")
public class GatheringController {

    private final GatheringService gatheringService;
    private final PostService postService;

    // ✅ [변경된 부분] 모임 리스트 (타입별)
    @GetMapping("/{type}")
    public String list(@PathVariable String type, Model model) {
        GatheringType gatheringType;
        try {
            gatheringType = GatheringType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            return "redirect:/error"; // 잘못된 타입 접근 시 에러 페이지 혹은 기본 리다이렉트
        }

        List<Gathering> gatherings = gatheringService.getAllByType(gatheringType);
        model.addAttribute("gatherings", gatherings);
        model.addAttribute("type", gatheringType);
        return "post/gathering";
    }

    // 모임 등록 폼
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("gathering", new Gathering());
        return "gathering/form";
    }

    // 모임 등록 처리
    @PostMapping("/new")
    public String create(@ModelAttribute Gathering gathering,
                         @RequestParam(required = false) Double latitude,
                         @RequestParam(required = false) Double longitude,
                         HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        gathering.setUserId(userId);
        gathering.setLatitude(latitude);
        gathering.setLongitude(longitude);
        gathering.setCreatedAt(LocalDateTime.now());
        gathering.setUpdatedAt(LocalDateTime.now());
        Gathering saved = gatheringService.save(gathering);

        Post post = new Post();
        post.setUserId(userId);
        post.setTeam(saved.getHomeTeam() + " vs " + saved.getAwayTeam());
        post.setContents(
            "[모임 안내] " + saved.getTitle() + "\n" +
            saved.getContent() + "\n" +
            "장소: " + saved.getLocation() + "\n" +
            "시간: " + saved.getMeetingTime()
        );
        post.setGatheringId(saved.getId());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        postService.save(post);

        return "redirect:/post/community";
    }

    // 모임 수정 폼
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        Gathering gathering = gatheringService.findById(id).orElse(null);
        if (gathering == null || !gathering.getUserId().equals(userId)) {
            return "redirect:/gathering/DIRECT";
        }
        model.addAttribute("gathering", gathering);
        return "gathering/form";
    }

    // 모임 수정 처리
    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute Gathering updatedGathering,
                         HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        Gathering original = gatheringService.findById(id).orElse(null);
        if (original == null || !original.getUserId().equals(userId)) {
            return "redirect:/gathering/DIRECT";
        }

        original.setType(updatedGathering.getType());
        original.setTitle(updatedGathering.getTitle());
        original.setContent(updatedGathering.getContent());
        original.setHomeTeam(updatedGathering.getHomeTeam());
        original.setAwayTeam(updatedGathering.getAwayTeam());
        original.setLocation(updatedGathering.getLocation());
        original.setMeetingTime(updatedGathering.getMeetingTime());
        original.setLatitude(updatedGathering.getLatitude());
        original.setLongitude(updatedGathering.getLongitude());
        original.setUpdatedAt(LocalDateTime.now());

        gatheringService.save(original);

        return "redirect:/post/community";
    }

    // 모임 삭제
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        Gathering gathering = gatheringService.findById(id).orElse(null);
        if (gathering == null || !gathering.getUserId().equals(userId)) {
            return "redirect:/gathering/DIRECT";
        }

        gatheringService.delete(id, userId);
        return "redirect:/post/community";
    }
    @GetMapping
    public String fallbackRedirect() {
        return "redirect:/gathering/DIRECT";
    }

}
