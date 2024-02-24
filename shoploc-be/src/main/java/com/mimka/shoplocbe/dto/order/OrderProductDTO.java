package com.mimka.shoplocbe.dto.order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductDTO {

    @NotNull
    @Positive(message = "L'id du produit est requis et doit être un nombre psotif.")
    private long productId;

    private String productName;

    private double price;

    private double rewardPointsPrice;

    @NotNull
    @Positive(message = "La quantité est requise et doit être un nombre positif.")
    private int quantity;
}
