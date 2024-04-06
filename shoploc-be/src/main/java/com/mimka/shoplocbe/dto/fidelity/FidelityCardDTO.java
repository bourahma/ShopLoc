package com.mimka.shoplocbe.dto.fidelity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FidelityCardDTO {

    private String fidelityCardId;

    private double balance;

    private double points;
}
