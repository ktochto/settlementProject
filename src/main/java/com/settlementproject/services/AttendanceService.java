package com.settlementproject.services;

import com.settlementproject.entities.AttendanceEntity;
import com.settlementproject.exceptions.WrongDateInpuException;
import com.settlementproject.repositories.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;

    @Value("${date.format}")
    private String dateFormat;


    public List<AttendanceEntity> findAttendanceByChildIdAndMonth(Long childId, String inputDate) throws WrongDateInpuException {
        LocalDate monthDate = getLocalDateOfInputDate(inputDate);
        List<AttendanceEntity> attendanceEntityList = attendanceRepository.findAttendanceByChildIdAndMonth(childId, monthDate.getMonthValue());
        return Objects.requireNonNullElseGet(attendanceEntityList, ArrayList::new);
    }

    public int howManyPayedHours(AttendanceEntity attendance) {
        int payedHour = 0;
        LocalDateTime entryDate = attendance.getEntryDate();
        LocalDateTime exitDate = attendance.getExitDate();

        if (entryDate.getHour() < 7)
            payedHour += 7 - entryDate.getHour();
        if (exitDate.getHour() > 12)
            payedHour += exitDate.getHour() - 12;

        return payedHour;
    }

    public double getPrice(double hourPrice, int payedHours) {
        return hourPrice * payedHours;
    }

    private LocalDate getLocalDateOfInputDate(String month) throws WrongDateInpuException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        try {
            return LocalDate.parse(month, formatter);
        } catch (DateTimeParseException e) {
            throw new WrongDateInpuException();
        }
    }

}