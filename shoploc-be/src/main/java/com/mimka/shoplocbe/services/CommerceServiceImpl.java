package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.commerce.CommerceDTO;
import com.mimka.shoplocbe.dto.commerce.CommerceDTOUtil;
import com.mimka.shoplocbe.entities.Address;
import com.mimka.shoplocbe.entities.Commerce;
import com.mimka.shoplocbe.entities.Product;
import com.mimka.shoplocbe.exception.CommerceNotFoundException;
import com.mimka.shoplocbe.repositories.CommerceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommerceServiceImpl implements CommerceService {

    private final CommerceRepository commerceRepository;

    private final CommerceDTOUtil commerceDTOUtil;

    @Autowired
    public CommerceServiceImpl(CommerceRepository commerceRepository, CommerceDTOUtil commerceDTOUtil) {
        this.commerceRepository = commerceRepository;
        this.commerceDTOUtil = commerceDTOUtil;
    }

    @Override
    public List<Commerce> getCommerces () {
        return this.commerceRepository.findAll();
    }

    @Override
    public Commerce getCommerce (Long id) throws CommerceNotFoundException {
        Commerce commerce = this.commerceRepository.findById(id).orElseThrow(() -> new CommerceNotFoundException("Commerce not found for ID : " + id));

        return this.commerceRepository.findByCommerceId(id);
    }

    @Override
    public Commerce createCommerce(CommerceDTO commerceDTO) {
        Commerce commerce = this.commerceDTOUtil.toCommerce(commerceDTO);

        return this.commerceRepository.save(commerce);
    }

    @Override
    public Commerce addProduct(Product product, Long commerceId) throws CommerceNotFoundException {
        Commerce commerce = this.getCommerce(commerceId);
        commerce.getProducts().add(product);

        this.commerceRepository.save(commerce);

        return commerce;
    }

    @Override
    public void deleteCommerce(Long commerceId) {
        this.commerceRepository.deleteById(commerceId);
    }

    @Override
    public Commerce updateCommerce(CommerceDTO commerceDTO) throws CommerceNotFoundException {
        Commerce commerce = this.getCommerce(commerceDTO.getCommerceId());

        Commerce commerce1 = this.commerceDTOUtil.toCommerce(commerceDTO);

        commerce.setCommerceName(commerceDTO.getCommerceName());
        commerce.setOpeningHour(commerceDTO.getOpeningHour());
        commerce.setClosingHour(commerceDTO.getClosingHour());
        commerce.setImageUrl(commerceDTO.getImageUrl());

        Address address = commerce.getAddress();

        address.setCity(commerceDTO.getAddressDTO().getCity());
        address.setStreet(commerceDTO.getAddressDTO().getStreet());
        address.setPostalCode(commerceDTO.getAddressDTO().getPostalCode());

        commerce.setAddress(address);

        this.commerceRepository.save(commerce1);

        return commerce;
    }

}
