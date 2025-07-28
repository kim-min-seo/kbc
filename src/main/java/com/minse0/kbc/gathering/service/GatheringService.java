package com.minse0.kbc.gathering.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.minse0.kbc.gathering.domain.Gathering;
import com.minse0.kbc.gathering.domain.Gathering.GatheringType;
import com.minse0.kbc.gathering.repository.GatheringRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GatheringService {

    private final GatheringRepository gatheringRepository;

    public Gathering save(Gathering gathering) {
        return gatheringRepository.save(gathering);
    }

    public List<Gathering> getAllByType(GatheringType type) {
        return gatheringRepository.findByType(type);
    }

    public Gathering getById(Long id) {
        return gatheringRepository.findById(id).orElse(null);
    }

    public void delete(Long id, Long userId) {
        Gathering g = getById(id);
        if (g != null && g.getUserId().equals(userId)) {
            gatheringRepository.deleteById(id);
        }
    }

    public void update(Gathering updated, Long userId) {
        Gathering origin = getById(updated.getId());
        if (origin != null && origin.getUserId().equals(userId)) {
            origin.setType(updated.getType());
            origin.setTeam(updated.getTeam());
            origin.setLocation(updated.getLocation());
            origin.setContents(updated.getContents());
            origin.setImagePath(updated.getImagePath());
            gatheringRepository.save(origin);
        }
    }
}
