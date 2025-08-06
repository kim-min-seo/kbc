package com.minse0.kbc.gathering.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.minse0.kbc.gathering.domain.Gathering;
import com.minse0.kbc.gathering.domain.Gathering.GatheringType;

public interface GatheringRepository extends JpaRepository<Gathering, Long> {

    
    List<Gathering> findByType(GatheringType type);

    
    Optional<Gathering> findByIdAndUserId(Long id, Long userId);

 
    List<Gathering> findByUserId(Long userId);

    
    List<Gathering> findByTypeAndUserId(GatheringType type, Long userId);

   
    boolean existsByIdAndUserId(Long id, Long userId);
}
