package com.settlementproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChildDTO {

    private int id;
    private String firstName;
    private String lastName;
    private int parentId;
    private int schoolId;

}