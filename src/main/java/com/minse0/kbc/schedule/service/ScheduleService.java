package com.minse0.kbc.schedule.service;

import java.time.DayOfWeek; // DayOfWeek import 추가
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit; // ChronoUnit import 추가
import java.time.temporal.TemporalAdjusters; // TemporalAdjusters import 추가
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream; // IntStream import 추가

import org.springframework.stereotype.Service;

import com.minse0.kbc.schedule.domain.Schedule;
import com.minse0.kbc.schedule.repository.ScheduleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository repository;

    
    public List<List<LocalDate>> generateCalendar(int year, int month) {
        YearMonth ym = YearMonth.of(year, month);
       
        LocalDate startOfCalendarGrid = ym.atDay(1).with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
       
        LocalDate endOfCalendarGrid   = ym.atEndOfMonth().with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
        
        long totalDaysInGrid = ChronoUnit.DAYS.between(startOfCalendarGrid, endOfCalendarGrid) + 1;
        
        return IntStream.range(0, (int) totalDaysInGrid)
                .mapToObj(startOfCalendarGrid::plusDays)
                .collect(Collectors.groupingBy(
                        date -> (int) ChronoUnit.DAYS.between(startOfCalendarGrid, date) / 7,
                        LinkedHashMap::new,
                        Collectors.toList()
                ))
                .values().stream()
                .collect(Collectors.toList());
    }

    public Map<LocalDate, List<String>> getScheduleMap(int year, int month) {
        LocalDate from = LocalDate.of(year, month, 1);
        LocalDate to   = from.withDayOfMonth(from.lengthOfMonth());
        
        List<Schedule> schedules = repository.findByGameDateBetween(from, to);

        return schedules.stream()
                .collect(Collectors.groupingBy(
                        Schedule::getGameDate,
                        LinkedHashMap::new,
                        Collectors.mapping(
                            Schedule::getGame,
                            Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> list.stream().limit(5).collect(Collectors.toList())
                            )
                        )
                ));
    }
}