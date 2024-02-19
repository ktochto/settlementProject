package com.settlementproject.services;

import com.settlementproject.entities.ParentEntity;
import com.settlementproject.exceptions.ParentNotExistException;
import com.settlementproject.repositories.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParentService {
    private final ParentRepository parentRepository;


    public ParentEntity getParentById(UUID id) throws ParentNotExistException {
        Optional<ParentEntity> parentDTO = parentRepository.findParentEntityById(id);
        if (parentDTO.isEmpty())
            throw new ParentNotExistException();
        return parentDTO.get();
    }

    public ParentEntity create() {
        ParentEntity parent = new ParentEntity();
        parent.setFirstName("Tim");
        parent.setLastName("Shaw");
        return parentRepository.save(parent);
    }

}