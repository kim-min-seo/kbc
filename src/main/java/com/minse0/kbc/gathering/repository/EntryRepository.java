package com.minse0.kbc.gathering.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.minse0.kbc.gathering.domain.Entry;
import com.minse0.kbc.gathering.domain.Gathering;

public interface EntryRepository extends JpaRepository<Entry, Long> {
    List<Entry> findByGathering(Gathering gathering);
    List<Entry> findByUserId(Long userId);
    boolean existsByGatheringAndUserId(Gathering gathering, Long userId);
}
