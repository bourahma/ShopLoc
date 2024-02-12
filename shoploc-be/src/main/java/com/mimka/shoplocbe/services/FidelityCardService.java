package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.entities.*;
import com.mimka.shoplocbe.entities.BalanceTransaction;

import java.util.Set;

public interface FidelityCardService {

    FidelityCard createFidelityCard ();

    FidelityCard getFidelityCard (Customer customer);

    void earnPoints(String fidelityCardId, long commerceId, double amount);


    void spendPoints(String fidelityCardId, long commerceId, double amount);


    Set<PointTransaction> getEarnedPoints (String fidelityCardId);


    Set<PointTransaction> getSpentPoints (String fidelityCardId);


    FidelityCard creditFidelityCardBalance (String fidelityCardId, double amount);


    void debitFidelityCardBalance (String fidelityCardId, long commerceId, double amount);


    Set<BalanceTransaction> getCreditedTransactions (String fidelityCardId);


    Set<BalanceTransaction> getDebitedTransactions (String fidelityCardId);
}
