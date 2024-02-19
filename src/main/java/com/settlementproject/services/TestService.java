package com.settlementproject.services;

import com.settlementproject.entities.ChildEntity;
import com.settlementproject.entities.ParentEntity;
import com.settlementproject.entities.SchoolEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {
    private final ParentService parentService;
    private final SchoolService schoolService;
    private final AttendanceService attendanceService;
    private final ChildService childService;


    @EventListener(ApplicationReadyEvent.class)
    public void create() {
        SchoolEntity school = schoolService.create();
        ParentEntity parent = parentService.create();
        ChildEntity child = childService.create(school, parent);
        attendanceService.create(child);
    }

}
