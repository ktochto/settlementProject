package com.settlementproject;

import com.settlementproject.controller.SettlementController;
import com.settlementproject.entities.AttendanceEntity;
import com.settlementproject.entities.ChildEntity;
import com.settlementproject.entities.ParentEntity;
import com.settlementproject.entities.SchoolEntity;
import com.settlementproject.repositories.AttendanceRepository;
import com.settlementproject.repositories.ChildRepository;
import com.settlementproject.repositories.ParentRepository;
import com.settlementproject.repositories.SchoolRepository;
import com.settlementproject.services.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SettlementController.class)
@Import({SettlementService.class,
        SettlementParentService.class,
        SettlementSchoolService.class,
        SettlementChildService.class,
        AttendanceService.class,
        ChildService.class,
        SchoolService.class,
        ParentService.class
})
public class SettlementTest {
    @Autowired
    private MockMvc mockMvc;
    private SettlementService settlementService;
    private SettlementParentService settlementParentService;
    private SettlementSchoolService settlementSchoolService;
    private SettlementChildService settlementChildService;
    private AttendanceService attendanceService;
    private ChildService childService;
    private SchoolService schoolService;
    private ParentService parentService;

    @MockBean
    private SchoolRepository schoolRepository;
    @MockBean
    private ParentRepository parentRepository;
    @MockBean
    private AttendanceRepository attendanceRepository;
    @MockBean
    private ChildRepository childRepository;

    String schoolUrl = "/school?schoolId={schoolId}&date={date}";
    String parentUrl = "/parent?parentId={parentId}&date={date}";
    String parent1JSON = "{\"firstName\":\"Tim\",\"lastName\":\"Shaw\",\"children\":[{\"firstName\":\"John\",\"lastName\":\"Ceena\",\"totalPrice\":0.0,\"payedHours\":0},{\"firstName\":\"Leona\",\"lastName\":\"Ceena\",\"totalPrice\":70.0,\"payedHours\":7}],\"totalPrice\":70.0,\"payedHours\":7}";
    String parent2JSON = "{\"firstName\":\"Alex\",\"lastName\":\"Alex\",\"children\":[{\"firstName\":\"Mirai\",\"lastName\":\"Zura\",\"totalPrice\":73.5,\"payedHours\":7}],\"totalPrice\":73.5,\"payedHours\":7}";
    String parentNonPayedJSON = "{\"firstName\":\"Tim\",\"lastName\":\"Shaw\",\"children\":[{\"firstName\":\"John\",\"lastName\":\"Ceena\",\"totalPrice\":0.0,\"payedHours\":0},{\"firstName\":\"Leona\",\"lastName\":\"Ceena\",\"totalPrice\":0.0,\"payedHours\":0}],\"totalPrice\":0.0,\"payedHours\":0}";
    String school1JSON = "{\"name\":\"Oxford\",\"children\":[{\"firstName\":\"John\",\"lastName\":\"Ceena\",\"totalPrice\":0.0,\"payedHours\":0},{\"firstName\":\"Leona\",\"lastName\":\"Ceena\",\"totalPrice\":70.0,\"payedHours\":7}],\"totalPrice\":70.0,\"payedHours\":7}";
    String school2JSON = "{\"name\":\"Mira\",\"children\":[{\"firstName\":\"Mirai\",\"lastName\":\"Zura\",\"totalPrice\":73.5,\"payedHours\":7}],\"totalPrice\":73.5,\"payedHours\":7}";
    String schoolNonPayedJSON = "{\"name\":\"Oxford\",\"children\":[{\"firstName\":\"John\",\"lastName\":\"Ceena\",\"totalPrice\":0.0,\"payedHours\":0},{\"firstName\":\"Leona\",\"lastName\":\"Ceena\",\"totalPrice\":0.0,\"payedHours\":0}],\"totalPrice\":0.0,\"payedHours\":0}";


    @Test
    public void testGetParentSettlement() throws Exception {
        addAllDatabaseData();
        mockMvc.perform(MockMvcRequestBuilders.get(parentUrl, 2, "01/02/2024"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(parent2JSON));

        mockMvc.perform(MockMvcRequestBuilders.get(parentUrl, 1, "01/02/2024"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(parent1JSON));
    }

    @Test
    public void testGetSchoolSettlement() throws Exception {
        addAllDatabaseData();
        mockMvc.perform(MockMvcRequestBuilders.get(schoolUrl, 1, "01/02/2024"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(school1JSON));

        mockMvc.perform(MockMvcRequestBuilders.get(schoolUrl, 2, "01/02/2024"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(school2JSON));
    }

    @Test
    public void testWrongInput() throws Exception {
        addAllDatabaseData();
        mockMvc.perform(MockMvcRequestBuilders.get(parentUrl, 1L, "01/012024"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testWrongParentId() throws Exception {
        addAllDatabaseData();
        mockMvc.perform(MockMvcRequestBuilders.get(parentUrl, 50, "01/01/2024"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testWrongSchoolId() throws Exception {
        addAllDatabaseData();
        mockMvc.perform(MockMvcRequestBuilders.get(schoolUrl, 50, "01/01/2024"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testNonPayedDays() throws Exception {
        addAllDatabaseData();
        mockMvc.perform(MockMvcRequestBuilders.get(parentUrl, 1L, "01/02/2025"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(parentNonPayedJSON));

        mockMvc.perform(MockMvcRequestBuilders.get(schoolUrl, 1L, "01/02/2025"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(schoolNonPayedJSON));
    }

    private void addAllDatabaseData() {
        List<AttendanceEntity> attendanceEntities = new ArrayList<>();
        AttendanceEntity attendance = new AttendanceEntity();
        attendance.setEntryDate(LocalDateTime.of(2024, 1, 1, 6, 20));
        attendance.setExitDate(LocalDateTime.of(2024, 1, 1, 18, 2));
        attendance.setChildId(2L);
        attendance.setId(4L);
        attendanceEntities.add(attendance);
        when(attendanceRepository.findAttendanceByChildIdAndMonthAndYear(2L, 1, 2024)).thenReturn(attendanceEntities);

        attendanceEntities = new ArrayList<>();
        attendance = new AttendanceEntity();
        attendance.setEntryDate(LocalDateTime.of(2024, 1, 1, 6, 20));
        attendance.setExitDate(LocalDateTime.of(2024, 1, 1, 18, 2));
        attendance.setChildId(1L);
        attendance.setId(2L);
        attendanceEntities.add(attendance);
        attendance = new AttendanceEntity();
        attendance.setEntryDate(LocalDateTime.of(2024, 1, 1, 7, 20));
        attendance.setExitDate(LocalDateTime.of(2024, 1, 1, 11, 2));
        attendance.setChildId(1L);
        attendance.setId(1L);
        attendanceEntities.add(attendance);
        attendance = new AttendanceEntity();
        attendance.setEntryDate(LocalDateTime.of(2024, 1, 1, 7, 20));
        attendance.setExitDate(LocalDateTime.of(2024, 1, 1, 18, 2));
        attendance.setChildId(1L);
        attendance.setId(3L);
        attendanceEntities.add(attendance);
        when(attendanceRepository.findAttendanceByChildIdAndMonthAndYear(1L, 1, 2024)).thenReturn(attendanceEntities);

        attendanceEntities = new ArrayList<>();
        attendance = new AttendanceEntity();
        attendance.setEntryDate(LocalDateTime.of(2024, 2, 1, 6, 20));
        attendance.setExitDate(LocalDateTime.of(2024, 2, 1, 18, 2));
        attendance.setChildId(2L);
        attendance.setId(5L);
        attendanceEntities.add(attendance);
        when(attendanceRepository.findAttendanceByChildIdAndMonthAndYear(2L, 2, 2024)).thenReturn(attendanceEntities);

        attendanceEntities = new ArrayList<>();
        attendance = new AttendanceEntity();
        attendance.setEntryDate(LocalDateTime.of(2024, 2, 1, 6, 20));
        attendance.setExitDate(LocalDateTime.of(2024, 2, 1, 18, 2));
        attendance.setChildId(3L);
        attendance.setId(6L);
        attendanceEntities.add(attendance);
        when(attendanceRepository.findAttendanceByChildIdAndMonthAndYear(3L, 2, 2024)).thenReturn(attendanceEntities);

        when(attendanceRepository.findAttendanceByChildIdAndMonthAndYear(3L, 2, 2023)).thenReturn(Collections.emptyList());
        when(attendanceRepository.findAttendanceByChildIdAndMonthAndYear(3L, 3, 2024)).thenReturn(Collections.emptyList());

        ChildEntity child = new ChildEntity();
        child.setFirstName("Mirai");
        child.setLastName("Zura");
        child.setParentId(2L);
        child.setSchoolId(2L);
        child.setId(3L);
        List<ChildEntity> childEntities = new ArrayList<>();
        childEntities.add(child);
        when(childRepository.findChildEntitiesByParentId(2L)).thenReturn(childEntities);
        when(childRepository.findChildEntitiesBySchoolId(2L)).thenReturn(childEntities);

        childEntities = new ArrayList<>();
        child = new ChildEntity();
        child.setFirstName("Leona");
        child.setLastName("Ceena");
        child.setParentId(1L);
        child.setSchoolId(1L);
        child.setId(2L);
        childEntities.add(child);
        child = new ChildEntity();
        child.setFirstName("John");
        child.setLastName("Ceena");
        child.setParentId(1L);
        child.setSchoolId(1L);
        child.setId(1L);
        childEntities.add(child);
        when(childRepository.findChildEntitiesByParentId(1L)).thenReturn(childEntities);
        when(childRepository.findChildEntitiesBySchoolId(1L)).thenReturn(childEntities);

        ParentEntity parent = new ParentEntity();
        parent.setFirstName("Alex");
        parent.setLastName("Alex");
        parent.setId(2L);
        when(parentRepository.findParentEntityById(2L)).thenReturn(Optional.of(parent));
        parent = new ParentEntity();
        parent.setFirstName("Tim");
        parent.setLastName("Shaw");
        parent.setId(1L);
        when(parentRepository.findParentEntityById(1L)).thenReturn(Optional.of(parent));

        SchoolEntity school = new SchoolEntity();
        school.setName("Mira");
        school.setHourPrice(10.5);
        school.setId(2L);
        when(schoolRepository.findSchoolEntityById(2L)).thenReturn(Optional.of(school));
        school = new SchoolEntity();
        school.setName("Oxford");
        school.setHourPrice(10.0);
        school.setId(1L);
        when(schoolRepository.findSchoolEntityById(1L)).thenReturn(Optional.of(school));
    }

}