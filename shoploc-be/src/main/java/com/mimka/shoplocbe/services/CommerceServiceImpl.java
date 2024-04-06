package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.dto.commerce.CommerceDTO;
import com.mimka.shoplocbe.entities.Commerce;
import com.mimka.shoplocbe.entities.CommerceType;
import com.mimka.shoplocbe.entities.Product;
import com.mimka.shoplocbe.entities.ProductCategory;
import com.mimka.shoplocbe.exception.CommerceNotFoundException;
import com.mimka.shoplocbe.repositories.CommerceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CommerceServiceImpl implements CommerceService {

    private final CommerceRepository commerceRepository;

    @Autowired
    public CommerceServiceImpl(CommerceRepository commerceRepository) {
        this.commerceRepository = commerceRepository;
    }

    @Override
    public List<Commerce> getCommerces () {
        return this.commerceRepository.findByDisabled(false);
    }

    @Override
    public Commerce getCommerce (Long id) throws CommerceNotFoundException {
        Commerce commerce = this.commerceRepository.findByCommerceIdAndDisabled(id, false);
        if (commerce == null) {
            throw new CommerceNotFoundException("Commerce not found for ID : " + id);
        }

        return commerce;
    }

    @Override
    public List<Commerce> getCommercesByType(CommerceType commerceType) throws CommerceNotFoundException {
        List<Commerce> commerceList = this.commerceRepository.findByCommerceType(commerceType);
        if (commerceList == null) {
            throw new CommerceNotFoundException("Aucun commerce n'est trouvé pour le type du commerce.");
        }
        return commerceList;
    }

    @Override
    public Commerce saveCommerce(Commerce commerce) {
        return this.commerceRepository.save(commerce);
    }

    @Override
    public Commerce addProduct(Product product, Long commerceId) throws CommerceNotFoundException {
        Commerce commerce = this.getCommerce(commerceId);
        product.setCommerce(commerce);
        commerce.getProducts().add(product);

        this.commerceRepository.save(commerce);

        return commerce;
    }

    @Override
    public void disableCommerce(Long commerceId) {
        Commerce commerce = this.commerceRepository.findByCommerceIdAndDisabled(commerceId, false);
        commerce.setDisabled(true);
        this.commerceRepository.save(commerce);
    }

    @Override
    public Commerce updateCommerce(CommerceDTO commerceDTO) throws CommerceNotFoundException {
        Commerce commerce = this.getCommerce(commerceDTO.getCommerceId());

        commerce.setCommerceName(commerceDTO.getCommerceName());
        commerce.setOpeningHour(commerceDTO.getOpeningHour());
        commerce.setClosingHour(commerceDTO.getClosingHour());
        commerce.setImageUrl(commerceDTO.getImageUrl());

        return commerce;
    }

    @Override
    public Set<ProductCategory> getCommerceProductCategories (Long commerceId) throws CommerceNotFoundException {
        return this.getCommerce(commerceId).getProductCategories();
    }

}
