package com.settlementproject.services;

import com.settlementproject.entities.AttendanceEntity;
import com.settlementproject.entities.ChildEntity;
import com.settlementproject.exceptions.WrongDateInpuException;
import com.settlementproject.repositories.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;


    public List<AttendanceEntity> findAttendanceByChildIdAnaMonth(UUID childId, String inputDate) throws WrongDateInpuException {
        LocalDate monthDate = getLocalDateOfInputDate(inputDate);
        return attendanceRepository.findAttendanceEntitiesByChildIdAnaEntryDateAndExitDate(childId, monthDate.getMonthValue());
    }

    public int howManyPayedHours(AttendanceEntity attendance) {
        LocalDateTime entryDate = attendance.getEntryDate();
        LocalDateTime exitDate = attendance.getExitDate();
        int payedHour = 0;

        if (entryDate.isBefore(LocalDateTime.of(entryDate.getYear(), entryDate.getMonth(), entryDate.getDayOfMonth(), 7, 0)))
            payedHour += 7 - entryDate.getHour();
        if (exitDate.isAfter(LocalDateTime.of(entryDate.getYear(), entryDate.getMonth(), entryDate.getDayOfMonth(), 12, 0)))
            payedHour += exitDate.getHour() - 12;

        return payedHour;
    }

    public double getPrice(double hourPrice, int payedHours) {
        return hourPrice * payedHours;
    }

    public void create(ChildEntity child) {
        AttendanceEntity attendance = new AttendanceEntity();
        attendance.setEntryDate(LocalDateTime.of(2024, 1, 1, 6, 20));
        attendance.setExitDate(LocalDateTime.of(2024, 1, 1, 18, 2));
        attendance.setChildId(child.getId());
        attendanceRepository.save(attendance);

        attendance.setEntryDate(LocalDateTime.of(2024, 1, 2, 7, 20));
        attendance.setExitDate(LocalDateTime.of(2024, 1, 2, 18, 2));
        attendance.setChildId(child.getId());
        attendanceRepository.save(attendance);

        attendance.setEntryDate(LocalDateTime.of(2024, 1, 3, 7, 20));
        attendance.setExitDate(LocalDateTime.of(2024, 1, 3, 11, 2));
        attendance.setChildId(child.getId());
        attendanceRepository.save(attendance);
    }

    private LocalDate getLocalDateOfInputDate(String month) throws WrongDateInpuException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            return LocalDate.parse(month, formatter);
        } catch (Exception e) {
            throw new WrongDateInpuException();
        }
    }

}