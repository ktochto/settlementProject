package com.settlementproject.controller;

import com.settlementproject.entities.ParentEntity;
import com.settlementproject.exceptions.Exceptions;
import com.settlementproject.exceptions.ParentNotExistException;
import com.settlementproject.exceptions.SchoolNotExistException;
import com.settlementproject.exceptions.WrongDateInpuException;
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


    @GetMapping("parent")
    public ResponseEntity<String> getSettlementForParent(ParentEntity parent, String inputDate) {
        log.info("Getting settlement for parent: start");
        try {
            String response = settlementService.getSettlementForParent(parent, inputDate);
            log.info("Getting settlement for parent: success");
            return ResponseEntity.ok().body(response);
        } catch (ParentNotExistException e) {
            log.info("Getting settlement for parent: no parent");
            return ResponseEntity.status(400).body(Exceptions.NO_PARENT.getMessage());
        } catch (SchoolNotExistException e) {
            log.info("Getting settlement for parent: no school");
            return ResponseEntity.status(400).body(Exceptions.NO_SCHOOL.getMessage());
        } catch (WrongDateInpuException e) {
            log.info("Getting settlement for parent: wrong date input format");
            return ResponseEntity.status(400).body(Exceptions.WRONG_INPUT.getMessage());
        } catch (Exception e) {
            log.info("Getting settlement for parent: bad request");
            return ResponseEntity.status(400).body(Exceptions.BAD_REQUEST.getMessage());
        }
    }

    @GetMapping("school")
    public ResponseEntity<String> getSettlementForSchool(UUID id, String inputDate) {
        log.info("Getting settlement for school: start");
        try {
            String response = settlementService.getSettlementForSchool(id, inputDate);
            return ResponseEntity.ok().body(response);
        } catch (SchoolNotExistException e) {
            log.info("Getting settlement for school: no school");
            return ResponseEntity.status(400).body(Exceptions.NO_SCHOOL.getMessage());
        } catch (WrongDateInpuException e) {
            log.info("Getting settlement for school: wrong date input format");
            return ResponseEntity.status(400).body(Exceptions.WRONG_INPUT.getMessage());
        } catch (Exception e) {
            log.info("Getting settlement for school: bad request");
            return ResponseEntity.status(400).body(Exceptions.BAD_REQUEST.getMessage());
        }
    }

}