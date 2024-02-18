package com.settlementproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class SettlementParentDTO {

    private String firstName;
    private String lastName;
    private List<SettlementChildDTO> children;

}