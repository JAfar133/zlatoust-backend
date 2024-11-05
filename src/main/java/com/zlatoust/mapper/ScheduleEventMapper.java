package com.zlatoust.mapper;

import com.zlatoust.models.ScheduleEvent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ScheduleEventMapper {
    ScheduleEvent getById(Long id);
    List<ScheduleEvent> getAll();
    List<ScheduleEvent> getByYearMonth(@Param("year") Integer year, @Param("month") Integer month);
    List<ScheduleEvent> getByYearMonthDay(@Param("year") Integer year, @Param("month") Integer month, @Param("day") Integer day);
    void update(Long id, ScheduleEvent scheduleEvent);
    void insert(ScheduleEvent scheduleEvent);
    void delete(Long id);
}
