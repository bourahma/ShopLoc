package com.mimka.shoplocbe.dto.product;

import com.mimka.shoplocbe.entities.Product;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromotionDTO {

    private Long promotionId;

    @NotNull(message = "La date de début est requise")
    private LocalDate startDate;

    @NotNull(message = "La date de fin est requise")
    private LocalDate endDate;

    @NotNull(message = "La description de la promotion est requise")
    private String description;

    private Long productId;

    private Long commerceId;

    // Discount promotion fields
    private int discountPercent;

    // Offer promotion fields
    private int requiredItems;

    private int offeredItems;
}
