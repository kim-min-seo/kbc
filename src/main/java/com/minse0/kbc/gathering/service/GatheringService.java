package com.minse0.kbc.gathering.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.minse0.kbc.gathering.domain.Gathering;
import com.minse0.kbc.gathering.domain.Gathering.GatheringType;
import com.minse0.kbc.gathering.repository.GatheringRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GatheringService {

    private final GatheringRepository gatheringRepository;

    // 모임 저장 (생성)
    public Gathering save(Gathering gathering) {
        gathering.setCreatedAt(LocalDateTime.now());
        gathering.setUpdatedAt(LocalDateTime.now());
        return gatheringRepository.save(gathering);
    }

    // 타입별 조회 (DIRECT, PUB)
    public List<Gathering> getAllByType(GatheringType type) {
        return gatheringRepository.findByType(type);
    }

    // 전체 모임 조회
    public List<Gathering> findAll() {
        return gatheringRepository.findAll();
    }

    // Optional 단건 조회
    public Optional<Gathering> findById(Long id) {
        return gatheringRepository.findById(id);
    }

    // null 허용 단건 조회
    public Gathering getById(Long id) {
        return gatheringRepository.findById(id).orElse(null);
    }

    // 삭제
    public void delete(Long id, Long userId) {
        Gathering g = getById(id);
        if (g != null && g.getUserId().equals(userId)) {
            gatheringRepository.deleteById(id);
        }
    }

    // 수정
    public void update(Gathering updated, Long userId) {
        Gathering origin = getById(updated.getId());
        if (origin != null && origin.getUserId().equals(userId)) {
            origin.setType(updated.getType());
            origin.setTitle(updated.getTitle());
            origin.setContent(updated.getContent());
            origin.setHomeTeam(updated.getHomeTeam());
            origin.setAwayTeam(updated.getAwayTeam());
            origin.setLocation(updated.getLocation());
            origin.setMeetingTime(updated.getMeetingTime());
            origin.setLatitude(updated.getLatitude());
            origin.setLongitude(updated.getLongitude());
            origin.setUpdatedAt(LocalDateTime.now());

            gatheringRepository.save(origin);
        }
    }

    // 존재 여부 확인 (삭제 권한 체크에 유용)
    public boolean existsByIdAndUserId(Long id, Long userId) {
        return gatheringRepository.existsById(id) && getById(id).getUserId().equals(userId);
    }
}
