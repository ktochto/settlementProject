package com.settlementproject.repositories;

import com.settlementproject.entities.AttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceEntity, UUID> {

    Optional<AttendanceEntity> findAttendanceEntityById(UUID id);

    List<AttendanceEntity> findAttendanceEntitiesByChildId(UUID id);

    @Query("select a from AttendanceEntity a where month(a.entryDate) = :month and month(a.exitDate) = :month")
    List<AttendanceEntity> findAttendanceEntitiesByChildIdAnaEntryDateAndExitDate(UUID id, int month);

}