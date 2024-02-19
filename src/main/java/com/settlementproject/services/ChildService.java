package com.settlementproject.services;

import com.settlementproject.entities.ChildEntity;
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

}