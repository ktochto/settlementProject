package com.settlementproject.services;

import com.settlementproject.entities.SchoolEntity;
import com.settlementproject.repositories.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SchoolService {
    private final SchoolRepository schoolRepository;


    public SchoolEntity getSchoolById(UUID id) {
        Optional<SchoolEntity> attendanceDTO = schoolRepository.findSchoolEntityById(id);
        return attendanceDTO.orElse(null);
    }

}