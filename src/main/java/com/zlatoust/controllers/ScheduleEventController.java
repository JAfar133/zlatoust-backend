package com.zlatoust.controllers;

import com.zlatoust.configuration.ApiConstants;
import com.zlatoust.models.ScheduleEvent;
import com.zlatoust.services.ScheduleEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiConstants.API_PATH_SCHEDULE)
@RequiredArgsConstructor
public class ScheduleEventController {

    private final ScheduleEventService scheduleEventService;

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleEvent> getById(@PathVariable Long id) {
        ScheduleEvent event = scheduleEventService.getById(id);
        return event != null ? ResponseEntity.ok(event) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<ScheduleEvent>> getAll() {
        return ResponseEntity.ok(scheduleEventService.getAll());
    }

    @GetMapping("/{year}/{month}")
    public ResponseEntity<List<ScheduleEvent>> getByYearMonth(@PathVariable String year, @PathVariable String month) {
        return ResponseEntity.ok(scheduleEventService.getByYearMonth(year, month));
    }

    @GetMapping("/{year}/{month}/{day}")
    public ResponseEntity<List<ScheduleEvent>> getByYearMonthDay(@PathVariable String year, @PathVariable String month, @PathVariable String day) {
        return ResponseEntity.ok(scheduleEventService.getByYearMonthDay(year, month, day));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody ScheduleEvent scheduleEvent) {
        scheduleEventService.update(id, scheduleEvent);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<ScheduleEvent> insert(@RequestBody ScheduleEvent scheduleEvent) {
        scheduleEventService.insert(scheduleEvent);
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleEvent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        scheduleEventService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
