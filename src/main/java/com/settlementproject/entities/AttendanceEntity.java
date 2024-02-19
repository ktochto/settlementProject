package com.settlementproject.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class AttendanceEntity {

    @Id
    private UUID id;
    private UUID childId;
    private LocalDateTime entryDate;
    private LocalDateTime exitDate;

}