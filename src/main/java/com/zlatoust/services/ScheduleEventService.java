package com.zlatoust.services;

import com.zlatoust.mapper.ScheduleEventMapper;
import com.zlatoust.models.ScheduleEvent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleEventService {

    private final ScheduleEventMapper scheduleEventMapper;

    public ScheduleEventService(ScheduleEventMapper scheduleEventMapper) {
        this.scheduleEventMapper = scheduleEventMapper;
    }

    public ScheduleEvent getById(Long id) {
        return scheduleEventMapper.getById(id);
    }

    public List<ScheduleEvent> getAll() {
        return scheduleEventMapper.getAll();
    }

    public List<ScheduleEvent> getByYearMonth(String year, String month) {
        return scheduleEventMapper.getByYearMonth(Integer.valueOf(year), Integer.valueOf(month));
    }

    public List<ScheduleEvent> getByYearMonthDay(String year, String month, String day) {
        return scheduleEventMapper.getByYearMonthDay(Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(day));
    }

    public void update(Long id, ScheduleEvent scheduleEvent) {
        scheduleEventMapper.update(id, scheduleEvent);
    }

    public void insert(ScheduleEvent scheduleEvent) {
        scheduleEventMapper.insert(scheduleEvent);
    }

    public void delete(Long id) {
        scheduleEventMapper.delete(id);
    }
}