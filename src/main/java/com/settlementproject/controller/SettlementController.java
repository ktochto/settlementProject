package com.settlementproject.controller;

import com.settlementproject.dto.ParentDTO;
import com.settlementproject.service.SettlementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

@Log4j2
@Controller
@RequiredArgsConstructor
public class SettlementController {
    private final SettlementService settlementService;


    @GetMapping
    public String getSettlementForParent(ParentDTO parent, int month) {
        return settlementService.getSettlementForParent(parent, month);
    }

    @GetMapping
    public String getSettlementForId(UUID id, int month) {
        return settlementService.getSettlementForId(id, month);
    }

}