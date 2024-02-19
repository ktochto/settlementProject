package com.settlementproject.services;

import com.settlementproject.entities.AttendanceEntity;
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


    public List<AttendanceEntity> getAttendanceByChildId(UUID childId) {
        return attendanceRepository.findAttendanceEntitiesByChildId(childId);
    }

    public List<AttendanceEntity> findAttendanceByChildIdAnaMonth(UUID childId, String inputDate) {
        LocalDate monthDate = getLocalDateOfInputDate(inputDate);
        return attendanceRepository.findAttendanceEntitiesByChildIdAnaEntryDateAndExitDate(childId, monthDate.getMonthValue());
    }

    private LocalDate getLocalDateOfInputDate(String month) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(month, formatter);
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

}