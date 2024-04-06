package com.mimka.shoplocbe.dto.fidelity;

import com.mimka.shoplocbe.entities.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BalanceTransactionDTO {

    private LocalDateTime transactionDate;

    private TransactionType type;

    private double amount;

    private Long commerceId;

}
