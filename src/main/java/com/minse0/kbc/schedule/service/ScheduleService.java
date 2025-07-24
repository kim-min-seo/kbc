package com.minse0.kbc.schedule.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
        LocalDate firstOfMonth = ym.atDay(1);
        int firstDow = firstOfMonth.getDayOfWeek().getValue() % 7;
        int daysInMonth = ym.lengthOfMonth();

        int totalCells = ((firstDow + daysInMonth + 6) / 7) * 7;
        List<LocalDate> cells = new ArrayList<>(totalCells);

        for (int i = 0; i < totalCells; i++) {
            if (i < firstDow || i >= firstDow + daysInMonth) {
                cells.add(null);
            } else {
                cells.add(LocalDate.of(year, month, i - firstDow + 1));
            }
        }

        List<List<LocalDate>> weeks = new ArrayList<>();
        for (int i = 0; i < totalCells; i += 7) {
            weeks.add(new ArrayList<>(cells.subList(i, i + 7)));
        }
        return weeks;
    }

    public Map<LocalDate, List<String>> getScheduleMap(int year, int month) {
        LocalDate from = LocalDate.of(year, month, 1);
        LocalDate to   = from.withDayOfMonth(from.lengthOfMonth());

        List<Schedule> schedules = repository.findByGameDateBetween(from, to);
        Map<LocalDate, List<String>> map = new LinkedHashMap<>();

        for (Schedule s : schedules) {
            map.computeIfAbsent(s.getGameDate(), d -> new ArrayList<>())
               .add(s.getGame());
        }
        return map;
    }
}
