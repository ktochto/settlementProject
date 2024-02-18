package com.settlementproject.repository;

import com.settlementproject.dto.AttendanceDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceDTO, UUID> {

    Optional<AttendanceDTO> getAttendanceById(UUID id);

}