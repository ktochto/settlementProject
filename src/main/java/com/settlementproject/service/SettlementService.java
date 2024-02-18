package com.settlementproject.service;

import com.google.gson.Gson;
import com.settlementproject.dto.ParentDTO;
import com.settlementproject.dto.SettlementIdDTO;
import com.settlementproject.dto.SettlementParentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SettlementService {
    private final ParentService parentService;
    private final AttendanceService attendanceService;


    public String getSettlementForParent(ParentDTO input, int month) {
        ParentDTO parent = parentService.getParent(input.getId());
        SettlementParentDTO settlementParent = new SettlementParentDTO();
        return getSettlementForParentJSON(settlementParent);
    }

    public String getSettlementForId(UUID id, int month) {
        SettlementIdDTO settlementId = new SettlementIdDTO();
        return getSettlementForIdJSON(settlementId);
    }

    private String getSettlementForParentJSON(SettlementParentDTO settlementParentDTO) {
        Gson gson = new Gson();
        return gson.toJson(settlementParentDTO);
    }

    private String getSettlementForIdJSON(SettlementIdDTO settlementIdDTO) {
        Gson gson = new Gson();
        return gson.toJson(settlementIdDTO);
    }

}