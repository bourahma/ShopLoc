package com.mimka.shoplocbe.dto.fidelity;

import com.mimka.shoplocbe.entities.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class PointTransactionDTO {

    private LocalDateTime transactionDate;

    private TransactionType type;

    private double amount;

    private Long commerceId;
}
