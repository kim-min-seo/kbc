package com.minse0.kbc.notification;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.minse0.kbc.notification.domain.Notification;
import com.minse0.kbc.notification.service.NotificationService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class NotificationController {
	
	private final NotificationService notificationService;

    /** 종 아이콘 클릭 시 알림 목록 페이지 */
    @GetMapping("/notifications")
    public String page(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/user/login-view";
        }

        List<Notification> items = notificationService.listAndMarkRead(userId);
        model.addAttribute("items", items);
        return "post/notification"; 
    }
}
