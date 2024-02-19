package com.settlementproject.repositories;

import com.settlementproject.entities.AttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceEntity, UUID> {

    @Query("select a from AttendanceEntity a where month(a.entryDate) = :month and month(a.exitDate) = :month and a.childId = :id")
    List<AttendanceEntity> findAttendanceEntitiesByChildIdAnaEntryDateAndExitDate(@Param("id") UUID id, @Param("month") int month);

}