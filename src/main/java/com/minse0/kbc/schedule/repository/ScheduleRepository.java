package com.minse0.kbc.schedule.repository;

import com.minse0.kbc.schedule.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    List<Schedule> findByGameDateBetween(LocalDate start, LocalDate end);
}
