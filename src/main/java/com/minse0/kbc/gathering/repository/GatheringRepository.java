package com.minse0.kbc.gathering.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.minse0.kbc.gathering.domain.Gathering;
import com.minse0.kbc.gathering.domain.Gathering.GatheringType;

public interface GatheringRepository extends JpaRepository<Gathering, Long> {
    List<Gathering> findByType(GatheringType type);
}
