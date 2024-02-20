package com.settlementproject.controller;

import com.settlementproject.exceptions.Exceptions;
import com.settlementproject.exceptions.ParentNotExistException;
import com.settlementproject.exceptions.SchoolNotExistException;
import com.settlementproject.exceptions.WrongDateInpuException;
import com.settlementproject.services.SettlementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@Controller
@RequiredArgsConstructor
public class SettlementController {
    private final SettlementService settlementService;


    @GetMapping("/parent")
    public ResponseEntity<?> getSettlementForParent(@RequestParam("id") @NonNull @Validated Long parentId,
                                                    @RequestParam("date") @NonNull @Validated @DateTimeFormat(pattern = "dd/MM/yyyy") String inputDate) {
        log.info("Getting settlement for parent: start");
        try {
            String response = settlementService.getSettlementForParent(parentId, inputDate);
            log.info("Getting settlement for parent: success");
            return ResponseEntity.ok(response);
        } catch (ParentNotExistException e) {
            log.error("Getting settlement for parent: no parent", e);
            return new ResponseEntity<>(new ErrorResponse(Exceptions.NO_PARENT.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        } catch (SchoolNotExistException e) {
            log.error("Getting settlement for parent: no school", e);
            return new ResponseEntity<>(new ErrorResponse(Exceptions.NO_SCHOOL.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        } catch (WrongDateInpuException e) {
            log.error("Getting settlement for parent: wrong date input format", e);
            return new ResponseEntity<>(new ErrorResponse(Exceptions.WRONG_INPUT.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Getting settlement for parent: internal server error", e);
            return new ResponseEntity<>(new ErrorResponse(Exceptions.INTERNAL_SERVER_ERROR.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("school")
    public ResponseEntity<String> getSettlementForSchool(@RequestParam("id") @NonNull @Validated Long id,
                                                         @RequestParam("date") @NonNull @Validated String inputDate) {
        log.info("Getting settlement for school: start");
        try {
            String response = settlementService.getSettlementForSchool(id, inputDate);
            log.info("Getting settlement for school: success");
            return ResponseEntity.ok().body(response);
        } catch (SchoolNotExistException e) {
            log.error("Getting settlement for school: no school");
            return ResponseEntity.status(400).body(Exceptions.NO_SCHOOL.getMessage());
        } catch (WrongDateInpuException e) {
            log.error("Getting settlement for school: wrong date input format");
            return ResponseEntity.status(400).body(Exceptions.WRONG_INPUT.getMessage());
        } catch (Exception e) {
            log.error("Getting settlement for school: bad request");
            return ResponseEntity.status(500).build();
        }
    }

    static class ErrorResponse {
        private final String message;
        private final int status;

        public ErrorResponse(String message, int status) {
            this.message = message;
            this.status = status;
        }

        // Getters
        public String getMessage() {
            return message;
        }

        public int getStatus() {
            return status;
        }
    }

}