package com.settlementproject.repositories;

import com.settlementproject.entities.ChildEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChildRepository extends JpaRepository<ChildEntity, UUID> {

    List<ChildEntity> findChildEntitiesByParentId(UUID parentId);

}