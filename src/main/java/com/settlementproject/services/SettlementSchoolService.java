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

@Service
@RequiredArgsConstructor
public class SettlementSchoolService {
    private final SettlementChildService settlementChildService;
    private final SchoolService schoolService;
    private final ChildService childService;


    public SettlementSchoolDTO createSettlementForSchool(Long inputSchoolId, String inputDate) throws SchoolNotExistException, WrongDateInpuException {
        SchoolEntity school = schoolService.getSchoolById(inputSchoolId);
        SettlementSchoolDTO settlementSchool = new SettlementSchoolDTO();
        setSchoolData(school, settlementSchool);
        calculateChildrenSettlementsAndSet(inputSchoolId, inputDate, settlementSchool);
        return settlementSchool;
    }

    private void setSchoolData(SchoolEntity school, SettlementSchoolDTO settlementSchool) {
        settlementSchool.setName(school.getName());
    }

    private void calculateChildrenSettlementsAndSet(Long schoolId, String inputDate, SettlementSchoolDTO settlementSchool) throws SchoolNotExistException, WrongDateInpuException {
        List<ChildEntity> schoolsChildren = childService.getChildListBySchoolId(schoolId);
        List<SettlementChildDTO> settlementsForChildren = settlementChildService.createSettlementsForChildren(inputDate, schoolsChildren);
        settlementSchool.setChildren(settlementsForChildren);
    }

}