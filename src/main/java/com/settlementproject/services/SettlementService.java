package com.settlementproject.services;

import com.google.gson.Gson;
import com.settlementproject.dtos.SettlementParentDTO;
import com.settlementproject.dtos.SettlementSchoolDTO;
import com.settlementproject.entities.ParentEntity;
import com.settlementproject.exceptions.ParentNotExistException;
import com.settlementproject.exceptions.SchoolNotExistException;
import com.settlementproject.exceptions.WrongDateInpuException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class SettlementService {
    private final SettlementParentService settlementParentService;
    private final SettlementSchoolService settlementSchoolService;


    public String getSettlementForParent(ParentEntity inputParent, String inputDate) throws ParentNotExistException, SchoolNotExistException, WrongDateInpuException {
        SettlementParentDTO settlementParent = settlementParentService.createSettlementForParent(inputParent, inputDate);
        return settlementParentToJSON(settlementParent);
    }

    public String getSettlementForSchool(UUID inputId, String inputDate) throws SchoolNotExistException, WrongDateInpuException {
        SettlementSchoolDTO settlementSchool = settlementSchoolService.createSettlementForSchool(inputId, inputDate);
        return getSettlementForSchoolJSON(settlementSchool);
    }

    private String settlementParentToJSON(SettlementParentDTO settlementParentDTO) {
        Gson gson = new Gson();
        return gson.toJson(settlementParentDTO);
    }

    private String getSettlementForSchoolJSON(SettlementSchoolDTO settlementSchoolDTO) {
        Gson gson = new Gson();
        return gson.toJson(settlementSchoolDTO);
    }

}