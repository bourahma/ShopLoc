package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.commerce.CommerceDTOUtil;
import com.mimka.shoplocbe.dto.commerce.CommerceTypeDTO;
import com.mimka.shoplocbe.entities.CommerceType;
import com.mimka.shoplocbe.exception.CommerceTypeNotFoundException;
import com.mimka.shoplocbe.repositories.CommerceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommerceTypeServiceImpl implements CommerceTypeService {

    private final CommerceTypeRepository commerceTypeRepository;

    private  final CommerceDTOUtil commerceDTOUtil;

    @Autowired
    public CommerceTypeServiceImpl(CommerceTypeRepository commerceTypeRepository, CommerceDTOUtil commerceDTOUtil) {
        this.commerceTypeRepository = commerceTypeRepository;
        this.commerceDTOUtil = commerceDTOUtil;
    }

    @Override
    public List<CommerceType> getCommerceTypes() {
        return this.commerceTypeRepository.findAll();
    }

    @Override
    public CommerceType getCommerceTypeById(Long commerceTypeId) throws CommerceTypeNotFoundException {
        return this.commerceTypeRepository.findById(commerceTypeId).orElseThrow(() -> new CommerceTypeNotFoundException("Aucun type de commerce ne correspond à l'id : " + commerceTypeId));
    }

    @Override
    public CommerceType createCommerceType(CommerceTypeDTO commerceTypeDTO) {
        CommerceType commerceType = this.commerceDTOUtil.toCommerceType(commerceTypeDTO);

        return this.commerceTypeRepository.save(commerceType);
    }
}
