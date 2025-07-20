package com.minse0.kbc.record.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.minse0.kbc.record.domain.BattersStats;
import com.minse0.kbc.record.domain.PitchersStats;
import com.minse0.kbc.record.repository.BattersStatsRepository;
import com.minse0.kbc.record.repository.PitchersStatsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final BattersStatsRepository battersRepo;
    private final PitchersStatsRepository pitchersRepo;

    public List<BattersStats> getSortedBatters(int year, String team, String sortKey) {
        boolean hasTeam = team != null && !team.isBlank();

        return switch (sortKey) {
            case "AVG" -> hasTeam ? battersRepo.findByYearAndTeamOrderByAvgDesc(year, team)
                                  : battersRepo.findByYearOrderByAvgDesc(year);
            case "G" -> hasTeam ? battersRepo.findByYearAndTeamOrderByGDesc(year, team)
                                : battersRepo.findByYearOrderByGDesc(year);
            case "PA" -> hasTeam ? battersRepo.findByYearAndTeamOrderByPaDesc(year, team)
                                 : battersRepo.findByYearOrderByPaDesc(year);
            case "AB" -> hasTeam ? battersRepo.findByYearAndTeamOrderByAbDesc(year, team)
                                 : battersRepo.findByYearOrderByAbDesc(year);
            case "H" -> hasTeam ? battersRepo.findByYearAndTeamOrderByHDesc(year, team)
                                : battersRepo.findByYearOrderByHDesc(year);
            case "2B" -> hasTeam ? battersRepo.findByYearAndTeamOrderByTwoBDesc(year, team)
                                 : battersRepo.findByYearOrderByTwoBDesc(year);
            case "3B" -> hasTeam ? battersRepo.findByYearAndTeamOrderByThreeBDesc(year, team)
                                 : battersRepo.findByYearOrderByThreeBDesc(year);
            case "HR" -> hasTeam ? battersRepo.findByYearAndTeamOrderByHrDesc(year, team)
                                 : battersRepo.findByYearOrderByHrDesc(year);
            case "RBI" -> hasTeam ? battersRepo.findByYearAndTeamOrderByRbiDesc(year, team)
                                  : battersRepo.findByYearOrderByRbiDesc(year);
            case "SB" -> hasTeam ? battersRepo.findByYearAndTeamOrderBySbDesc(year, team)
                                 : battersRepo.findByYearOrderBySbDesc(year);
            case "CS" -> hasTeam ? battersRepo.findByYearAndTeamOrderByCsDesc(year, team)
                                 : battersRepo.findByYearOrderByCsDesc(year);
            case "BB" -> hasTeam ? battersRepo.findByYearAndTeamOrderByBbDesc(year, team)
                                 : battersRepo.findByYearOrderByBbDesc(year);
            case "HBP" -> hasTeam ? battersRepo.findByYearAndTeamOrderByHbpDesc(year, team)
                                  : battersRepo.findByYearOrderByHbpDesc(year);
            case "SO" -> hasTeam ? battersRepo.findByYearAndTeamOrderBySoDesc(year, team)
                                 : battersRepo.findByYearOrderBySoDesc(year);
            case "GDP" -> hasTeam ? battersRepo.findByYearAndTeamOrderByGdpDesc(year, team)
                                  : battersRepo.findByYearOrderByGdpDesc(year);
            case "E" -> hasTeam ? battersRepo.findByYearAndTeamOrderByEDesc(year, team)
                                : battersRepo.findByYearOrderByEDesc(year);
            case "R" -> hasTeam ? battersRepo.findByYearAndTeamOrderByRDesc(year, team)
                                : battersRepo.findByYearOrderByRDesc(year);
            case "TB" -> hasTeam ? battersRepo.findByYearAndTeamOrderByTbDesc(year, team)
                                 : battersRepo.findByYearOrderByTbDesc(year);
            case "SAC" -> hasTeam ? battersRepo.findByYearAndTeamOrderBySacDesc(year, team)
                                  : battersRepo.findByYearOrderBySacDesc(year);
            case "SF" -> hasTeam ? battersRepo.findByYearAndTeamOrderBySfDesc(year, team)
                                 : battersRepo.findByYearOrderBySfDesc(year);
            default -> throw new IllegalArgumentException("Invalid sortKey for batters: " + sortKey);
        };
    }

    public List<PitchersStats> getSortedPitchers(int year, String team, String sortKey) {
        boolean hasTeam = team != null && !team.isBlank();

        return switch (sortKey) {
            case "ERA" -> hasTeam ? pitchersRepo.findByYearAndTeamOrderByEraAsc(year, team)
                                  : pitchersRepo.findByYearOrderByEraAsc(year);
            case "WHIP" -> hasTeam ? pitchersRepo.findByYearAndTeamOrderByWhipAsc(year, team)
                                   : pitchersRepo.findByYearOrderByWhipAsc(year);
            case "G" -> hasTeam ? pitchersRepo.findByYearAndTeamOrderByGDesc(year, team)
                                : pitchersRepo.findByYearOrderByGDesc(year);
            case "W" -> hasTeam ? pitchersRepo.findByYearAndTeamOrderByWDesc(year, team)
                                : pitchersRepo.findByYearOrderByWDesc(year);
            case "L" -> hasTeam ? pitchersRepo.findByYearAndTeamOrderByLDesc(year, team)
                                : pitchersRepo.findByYearOrderByLDesc(year);
            case "SV" -> hasTeam ? pitchersRepo.findByYearAndTeamOrderBySvDesc(year, team)
                                 : pitchersRepo.findByYearOrderBySvDesc(year);
            case "HLD" -> hasTeam ? pitchersRepo.findByYearAndTeamOrderByHldDesc(year, team)
                                  : pitchersRepo.findByYearOrderByHldDesc(year);
            case "H" -> hasTeam ? pitchersRepo.findByYearAndTeamOrderByHDesc(year, team)
                                : pitchersRepo.findByYearOrderByHDesc(year);
            case "HR" -> hasTeam ? pitchersRepo.findByYearAndTeamOrderByHrDesc(year, team)
                                 : pitchersRepo.findByYearOrderByHrDesc(year);
            case "BB" -> hasTeam ? pitchersRepo.findByYearAndTeamOrderByBbDesc(year, team)
                                 : pitchersRepo.findByYearOrderByBbDesc(year);
            case "HBP" -> hasTeam ? pitchersRepo.findByYearAndTeamOrderByHbpDesc(year, team)
                                  : pitchersRepo.findByYearOrderByHbpDesc(year);
            case "SO" -> hasTeam ? pitchersRepo.findByYearAndTeamOrderBySoDesc(year, team)
                                 : pitchersRepo.findByYearOrderBySoDesc(year);
            case "R" -> hasTeam ? pitchersRepo.findByYearAndTeamOrderByRDesc(year, team)
                                : pitchersRepo.findByYearOrderByRDesc(year);
            case "ER" -> hasTeam ? pitchersRepo.findByYearAndTeamOrderByErDesc(year, team)
                                 : pitchersRepo.findByYearOrderByErDesc(year);
            case "WPCT" -> hasTeam ? pitchersRepo.findByYearAndTeamOrderByWpctDesc(year, team)
                                   : pitchersRepo.findByYearOrderByWpctDesc(year);
            case "IP" -> hasTeam ? pitchersRepo.findByYearAndTeamOrderByIpDesc(year, team)
                                 : pitchersRepo.findByYearOrderByIpDesc(year);
            default -> throw new IllegalArgumentException("Invalid sortKey for pitchers: " + sortKey);
        };
    }

    public List<String> getAllTeamsB() {
        return battersRepo.findDistinctTeam();
    }

    public List<String> getAllTeamsP() {
        return pitchersRepo.findDistinctTeam();
    }
}
