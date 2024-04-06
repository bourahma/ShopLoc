package com.mimka.shoplocbe.dto.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long orderId;

    @NotNull
    @Positive(message = "L'id du commerce est requis et doit être un nombre positif.")
    private long commerceId;

    private String commerceName;

    private LocalDate orderDate;

    private String status;

    @Valid
    @NotNull(message = "Un produit est requis pour effectuer une commande.")
    private Set<OrderProductDTO> products;
}
