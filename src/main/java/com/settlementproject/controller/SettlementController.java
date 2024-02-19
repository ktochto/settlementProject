package com.settlementproject.controller;

import com.settlementproject.entities.ParentEntity;
import com.settlementproject.exceptions.Exceptions;
import com.settlementproject.exceptions.ParentNotExistException;
import com.settlementproject.services.SettlementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

@Log4j2
@Controller
@RequiredArgsConstructor
public class SettlementController {
    private final SettlementService settlementService;


    @GetMapping
    public ResponseEntity<String> getSettlementForParent(ParentEntity parent, String month) {
        try {
            String response = settlementService.getSettlementForParent(parent, month);
            return ResponseEntity.ok().body(response);
        } catch (ParentNotExistException e) {
            return ResponseEntity.status(400).body(Exceptions.NO_PARENT.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Exceptions.BAD_REQUEST.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<String> getSettlementForId(UUID id, int month) {
        String response = settlementService.getSettlementForId(id, month);
        return ResponseEntity.ok().body(response);
    }

}