package com.minse0.kbc.record.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.minse0.kbc.record.domain.BattersStats;

public interface BattersStatsRepository extends JpaRepository<BattersStats, Long> {

    List<BattersStats> findByYearOrderByAvgDesc(int year);
    List<BattersStats> findByYearAndTeamOrderByAvgDesc(int year, String team);

    List<BattersStats> findByYearOrderByGDesc(int year);
    List<BattersStats> findByYearAndTeamOrderByGDesc(int year, String team);

    List<BattersStats> findByYearOrderByPaDesc(int year);
    List<BattersStats> findByYearAndTeamOrderByPaDesc(int year, String team);

    List<BattersStats> findByYearOrderByAbDesc(int year);
    List<BattersStats> findByYearAndTeamOrderByAbDesc(int year, String team);

    List<BattersStats> findByYearOrderByHDesc(int year);
    List<BattersStats> findByYearAndTeamOrderByHDesc(int year, String team);

    List<BattersStats> findByYearOrderByTwoBDesc(int year);
    List<BattersStats> findByYearAndTeamOrderByTwoBDesc(int year, String team);

    List<BattersStats> findByYearOrderByThreeBDesc(int year);
    List<BattersStats> findByYearAndTeamOrderByThreeBDesc(int year, String team);

    List<BattersStats> findByYearOrderByHrDesc(int year);
    List<BattersStats> findByYearAndTeamOrderByHrDesc(int year, String team);

    List<BattersStats> findByYearOrderByRbiDesc(int year);
    List<BattersStats> findByYearAndTeamOrderByRbiDesc(int year, String team);

    List<BattersStats> findByYearOrderBySbDesc(int year);
    List<BattersStats> findByYearAndTeamOrderBySbDesc(int year, String team);

    List<BattersStats> findByYearOrderByCsDesc(int year);
    List<BattersStats> findByYearAndTeamOrderByCsDesc(int year, String team);

    List<BattersStats> findByYearOrderByBbDesc(int year);
    List<BattersStats> findByYearAndTeamOrderByBbDesc(int year, String team);

    List<BattersStats> findByYearOrderByHbpDesc(int year);
    List<BattersStats> findByYearAndTeamOrderByHbpDesc(int year, String team);

    List<BattersStats> findByYearOrderBySoDesc(int year);
    List<BattersStats> findByYearAndTeamOrderBySoDesc(int year, String team);

    List<BattersStats> findByYearOrderByGdpDesc(int year);
    List<BattersStats> findByYearAndTeamOrderByGdpDesc(int year, String team);

    List<BattersStats> findByYearOrderByEDesc(int year);
    List<BattersStats> findByYearAndTeamOrderByEDesc(int year, String team);

    List<BattersStats> findByYearOrderByRDesc(int year);
    List<BattersStats> findByYearAndTeamOrderByRDesc(int year, String team);

    List<BattersStats> findByYearOrderByTbDesc(int year);
    List<BattersStats> findByYearAndTeamOrderByTbDesc(int year, String team);

    List<BattersStats> findByYearOrderBySacDesc(int year);
    List<BattersStats> findByYearAndTeamOrderBySacDesc(int year, String team);

    List<BattersStats> findByYearOrderBySfDesc(int year);
    List<BattersStats> findByYearAndTeamOrderBySfDesc(int year, String team);
    
    @Query("SELECT DISTINCT b.team FROM BattersStats b")
    List<String> findDistinctTeam();
}
