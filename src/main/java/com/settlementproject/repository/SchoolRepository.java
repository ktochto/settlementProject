package com.settlementproject.repository;

import com.settlementproject.dto.SchoolDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SchoolRepository extends JpaRepository<SchoolDTO, UUID> {


}