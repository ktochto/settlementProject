package com.settlementproject.services;

import com.google.gson.Gson;
import com.settlementproject.dtos.SettlementParentDTO;
import com.settlementproject.dtos.SettlementSchoolDTO;
import com.settlementproject.exceptions.ParentNotExistException;
import com.settlementproject.exceptions.SchoolNotExistException;
import com.settlementproject.exceptions.WrongDateInpuException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SettlementService {
    private final SettlementParentService settlementParentService;
    private final SettlementSchoolService settlementSchoolService;

    private final Gson gson = new Gson();


    public String getSettlementForParent(Long parentId, String inputDate) throws ParentNotExistException, SchoolNotExistException, WrongDateInpuException {
        SettlementParentDTO settlementParent = settlementParentService.createSettlementForParent(parentId, inputDate);
        return convertSettlementToJSON(settlementParent);
    }

    public String getSettlementForSchool(Long inputId, String inputDate) throws SchoolNotExistException, WrongDateInpuException {
        SettlementSchoolDTO settlementSchool = settlementSchoolService.createSettlementForSchool(inputId, inputDate);
        return convertSettlementToJSON(settlementSchool);
    }

    private String convertSettlementToJSON(Object object) {
        return gson.toJson(object);
    }

}