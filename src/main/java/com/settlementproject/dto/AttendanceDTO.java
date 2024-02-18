package com.settlementproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDTO {

    private int id;
    private int childId;
    private Date entryDate;
    private Date exitDate;

}