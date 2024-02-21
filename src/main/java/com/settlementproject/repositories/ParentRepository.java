package com.settlementproject.repositories;

import com.settlementproject.entities.ParentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParentRepository extends JpaRepository<ParentEntity, Long> {

    Optional<ParentEntity> findParentEntityById(Long id);

}
