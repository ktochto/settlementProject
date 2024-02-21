package com.settlementproject.services;

import com.settlementproject.dtos.SettlementChildDTO;
import com.settlementproject.dtos.SettlementParentDTO;
import com.settlementproject.entities.ChildEntity;
import com.settlementproject.entities.ParentEntity;
import com.settlementproject.exceptions.ParentNotExistException;
import com.settlementproject.exceptions.SchoolNotExistException;
import com.settlementproject.exceptions.WrongDateInpuException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SettlementParentService {
    private final SettlementChildService settlementChildService;
    private final ParentService parentService;
    private final ChildService childService;


    public SettlementParentDTO createSettlementForParent(Long inputParentId, String inputDate) throws ParentNotExistException, SchoolNotExistException, WrongDateInpuException {
        ParentEntity parent = parentService.getParentById(inputParentId);
        SettlementParentDTO settlementParent = new SettlementParentDTO();
        setParentData(parent, settlementParent);
        calculateChildrenSettlementsAndSet(inputParentId, inputDate, settlementParent);
        calculateTotalHoursAndPriceAndSet(settlementParent);
        return settlementParent;
    }

    private void setParentData(ParentEntity parent, SettlementParentDTO settlementParent) {
        settlementParent.setFirstName(parent.getFirstName());
        settlementParent.setLastName(parent.getLastName());
    }

    private void calculateChildrenSettlementsAndSet(Long parentId, String inputDate, SettlementParentDTO settlementParent) throws SchoolNotExistException, WrongDateInpuException {
        List<ChildEntity> parentsChildren = childService.getChildListByParentId(parentId);
        List<SettlementChildDTO> settlementsForChildren = settlementChildService.createSettlementsForChildren(inputDate, parentsChildren);
        settlementParent.setChildren(settlementsForChildren);
    }

    private void calculateTotalHoursAndPriceAndSet(SettlementParentDTO settlementParent) {
        double totalPrice = settlementParent.getChildren().stream()
                .mapToDouble(SettlementChildDTO::getTotalPrice)
                .sum();

        int payedHours = settlementParent.getChildren().stream()
                .mapToInt(SettlementChildDTO::getPayedHours)
                .sum();

        settlementParent.setTotalPrice(totalPrice);
        settlementParent.setPayedHours(payedHours);
    }

}