package com.minse0.kbc.gathering.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.minse0.kbc.gathering.domain.Entry;
import com.minse0.kbc.gathering.domain.Gathering;
import com.minse0.kbc.gathering.repository.EntryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EntryService {

    private final EntryRepository entryRepository;

    @Transactional
    public Entry createEntry(Gathering gathering, Long userId, String nickname) {
        if (entryRepository.existsByGatheringAndUserId(gathering, userId)) {
            throw new IllegalStateException("You have already joined this gathering.");
        }

        Entry entry = Entry.builder()
                .gathering(gathering)
                .userId(userId)
                .nickname(nickname)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return entryRepository.save(entry);
    }

    @Transactional
    public void deleteEntry(Long entryId, Long userId) {
        Entry entry = entryRepository.findById(entryId)
                .orElseThrow(() -> new IllegalArgumentException("Entry not found."));

        if (!entry.getUserId().equals(userId)) {
            throw new IllegalStateException("You are not allowed to delete this entry.");
        }

        entryRepository.delete(entry);
    }

    @Transactional(readOnly = true)
    public List<Entry> getEntriesByGathering(Gathering gathering) {
        return entryRepository.findByGathering(gathering);
    }
}
