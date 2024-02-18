package com.settlementproject.service;

import com.settlementproject.dto.ParentDTO;
import com.settlementproject.repository.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParentService {
    private final ParentRepository parentRepository;


    public ParentDTO getParent(UUID id) {
        Optional<ParentDTO> parentDTO = parentRepository.getParentById(id);
        return parentDTO.orElse(null);
    }

    private void createParent(ParentDTO parentDTO) {
        parentRepository.save(parentDTO);
    }

}