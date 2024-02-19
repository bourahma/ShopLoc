package com.mimka.shoplocbe.dto.product;

import com.mimka.shoplocbe.entities.Product;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

public class ViewedProductDTO {

    private Product id;
    private int viewCount;
}
