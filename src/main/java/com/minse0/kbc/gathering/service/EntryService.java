// package: com.minse0.kbc.gathering.service
package com.minse0.kbc.gathering.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.minse0.kbc.gathering.domain.Entry;
import com.minse0.kbc.gathering.domain.Gathering;
import com.minse0.kbc.gathering.repository.EntryRepository;
import com.minse0.kbc.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EntryService {

    private final EntryRepository entryRepository;
    private final NotificationService notificationService;

    /** 모임 신청 */
    @Transactional
    public Entry createEntry(Gathering gathering, Long userId, String nickname) {
        // 본인 모임 신청 방지
        if (gathering.getUserId() != null && gathering.getUserId().equals(userId)) {
            throw new IllegalStateException("본인 모임에는 신청할 수 없습니다.");
        }
        // 중복 신청 방지
        if (entryRepository.existsByGatheringAndUserId(gathering, userId)) {
            throw new IllegalStateException("You have already joined this gathering.");
        }

        Entry entry = Entry.builder()
                .gathering(gathering)
                .userId(userId)
                .nickname(nickname)
                // Auditing이 있어도 안전하게 기본값 세팅
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Entry saved = entryRepository.save(entry);

        // 주최자에게 "신청" 알림
        try { notificationService.notifyEntryApplied(gathering, saved); } catch (Exception ignore) {}

        return saved;
    }

    /** 신청 삭제 (본인만) */
    @Transactional
    public void deleteEntry(Long entryId, Long userId) {
        Entry entry = entryRepository.findById(entryId)
                .orElseThrow(() -> new IllegalArgumentException("Entry not found."));

        if (!entry.getUserId().equals(userId)) {
            throw new IllegalStateException("You are not allowed to delete this entry.");
        }
        entryRepository.delete(entry);
    }

    /** 주최자: 신청 수락 */
    @Transactional
    public void approveEntry(Gathering gathering, Long ownerId, Long entryId) {
        Entry e = entryRepository.findById(entryId)
                .orElseThrow(() -> new IllegalArgumentException("Entry not found."));

        if (!gathering.getUserId().equals(ownerId)) {
            throw new IllegalStateException("권한이 없습니다.");
        }
        if (!e.getGathering().getId().equals(gathering.getId())) {
            throw new IllegalStateException("잘못된 요청입니다.");
        }
        if (e.getStatus() != Entry.Status.PENDING) {
            throw new IllegalStateException("이미 처리된 신청입니다.");
        }

        e.setStatus(Entry.Status.ACCEPTED); 
        try { notificationService.notifyEntryAccepted(gathering, e); } catch (Exception ignore) {}
    }

    /** 주최자: 신청 거절 */
    @Transactional
    public void rejectEntry(Gathering gathering, Long ownerId, Long entryId) {
        Entry e = entryRepository.findById(entryId)
                .orElseThrow(() -> new IllegalArgumentException("Entry not found."));

        if (!gathering.getUserId().equals(ownerId)) {
            throw new IllegalStateException("권한이 없습니다.");
        }
        if (!e.getGathering().getId().equals(gathering.getId())) {
            throw new IllegalStateException("잘못된 요청입니다.");
        }
        if (e.getStatus() != Entry.Status.PENDING) {
            throw new IllegalStateException("이미 처리된 신청입니다.");
        }

        e.setStatus(Entry.Status.REJECTED); // ✅ 상태만 변경
        try { notificationService.notifyEntryRejected(gathering, e); } catch (Exception ignore) {}
    }

    /** 모임별 신청 목록 */
    @Transactional(readOnly = true)
    public List<Entry> getEntriesByGathering(Gathering gathering) {
        return entryRepository.findByGathering(gathering);
    }
}
