package com.minse0.kbc.notification;

import java.util.Collections;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minse0.kbc.notification.service.NotificationService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationRestController {

    private final NotificationService notificationService;

    /** 헤더 뱃지용 미확인 개수 반환 */
    @GetMapping("/unread-count")
    public Map<String, Long> unreadCount(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        long count = (userId == null) ? 0L : notificationService.countUnread(userId);
        return Collections.singletonMap("count", count);
    }
}
