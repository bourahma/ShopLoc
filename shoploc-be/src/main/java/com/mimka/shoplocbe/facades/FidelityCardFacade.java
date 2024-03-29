package com.mimka.shoplocbe.facades;

import com.mimka.shoplocbe.dto.fidelity.*;
import com.mimka.shoplocbe.entities.BalanceTransaction;
import com.mimka.shoplocbe.entities.Customer;
import com.mimka.shoplocbe.entities.FidelityCard;
import com.mimka.shoplocbe.entities.PointTransaction;
import com.mimka.shoplocbe.exception.InvalidCreditAmountException;
import com.mimka.shoplocbe.services.CustomerService;
import com.mimka.shoplocbe.services.FidelityCardService;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FidelityCardFacade {

    private final FidelityCardService fidelityCardService;

    private final FidelityCardDotUtil fidelityCardDotUtil;

    private final CustomerService customerService;


    public FidelityCardFacade(FidelityCardService fidelityCardService, FidelityCardDotUtil fidelityCardDotUtil, CustomerService customerService) {
        this.fidelityCardService = fidelityCardService;
        this.fidelityCardDotUtil = fidelityCardDotUtil;
        this.customerService = customerService;
    }

    public FidelityCardDTO getCustomerFidelityCard (Principal principal) {
        Customer customer = this.customerService.getCustomerByUsername(principal.getName());
        FidelityCard fidelityCard = customer.getFidelityCard();

        return this.fidelityCardDotUtil.fidelityCardDTO(fidelityCard);
    }

    public FidelityCardDTO creditFidelityCardBalance (CreditBalanceDTO creditBalanceDTO) throws InvalidCreditAmountException {
        FidelityCard fidelityCard = this.fidelityCardService.creditFidelityCardBalance(creditBalanceDTO.getFidelityCardId(), creditBalanceDTO.getAmount());

        return this.fidelityCardDotUtil.fidelityCardDTO(fidelityCard);
    }

    public Set<BalanceTransactionDTO> getCreditedTransactions(String fidelityCardId) {
        Set<BalanceTransaction> transactions = this.fidelityCardService.getCreditedTransactions(fidelityCardId);

        return transactions.stream()
                .map(this.fidelityCardDotUtil::toBalanceTransactionDTO)
                .collect(Collectors.toSet());
    }

    public Set<BalanceTransactionDTO> getDebitedTransactions(String fidelityCardId) {
        Set<BalanceTransaction> transactions = this.fidelityCardService.getDebitedTransactions(fidelityCardId);
        return transactions.stream()
                .map(this.fidelityCardDotUtil::toBalanceTransactionDTO)
                .collect(Collectors.toSet());
    }

    public Set<PointTransactionDTO> getEarnedPointsTransactions(String fidelityCardId) {
        Set<PointTransaction> transactions = this.fidelityCardService.getEarnedPoints(fidelityCardId);
        return transactions.stream()
                .map(this.fidelityCardDotUtil::toPointTransactionDTO)
                .collect(Collectors.toSet());
    }

    public Set<PointTransactionDTO> getSpentPointsTransactions(String fidelityCardId) {
        Set<PointTransaction> transactions = this.fidelityCardService.getSpentPoints(fidelityCardId);
        return transactions.stream()
                .map(this.fidelityCardDotUtil::toPointTransactionDTO)
                .collect(Collectors.toSet());
    }

}
