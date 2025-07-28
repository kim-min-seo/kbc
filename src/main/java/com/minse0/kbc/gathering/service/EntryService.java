package com.minse0.kbc.gathering.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.minse0.kbc.gathering.domain.Entry;
import com.minse0.kbc.gathering.domain.Gathering;
import com.minse0.kbc.gathering.repository.EntryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EntryService {

    private final EntryRepository entryRepository;

    public Entry createEntry(Gathering gathering, Long userId, String nickname) {
        if (entryRepository.existsByGatheringAndUserId(gathering, userId)) {
            throw new IllegalStateException("이미 신청한 모임입니다.");
        }
        Entry entry = Entry.builder()
                .gathering(gathering)
                .userId(userId)
                .nickname(nickname)
                .build();
        return entryRepository.save(entry);
    }

    public List<Entry> getEntriesByGathering(Gathering gathering) {
        return entryRepository.findByGathering(gathering);
    }

    public List<Entry> getEntriesByUserId(Long userId) {
        return entryRepository.findByUserId(userId);
    }

    public void deleteEntry(Long entryId, Long userId) {
        Entry entry = entryRepository.findById(entryId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 신청입니다."));
        if (!entry.getUserId().equals(userId)) {
            throw new SecurityException("삭제 권한이 없습니다.");
        }
        entryRepository.delete(entry);
    }
}
