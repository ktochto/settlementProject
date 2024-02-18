package com.settlementproject.repository;

import com.settlementproject.dto.ParentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ParentRepository extends JpaRepository<ParentDTO, UUID> {

    Optional<ParentDTO> getParentById(UUID id);

}
