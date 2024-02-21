package com.settlementproject.services;

import com.settlementproject.entities.ChildEntity;
import com.settlementproject.repositories.ChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ChildService {
    private final ChildRepository childRepository;


    public List<ChildEntity> getChildListByParentId(Long parentId) {
        return Objects.requireNonNullElseGet(childRepository.findChildEntitiesByParentId(parentId), ArrayList::new);
    }

    public List<ChildEntity> getChildListBySchoolId(Long schoolId) {
        return Objects.requireNonNullElseGet(childRepository.findChildEntitiesBySchoolId(schoolId), ArrayList::new);
    }

}