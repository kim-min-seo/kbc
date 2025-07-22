package com.minse0.kbc.record.service;

import java.util.List;

import org.springframework.data.domain.Sort;
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
        Sort sort = Sort.by(Sort.Order.desc(toBattersProperty(sortKey)));
        return (team != null && !team.isBlank())
            ? battersRepo.findByYearAndTeam(year, team, sort)
            : battersRepo.findByYear(year, sort);
    }

    public List<PitchersStats> getSortedPitchers(int year, String team, String sortKey) {
        Sort.Order order = toPitchersOrder(sortKey);
        return (team != null && !team.isBlank())
            ? pitchersRepo.findByYearAndTeam(year, team, Sort.by(order))
            : pitchersRepo.findByYear(year, Sort.by(order));
    }

    public List<BattersStats> getYearlyBattersByPlayer(String playerName) {
        return battersRepo.findByPlayerName(playerName, Sort.by("year"));
    }

    public List<PitchersStats> getYearlyPitchersByPlayer(String playerName) {
        return pitchersRepo.findByPlayerName(playerName, Sort.by("year"));
    }

    public List<String> getAllTeamsB() {
        return battersRepo.findDistinctTeam();
    }

    public List<String> getAllTeamsP() {
        return pitchersRepo.findDistinctTeam();
    }

   

    private String toBattersProperty(String sortKey) {
        return switch (sortKey) {
            case "AVG" -> "avg";
            case "G"   -> "g";
            case "PA"  -> "pa";
            case "AB"  -> "ab";
            case "H"   -> "h";
            case "2B"  -> "twoB";
            case "3B"  -> "threeB";
            case "HR"  -> "hr";
            case "RBI" -> "rbi";
            case "SB"  -> "sb";
            case "CS"  -> "cs";
            case "BB"  -> "bb";
            case "HBP" -> "hbp";
            case "SO"  -> "so";
            case "GDP" -> "gdp";
            case "E"   -> "e";
            case "R"   -> "r";
            case "TB"  -> "tb";
            case "SAC" -> "sac";
            case "SF"  -> "sf";
            default    -> throw new IllegalArgumentException("Invalid sortKey for batters: " + sortKey);
        };
    }

    private Sort.Order toPitchersOrder(String sortKey) {
        boolean asc;
        String prop;
        switch (sortKey) {
            case "ERA":  prop = "era";  asc = true;  break;
            case "WHIP": prop = "whip"; asc = true;  break;
            case "G":    prop = "g";    asc = false; break;
            case "W":    prop = "w";    asc = false; break;
            case "L":    prop = "l";    asc = false; break;
            case "SV":   prop = "sv";   asc = false; break;
            case "HLD":  prop = "hld";  asc = false; break;
            case "H":    prop = "h";    asc = false; break;
            case "HR":   prop = "hr";   asc = false; break;
            case "BB":   prop = "bb";   asc = false; break;
            case "HBP":  prop = "hbp";  asc = false; break;
            case "SO":   prop = "so";   asc = false; break;
            case "R":    prop = "r";    asc = false; break;
            case "ER":   prop = "er";   asc = false; break;
            case "WPCT": prop = "wpct"; asc = false; break;
            case "IP":   prop = "ip";   asc = false; break;
            default: throw new IllegalArgumentException("Invalid sortKey for pitchers: " + sortKey);
        }
        return asc
            ? Sort.Order.asc(prop)
            : Sort.Order.desc(prop);
    }
}
