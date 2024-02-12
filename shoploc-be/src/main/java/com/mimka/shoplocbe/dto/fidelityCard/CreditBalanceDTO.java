package com.mimka.shoplocbe.dto.fidelityCard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditBalanceDTO {

    private String fidelityCardId;

    private double amount;
}
