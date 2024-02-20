package com.settlementproject.services;

import com.settlementproject.entities.SchoolEntity;
import com.settlementproject.exceptions.SchoolNotExistException;
import com.settlementproject.repositories.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SchoolService {
    private final SchoolRepository schoolRepository;


    public SchoolEntity getSchoolById(Long id) throws SchoolNotExistException {
        Optional<SchoolEntity> attendanceDTO = schoolRepository.findSchoolEntityById(id);
        if (attendanceDTO.isEmpty())
            throw new SchoolNotExistException();
        return attendanceDTO.get();
    }

}