package com.mimka.shoplocbe.service;

import com.mimka.shoplocbe.dto.commerce.CommerceDTO;
import com.mimka.shoplocbe.dto.commerce.CommerceDTOUtil;
import com.mimka.shoplocbe.repository.CommerceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommerceServiceImpl {

    private CommerceRepository commerceRepository;

    private CommerceDTOUtil commerceDTOUtil;

    @Autowired
    public CommerceServiceImpl(CommerceRepository commerceRepository, CommerceDTOUtil commerceDTOUtil) {
        this.commerceRepository = commerceRepository;
        this.commerceDTOUtil = commerceDTOUtil;
    }

    public List<CommerceDTO> getCommerces () {
        return this.commerceRepository.findAll()
                .stream()
                .map(commerceDTOUtil::toCommerceDTO)
                .collect(Collectors.toList());
    }
}