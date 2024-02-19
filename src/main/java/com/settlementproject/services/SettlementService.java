package com.settlementproject.services;

import com.google.gson.Gson;
import com.settlementproject.dtos.SettlementChildDTO;
import com.settlementproject.dtos.SettlementIdDTO;
import com.settlementproject.dtos.SettlementParentDTO;
import com.settlementproject.entities.AttendanceEntity;
import com.settlementproject.entities.ChildEntity;
import com.settlementproject.entities.ParentEntity;
import com.settlementproject.entities.SchoolEntity;
import com.settlementproject.exceptions.ParentNotExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class SettlementService {
    private final AttendanceService attendanceService;
    private final ParentService parentService;
    private final SchoolService schoolService;
    private final ChildService childService;


    public String getSettlementForParent(ParentEntity inputParent, String inputDate) throws ParentNotExistException {
        SettlementParentDTO settlementParent = createSettlementForParent(inputParent, inputDate);
        return settlementParentToJSON(settlementParent);
    }

    public String getSettlementForId(UUID id, int month) {
        SettlementIdDTO settlementId = new SettlementIdDTO();
        return getSettlementForIdJSON(settlementId);
    }

    private SettlementParentDTO createSettlementForParent(ParentEntity inputParent, String inputDate) throws ParentNotExistException {
        ParentEntity parent = findParent(inputParent);
        return createSettlement(inputDate, parent);
    }

    private SettlementParentDTO createSettlement(String inputDate, ParentEntity parent) {
        SettlementParentDTO settlementParent = new SettlementParentDTO();
        setParentData(parent, settlementParent);
        setChildrenData(parent, inputDate, settlementParent);
        return settlementParent;
    }

    private static void setParentData(ParentEntity parent, SettlementParentDTO settlementParent) {
        settlementParent.setFirstName(parent.getFirstName());
        settlementParent.setLastName(parent.getLastName());
    }

    private void setChildrenData(ParentEntity parent, String inputDate, SettlementParentDTO settlementParent) {
        List<ChildEntity> parentsChildren = childService.getChildListByParentId(parent.getId());
        if (parentsChildren.isEmpty()) {
            return;
        }
        List<SettlementChildDTO> settlementsForChildren = createSettlementsForChildren(inputDate, parentsChildren);
        settlementParent.setChildren(settlementsForChildren);
    }

    private List<SettlementChildDTO> createSettlementsForChildren(String inputDate, List<ChildEntity> parentsChildren) {
        List<SettlementChildDTO> settlementChildren = new ArrayList<>();
        for (ChildEntity child : parentsChildren) {
            SettlementChildDTO settlementChild = createSettlementChild(inputDate, child);
            settlementChildren.add(settlementChild);
        }
        return settlementChildren;
    }

    private SettlementChildDTO createSettlementChild(String inputDate, ChildEntity child) {
        SettlementChildDTO settlementChild = new SettlementChildDTO();
        setChildNameData(child, settlementChild);
        findAndSetChildAttendanceData(inputDate, child, settlementChild);
        return settlementChild;
    }

    private static void setChildNameData(ChildEntity child, SettlementChildDTO settlementChild) {
        settlementChild.setFirstName(child.getFirstName());
        settlementChild.setLastName(child.getLastName());
    }

    private void findAndSetChildAttendanceData(String inputDate, ChildEntity child, SettlementChildDTO settlementChild) {
        int payedHours = 0;
        int totalPrice = 0;
        SchoolEntity school = schoolService.getSchoolById(child.getSchoolId());
//        List<AttendanceEntity> attendanceEntities = attendanceService.getAttendanceByChildId(child.getId());
        List<AttendanceEntity> attendanceEntities = attendanceService.findAttendanceByChildIdAnaMonth(child.getId(), inputDate);
        for (AttendanceEntity attendance : attendanceEntities) {
            int payedHoursForOneDay = attendanceService.howManyPayedHours(attendance);
            payedHours += payedHoursForOneDay;
            totalPrice += attendanceService.getPrice(school.getHourPrice(), payedHoursForOneDay);
        }
        settlementChild.setPayedHours(payedHours);
        settlementChild.setTotalPrice(totalPrice);
    }

    private ParentEntity findParent(ParentEntity input) throws ParentNotExistException {
        ParentEntity parent = parentService.getParentById(input.getId());
        if (parent == null)
            throw new ParentNotExistException();
        return parent;
    }

    private String settlementParentToJSON(SettlementParentDTO settlementParentDTO) {
        Gson gson = new Gson();
        return gson.toJson(settlementParentDTO);
    }

    private String getSettlementForIdJSON(SettlementIdDTO settlementIdDTO) {
        Gson gson = new Gson();
        return gson.toJson(settlementIdDTO);
    }

}