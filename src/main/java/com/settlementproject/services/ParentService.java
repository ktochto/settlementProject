package com.settlementproject.services;

import com.settlementproject.entities.ParentEntity;
import com.settlementproject.repositories.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParentService {
    private final ParentRepository parentRepository;


    public ParentEntity getParentById(UUID id) {
        Optional<ParentEntity> parentDTO = parentRepository.findParentEntityById(id);
        return parentDTO.orElse(null);
    }

    private void createParent(ParentEntity parentEntity) {
        parentRepository.save(parentEntity);
    }

}