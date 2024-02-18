package com.settlementproject.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Entity
@Table
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class ChildDTO {

    @Id
    private UUID id;
    private String firstName;
    private String lastName;
    private UUID parentId;
    private UUID schoolId;

}