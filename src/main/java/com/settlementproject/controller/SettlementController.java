package com.settlementproject.controller;

import com.settlementproject.dtos.SettlementParentDTO;
import com.settlementproject.dtos.SettlementSchoolDTO;
import com.settlementproject.exceptions.Exceptions;
import com.settlementproject.exceptions.ParentNotExistException;
import com.settlementproject.exceptions.SchoolNotExistException;
import com.settlementproject.exceptions.WrongDateInpuException;
import com.settlementproject.services.SettlementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
public class SettlementController {
    private final SettlementService settlementService;


    @Operation(summary = "Calculate and return settlement for a parent by parent id and date.",
            description = "Needs parent id and date not null. Date format store in app.properties")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SettlementParentDTO.class))}
            ),
            @ApiResponse(responseCode = "404", description = "Parent with this id isn't exist.", content = @Content),
            @ApiResponse(responseCode = "404", description = "School with this id isn't exist.", content = @Content),
            @ApiResponse(responseCode = "400", description = "Wrong date input format.", content = @Content)})
    @GetMapping("/parent")
    public ResponseEntity<?> getSettlementForParent(@RequestParam("parentId") @NonNull @Validated Long inputParentId,
                                                    @RequestParam("date") @NonNull @Validated String inputDate) {
        log.info("Getting settlement for parent: start");
        try {
            String response = settlementService.getSettlementForParent(inputParentId, inputDate);
            log.info("Getting settlement for parent: success");
            return ResponseEntity.ok().body(response);
        } catch (ParentNotExistException e) {
            log.error("Getting settlement for parent: no parent", e);
            log.error(e.getMessage());
            return ResponseEntity.status(404).body(Exceptions.NO_PARENT.getMessage());
        } catch (SchoolNotExistException e) {
            log.error("Getting settlement for parent: no school", e);
            log.error(e.getMessage());
            return ResponseEntity.status(404).body(Exceptions.NO_SCHOOL.getMessage());
        } catch (WrongDateInpuException e) {
            log.error("Getting settlement for parent: wrong date input format", e);
            log.error(e.getMessage());
            return ResponseEntity.status(400).body(Exceptions.WRONG_INPUT.getMessage());
        } catch (Exception e) {
            log.error("Getting settlement for parent: internal server error", e);
            log.error(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @Operation(summary = "Calculate and return settlement for a school by school id and date.",
            description = "Needs parent id and date not null. Date format store in app.properties")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SettlementSchoolDTO.class))}
            ),
            @ApiResponse(responseCode = "404", description = "School with this id isn't exist.", content = @Content),
            @ApiResponse(responseCode = "400", description = "Wrong date input format.", content = @Content)})
    @GetMapping("/school")
    public ResponseEntity<String> getSettlementForSchool(@RequestParam("schoolId") @NonNull @Validated Long inputSchoolId,
                                                         @RequestParam("date") @NonNull @Validated String inputDate) {
        log.info("Getting settlement for school: start");
        try {
            String response = settlementService.getSettlementForSchool(inputSchoolId, inputDate);
            log.info("Getting settlement for school: success");
            return ResponseEntity.ok().body(response);
        } catch (SchoolNotExistException e) {
            log.error("Getting settlement for school: no school");
            log.error(e.getMessage());
            return ResponseEntity.status(404).body(Exceptions.NO_SCHOOL.getMessage());
        } catch (WrongDateInpuException e) {
            log.error("Getting settlement for school: wrong date input format");
            log.error(e.getMessage());
            return ResponseEntity.status(400).body(Exceptions.WRONG_INPUT.getMessage());
        } catch (Exception e) {
            log.error("Getting settlement for school: bad request");
            log.error(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }
}