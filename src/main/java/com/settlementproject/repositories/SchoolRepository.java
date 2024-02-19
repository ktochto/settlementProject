package com.settlementproject.repositories;

import com.settlementproject.entities.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SchoolRepository extends JpaRepository<SchoolEntity, UUID> {

    Optional<SchoolEntity> findSchoolEntityById(UUID id);

}