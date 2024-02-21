package com.settlementproject.repositories;

import com.settlementproject.entities.ChildEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildRepository extends JpaRepository<ChildEntity, Long> {

    List<ChildEntity> findChildEntitiesByParentId(Long parentId);

    List<ChildEntity> findChildEntitiesBySchoolId(Long schoolId);

}