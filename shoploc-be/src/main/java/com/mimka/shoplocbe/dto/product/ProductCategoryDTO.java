package com.mimka.shoplocbe.dto.product;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategoryDTO {

    private Long productCategoryId;

    private Long commerceId;

    @NotBlank(message = "Le nom de la catégorie du produit est requise")
    private String label;

    @NotBlank(message = "Le description de la catégorie du produit est requise")
    private String description;
}
