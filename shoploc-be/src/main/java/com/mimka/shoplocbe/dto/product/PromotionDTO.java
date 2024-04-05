package com.mimka.shoplocbe.dto.product;

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

    private Long commerceId;

    private String label;

    private boolean sent;

    // Discount promotion fields
    private Integer discountPercent;

    private String promotionType;

    // Offer promotion fields
    private Integer requiredItems;

    private Integer offeredItems;
}
