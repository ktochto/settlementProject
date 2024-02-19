package com.settlementproject.services;

import com.settlementproject.entities.ChildEntity;
import com.settlementproject.entities.ParentEntity;
import com.settlementproject.entities.SchoolEntity;
import com.settlementproject.repositories.ChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChildService {
    private final ChildRepository childRepository;


    public List<ChildEntity> getChildListByParentId(UUID parentId) {
        return childRepository.findChildEntitiesByParentId(parentId);
    }

    public List<ChildEntity> getChildListBySchoolId(UUID schoolId) {
        return childRepository.findChildEntitiesBySchoolId(schoolId);
    }

    public ChildEntity create(SchoolEntity school, ParentEntity parent) {
        ChildEntity child = new ChildEntity();
        child.setFirstName("John");
        child.setLastName("Ceena");
        child.setParentId(parent.getId());
        child.setSchoolId(school.getId());
        return childRepository.save(child);
    }

}