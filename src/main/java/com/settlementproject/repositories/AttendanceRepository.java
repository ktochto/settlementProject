package com.settlementproject.repositories;

import com.settlementproject.entities.AttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Long> {

    @Query("select a from AttendanceEntity a where month(a.entryDate) = :month and month(a.exitDate) = :month " +
            "and year (a.entryDate) = :year and year(a.exitDate) = :year " +
            "and a.childId = :id")
    List<AttendanceEntity> findAttendanceByChildIdAndMonthAndYear(@Param("id") Long id, @Param("month") int month, @Param("year") int year);

}