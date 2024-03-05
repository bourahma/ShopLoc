package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.entities.*;
import com.mimka.shoplocbe.entities.BalanceTransaction;
import com.mimka.shoplocbe.exception.InsufficientFundsException;
import com.mimka.shoplocbe.exception.InvalidCreditAmountException;

import java.util.Set;

public interface FidelityCardService {

    FidelityCard createFidelityCard ();

    void earnPoints(String fidelityCardId, long commerceId, double amount);


    void spendPoints(String fidelityCardId, long commerceId, double amount) throws InsufficientFundsException;


    Set<PointTransaction> getEarnedPoints (String fidelityCardId);


    Set<PointTransaction> getSpentPoints (String fidelityCardId);


    FidelityCard creditFidelityCardBalance (String fidelityCardId, double amount) throws InvalidCreditAmountException;


    void debitFidelityCardBalance (String fidelityCardId, long commerceId, double amount) throws InsufficientFundsException;


    Set<BalanceTransaction> getCreditedTransactions (String fidelityCardId);


    Set<BalanceTransaction> getDebitedTransactions (String fidelityCardId);
}
