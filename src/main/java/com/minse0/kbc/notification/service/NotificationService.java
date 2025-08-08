package com.minse0.kbc.notification.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.minse0.kbc.gathering.domain.Entry;
import com.minse0.kbc.gathering.domain.Gathering;
import com.minse0.kbc.notification.domain.Notification;
import com.minse0.kbc.notification.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository repo;

    /** 헤더 뱃지용: 미확인 알림 수 */
    @Transactional(readOnly = true)
    public long countUnread(Long userId) {
        return (userId == null) ? 0L : repo.countByReceiverIdAndReadFalse(userId);
    }

    /** 알림 목록 + 페이지 진입 시 일괄 읽음 처리 */
    @Transactional
    public List<Notification> listAndMarkRead(Long userId) {
        List<Notification> items = repo.findByReceiverIdOrderByCreatedAtDesc(userId);
        repo.markAllRead(userId);
        return items;
    }

    /** (주최자에게) 새 신청 알림 */
    @Transactional
    public void notifyEntryApplied(Gathering g, Entry e) {
        Long ownerId = g.getUserId();
        if (ownerId == null || ownerId.equals(e.getUserId())) return; // 본인 신청은 스킵

        Notification n = Notification.builder()
            .receiverId(ownerId)
            .type(Notification.Type.ENTRY_APPLIED)
            .title("새 모임 신청")
            .message(e.getNickname() + " 님이 [" + safe(g.getTitle()) + "]에 신청했습니다.")
            .linkUrl("/entry/list?gatheringId=" + g.getId())
            .targetGatheringId(g.getId())   // ✅ 넣어줘야 함
            .targetEntryId(e.getId())       // ✅ 넣어줘야 함
            .read(false)
            .createdAt(LocalDateTime.now())
            .build();

        repo.save(n);
    }

    /** (신청자에게) 수락 알림 */
    @Transactional
    public void notifyEntryAccepted(Gathering g, Entry e) {
        Notification n = Notification.builder()
            .receiverId(e.getUserId())
            .type(Notification.Type.ENTRY_ACCEPTED) // ⚠️ enum에 추가 필요
            .title("모임 신청 수락")
            .message("[" + safe(g.getTitle()) + "] 모임 신청이 수락되었습니다.")
            .linkUrl("/gathering/" + g.getId())
            .read(false)
            .createdAt(LocalDateTime.now())
            .build();

        repo.save(n);
    }

    /** (신청자에게) 거절 알림 */
    @Transactional
    public void notifyEntryRejected(Gathering g, Entry e) {
        Notification n = Notification.builder()
            .receiverId(e.getUserId())
            .type(Notification.Type.ENTRY_REJECTED) // ⚠️ enum에 추가 필요
            .title("모임 신청 거절")
            .message("[" + safe(g.getTitle()) + "] 모임 신청이 거절되었습니다.")
            .linkUrl("/gathering/" + g.getId())
            .read(false)
            .createdAt(LocalDateTime.now())
            .build();

        repo.save(n);
    }

    private String safe(String s) {
        return (s == null || s.isBlank()) ? "모임" : s;
    }
}
