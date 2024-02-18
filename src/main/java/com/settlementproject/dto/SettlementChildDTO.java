package com.settlementproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class SettlementChildDTO {

    private String firstName;
    private String lastName;
    private double totalPrice;
    private int payedHours;

}