package com.settlementproject.service;

import com.settlementproject.dto.AttendanceDTO;
import com.settlementproject.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;


    private AttendanceDTO getAttendance(UUID id) {
        Optional<AttendanceDTO> attendanceDTO = attendanceRepository.getAttendanceById(id);
        return attendanceDTO.orElse(null);
    }

    private int howManyPayedHours(AttendanceDTO attendance) {
        LocalDateTime entryDate = attendance.getEntryDate();
        LocalDateTime exitDate = attendance.getExitDate();
        int payedHour = 0;

        if (entryDate.isBefore(LocalDateTime.of(entryDate.getYear(), entryDate.getMonth(), entryDate.getDayOfMonth(), 7, 0)))
            payedHour += 7 - entryDate.getHour();
        if (exitDate.isAfter(LocalDateTime.of(entryDate.getYear(), entryDate.getMonth(), entryDate.getDayOfMonth(), 12, 0)))
            payedHour += exitDate.getHour() - 12;

        return payedHour;
    }

    private double getPrice(double hourPrice, int payedHours) {
        return hourPrice * payedHours;
    }

}