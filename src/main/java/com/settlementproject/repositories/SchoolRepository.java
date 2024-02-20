package com.settlementproject.repositories;

import com.settlementproject.entities.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SchoolRepository extends JpaRepository<SchoolEntity, Long> {

    Optional<SchoolEntity> findSchoolEntityById(Long id);

}