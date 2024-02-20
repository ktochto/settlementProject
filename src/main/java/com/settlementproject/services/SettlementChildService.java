package com.settlementproject.services;

import com.settlementproject.dtos.SettlementChildDTO;
import com.settlementproject.entities.AttendanceEntity;
import com.settlementproject.entities.ChildEntity;
import com.settlementproject.entities.SchoolEntity;
import com.settlementproject.exceptions.SchoolNotExistException;
import com.settlementproject.exceptions.WrongDateInpuException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SettlementChildService {
    private final AttendanceService attendanceService;
    private final SchoolService schoolService;


    public List<SettlementChildDTO> createSettlementsForChildren(String inputDate, List<ChildEntity> parentsChildren) throws SchoolNotExistException, WrongDateInpuException {
        List<SettlementChildDTO> settlementChildren = new ArrayList<>();
        for (ChildEntity child : parentsChildren) {
            SettlementChildDTO settlementChild = createSettlementChild(inputDate, child);
            settlementChildren.add(settlementChild);
        }
        return settlementChildren;
    }

    private SettlementChildDTO createSettlementChild(String inputDate, ChildEntity child) throws SchoolNotExistException, WrongDateInpuException {
        SettlementChildDTO settlementChild = new SettlementChildDTO();
        setChildNameData(child, settlementChild);
        findAndSetChildAttendanceData(inputDate, child, settlementChild);
        return settlementChild;
    }

    private void setChildNameData(ChildEntity child, SettlementChildDTO settlementChild) {
        settlementChild.setFirstName(child.getFirstName());
        settlementChild.setLastName(child.getLastName());
    }

    private void findAndSetChildAttendanceData(String inputDate, ChildEntity child, SettlementChildDTO settlementChild) throws SchoolNotExistException, WrongDateInpuException {
        int payedHours = 0;
        int totalPrice = 0;
        SchoolEntity school = schoolService.getSchoolById(child.getSchoolId());

        List<AttendanceEntity> attendanceEntities = attendanceService.findAttendanceByChildIdAndMonth(child.getId(), inputDate);
        for (AttendanceEntity attendance : attendanceEntities) {
            int payedHoursForOneDay = attendanceService.howManyPayedHours(attendance);
            payedHours += payedHoursForOneDay;
            totalPrice += attendanceService.getPrice(school.getHourPrice(), payedHoursForOneDay);
        }
        settlementChild.setPayedHours(payedHours);
        settlementChild.setTotalPrice(totalPrice);
    }
}