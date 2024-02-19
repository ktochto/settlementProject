package com.settlementproject.services;

import com.settlementproject.dtos.SettlementChildDTO;
import com.settlementproject.dtos.SettlementSchoolDTO;
import com.settlementproject.entities.ChildEntity;
import com.settlementproject.entities.SchoolEntity;
import com.settlementproject.exceptions.SchoolNotExistException;
import com.settlementproject.exceptions.WrongDateInpuException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SettlementSchoolService {
    private final SettlementChildService settlementChildService;
    private final SchoolService schoolService;
    private final ChildService childService;


    public SettlementSchoolDTO createSettlementForSchool(UUID inputId, String inputDate) throws SchoolNotExistException, WrongDateInpuException {
        SchoolEntity school = schoolService.getSchoolById(inputId);
        return createSettlement(inputDate, school);
    }

    private SettlementSchoolDTO createSettlement(String inputDate, SchoolEntity school) throws SchoolNotExistException, WrongDateInpuException {
        SettlementSchoolDTO settlementSchool = new SettlementSchoolDTO();
        setSchoolData(school, settlementSchool);
        setChildrenData(school, inputDate, settlementSchool);
        return settlementSchool;
    }

    private static void setSchoolData(SchoolEntity school, SettlementSchoolDTO settlementSchool) {
        settlementSchool.setName(school.getName());
    }

    private void setChildrenData(SchoolEntity school, String inputDate, SettlementSchoolDTO settlementSchool) throws SchoolNotExistException, WrongDateInpuException {
        List<ChildEntity> schoolsChildren = childService.getChildListBySchoolId(school.getId());
        if (schoolsChildren.isEmpty()) {
            return;
        }
        List<SettlementChildDTO> settlementsForChildren = settlementChildService.createSettlementsForChildren(inputDate, schoolsChildren);
        settlementSchool.setChildren(settlementsForChildren);
    }

}