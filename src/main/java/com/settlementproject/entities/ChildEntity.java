package com.settlementproject.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Table(name = "child")
public class ChildEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private UUID parentId;
    @NonNull
    private UUID schoolId;

}