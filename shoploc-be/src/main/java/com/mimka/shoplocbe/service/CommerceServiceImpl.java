package com.mimka.shoplocbe.service;

import com.mimka.shoplocbe.dto.commerce.CommerceDTO;
import com.mimka.shoplocbe.dto.commerce.CommerceDTOUtil;
import com.mimka.shoplocbe.entity.Commerce;
import com.mimka.shoplocbe.repository.CommerceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommerceServiceImpl implements CommerceService {

    private CommerceRepository commerceRepository;

    private CommerceDTOUtil commerceDTOUtil;

    @Autowired
    public CommerceServiceImpl(CommerceRepository commerceRepository, CommerceDTOUtil commerceDTOUtil) {
        this.commerceRepository = commerceRepository;
        this.commerceDTOUtil = commerceDTOUtil;
    }

    @Override
    public List<CommerceDTO> getCommerces () {
        return this.commerceRepository.findAll()
                .stream()
                .map(commerceDTOUtil::toCommerceDTO).toList();
    }

    public Commerce getCommerce (Long id) {
        return this.commerceRepository.findByCommerceId(id);
    }
}
