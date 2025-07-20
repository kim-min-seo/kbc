package com.minse0.kbc.record.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.minse0.kbc.record.domain.PitchersStats;

public interface PitchersStatsRepository extends JpaRepository<PitchersStats, Long> {

    List<PitchersStats> findByYearOrderByEraAsc(int year);
    List<PitchersStats> findByYearAndTeamOrderByEraAsc(int year, String team);

    List<PitchersStats> findByYearOrderByWhipAsc(int year);
    List<PitchersStats> findByYearAndTeamOrderByWhipAsc(int year, String team);

    List<PitchersStats> findByYearOrderByGDesc(int year);
    List<PitchersStats> findByYearAndTeamOrderByGDesc(int year, String team);

    List<PitchersStats> findByYearOrderByWDesc(int year);
    List<PitchersStats> findByYearAndTeamOrderByWDesc(int year, String team);

    List<PitchersStats> findByYearOrderByLDesc(int year);
    List<PitchersStats> findByYearAndTeamOrderByLDesc(int year, String team);

    List<PitchersStats> findByYearOrderBySvDesc(int year);
    List<PitchersStats> findByYearAndTeamOrderBySvDesc(int year, String team);

    List<PitchersStats> findByYearOrderByHldDesc(int year);
    List<PitchersStats> findByYearAndTeamOrderByHldDesc(int year, String team);

    List<PitchersStats> findByYearOrderByHDesc(int year);
    List<PitchersStats> findByYearAndTeamOrderByHDesc(int year, String team);

    List<PitchersStats> findByYearOrderByHrDesc(int year);
    List<PitchersStats> findByYearAndTeamOrderByHrDesc(int year, String team);

    List<PitchersStats> findByYearOrderByBbDesc(int year);
    List<PitchersStats> findByYearAndTeamOrderByBbDesc(int year, String team);

    List<PitchersStats> findByYearOrderByHbpDesc(int year);
    List<PitchersStats> findByYearAndTeamOrderByHbpDesc(int year, String team);

    List<PitchersStats> findByYearOrderBySoDesc(int year);
    List<PitchersStats> findByYearAndTeamOrderBySoDesc(int year, String team);

    List<PitchersStats> findByYearOrderByRDesc(int year);
    List<PitchersStats> findByYearAndTeamOrderByRDesc(int year, String team);

    List<PitchersStats> findByYearOrderByErDesc(int year);
    List<PitchersStats> findByYearAndTeamOrderByErDesc(int year, String team);

    List<PitchersStats> findByYearOrderByWpctDesc(int year);
    List<PitchersStats> findByYearAndTeamOrderByWpctDesc(int year, String team);

    List<PitchersStats> findByYearOrderByIpDesc(int year);
    List<PitchersStats> findByYearAndTeamOrderByIpDesc(int year, String team);
    
    @Query("SELECT DISTINCT p.team FROM PitchersStats p")
    List<String> findDistinctTeam();
}
