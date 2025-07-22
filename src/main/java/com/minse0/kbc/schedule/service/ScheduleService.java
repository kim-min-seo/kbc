package com.minse0.kbc.schedule.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        LocalDate start = ym.atDay(1).with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate end   = ym.atEndOfMonth().with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
        long days = ChronoUnit.DAYS.between(start, end) + 1;
        return IntStream.range(0, (int) days)
                .mapToObj(start::plusDays)
                .collect(Collectors.groupingBy(
                        date -> (int) ChronoUnit.DAYS.between(start, date) / 7,
                        LinkedHashMap::new,
                        Collectors.toList()
                ))
                .values().stream()
                .collect(Collectors.toList());
    }

    public Map<LocalDate, List<String>> getScheduleMap(int year, int month) {
        LocalDate from = LocalDate.of(year, month, 1);
        LocalDate to   = from.withDayOfMonth(from.lengthOfMonth());
        return repository.findByGameDateBetween(from, to)
                .stream()
                .collect(Collectors.groupingBy(
                        Schedule::getGameDate,
                        LinkedHashMap::new,
                        Collectors.mapping(Schedule::getGame, Collectors.toList())
                ));
    }
}
