package com.settlementproject.services;

import com.settlementproject.entities.SchoolEntity;
import com.settlementproject.exceptions.SchoolNotExistException;
import com.settlementproject.repositories.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SchoolService {
    private final SchoolRepository schoolRepository;


    public SchoolEntity getSchoolById(UUID id) throws SchoolNotExistException {
        Optional<SchoolEntity> attendanceDTO = schoolRepository.findSchoolEntityById(id);
        if (attendanceDTO.isEmpty())
            throw new SchoolNotExistException();
        return attendanceDTO.get();
    }

    public SchoolEntity create() {
        SchoolEntity school = new SchoolEntity();
        school.setName("Oxford");
        school.setHourPrice(10.0);
        return schoolRepository.save(school);
    }

}