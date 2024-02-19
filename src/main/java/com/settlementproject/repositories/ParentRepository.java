package com.settlementproject.repositories;

import com.settlementproject.entities.ParentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ParentRepository extends JpaRepository<ParentEntity, UUID> {

    Optional<ParentEntity> findParentEntityById(UUID id);

}
