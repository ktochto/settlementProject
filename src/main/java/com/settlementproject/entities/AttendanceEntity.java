package com.settlementproject.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Table
public class AttendanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long childId;
    @Column(nullable = false)
    private LocalDateTime entryDate;
    @Column(nullable = false)
    private LocalDateTime exitDate;

}