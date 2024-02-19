package com.settlementproject.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class SettlementIdDTO {

    private String name;
    private List<SettlementParentDTO> parents;

}