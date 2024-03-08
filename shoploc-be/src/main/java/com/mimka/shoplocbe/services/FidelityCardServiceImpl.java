package com.mimka.shoplocbe.services;

import com.mimka.shoplocbe.entities.*;
import com.mimka.shoplocbe.exception.InsufficientFundsException;
import com.mimka.shoplocbe.exception.InvalidCreditAmountException;
import com.mimka.shoplocbe.repositories.BalanceTransactionRepository;
import com.mimka.shoplocbe.repositories.CommerceRepository;
import com.mimka.shoplocbe.repositories.FidelityCardRepository;
import com.mimka.shoplocbe.repositories.PointTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FidelityCardServiceImpl implements FidelityCardService {

    private final PointTransactionRepository pointTransactionRepository;
    private final BalanceTransactionRepository balanceTransactionRepository;
    private final CommerceRepository commerceRepository;
    private final FidelityCardRepository fidelityCardRepository;

    private static final double MAXIMUMAMOUNTCREDITALLOWED = 50.00;

    private static final double MINIMUMAMOUNTCREDITALLOWED = 1.0;

    @Autowired
    public FidelityCardServiceImpl(PointTransactionRepository pointTransactionRepository, BalanceTransactionRepository balanceTransactionRepository, CommerceRepository commerceRepository, FidelityCardRepository fidelityCardRepository) {
        this.pointTransactionRepository = pointTransactionRepository;
        this.balanceTransactionRepository = balanceTransactionRepository;
        this.commerceRepository = commerceRepository;
        this.fidelityCardRepository = fidelityCardRepository;
    }

    @Override
    public FidelityCard createFidelityCard() {
        FidelityCard fidelityCard = new FidelityCard();
        fidelityCard.setFidelityCardId(UUID.randomUUID().toString());
        fidelityCard.setBalance(0);
        fidelityCard.setPoints(0);

        return fidelityCard;
    }

    @Override
    public void earnPoints(String fidelityCardId, long commerceId, double amount) {
        Optional<FidelityCard> optionalFidelityCard = this.fidelityCardRepository.findById(fidelityCardId);
        if (optionalFidelityCard.isPresent()) {
            FidelityCard fidelityCard = optionalFidelityCard.get();
            fidelityCard.setPoints(fidelityCard.getPoints() + Math.floor((amount)));
            createPointTransaction(fidelityCard, commerceId, amount, TransactionType.EARNED);
            this.fidelityCardRepository.save(fidelityCard);
        }
    }

    @Override
    public void spendPoints(String fidelityCardId, long commerceId, double amount) throws InsufficientFundsException {
        Optional<FidelityCard> optionalFidelityCard = this.fidelityCardRepository.findById(fidelityCardId);
        if (optionalFidelityCard.isPresent()) {
            FidelityCard fidelityCard = optionalFidelityCard.get();
            if (fidelityCard.getPoints() >= amount) {
                fidelityCard.setPoints(fidelityCard.getPoints() - amount);
                createPointTransaction(fidelityCard, commerceId, amount, TransactionType.SPENT);
                this.fidelityCardRepository.save(fidelityCard);
            } else {
                throw new InsufficientFundsException("Le solde de points sur votre carte fidélité est insuffisant pour effectuer cette transaction.");
            }
        }
    }

    private void createPointTransaction(FidelityCard fidelityCard, long commerceId, double amount, TransactionType type) {
        PointTransaction transaction = new PointTransaction();
        transaction.setCommerce(this.commerceRepository.findByCommerceIdAndDisabled(commerceId, false));
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setType(type);
        transaction.setAmount(amount);

        this.pointTransactionRepository.save(transaction);
        fidelityCard.getPointTransactions().add(transaction);
    }

    @Override
    public Set<PointTransaction> getEarnedPoints(String customerUsername) {
        return getPointsByType(customerUsername, TransactionType.EARNED);
    }

    @Override
    public Set<PointTransaction> getSpentPoints(String customerUsername) {
        return getPointsByType(customerUsername, TransactionType.SPENT);
    }

    private Set<PointTransaction> getPointsByType(String fidelityCardId, TransactionType type) {
        FidelityCard fidelityCard = this.fidelityCardRepository.findById(fidelityCardId).get();

        if (fidelityCard.getPointTransactions() == null) {
            return Collections.emptySet();
        }

        return fidelityCard.getPointTransactions().stream()
                .filter(transaction -> transaction.getType() == type)
                .collect(Collectors.toSet());
    }


    @Override
    public FidelityCard creditFidelityCardBalance(String fidelityCardId, double amount) throws InvalidCreditAmountException {
        if (amount < FidelityCardServiceImpl.MINIMUMAMOUNTCREDITALLOWED || amount > FidelityCardServiceImpl.MAXIMUMAMOUNTCREDITALLOWED) {
            throw new InvalidCreditAmountException("le montant de crédit doit être compris entre 1€ et 50€.");
        }
        FidelityCard fidelityCard = this.fidelityCardRepository.findById(fidelityCardId).get();
        createBalanceTransaction(fidelityCard, 0, amount, TransactionType.CREDIT);

        return this.fidelityCardRepository.save(fidelityCard);
    }

    @Override
    public void debitFidelityCardBalance(String fidelityCardId,long commerceId, double amount) throws InsufficientFundsException {
        Optional<FidelityCard> optionalFidelityCard = this.fidelityCardRepository.findById(fidelityCardId);
        if (optionalFidelityCard.isPresent()) {
            FidelityCard fidelityCard = optionalFidelityCard.get();
            if (fidelityCard.getBalance() >= amount) {
                createBalanceTransaction(fidelityCard, commerceId, amount, TransactionType.DEBIT);
                this.fidelityCardRepository.save(fidelityCard);
            } else {
                throw new InsufficientFundsException("Le solde sur votre carte fidélité est insuffisant pour effectuer cette transaction.");
            }
        }
    }

    private void createBalanceTransaction(FidelityCard fidelityCard, long commerceId, double amount, TransactionType type) {
        double newBalance = type == TransactionType.CREDIT ?
                fidelityCard.getBalance() + amount :
                fidelityCard.getBalance() - amount;
        fidelityCard.setBalance(newBalance);

        BalanceTransaction transaction = new BalanceTransaction();
        transaction.setFidelityCard(fidelityCard);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setType(type);
        transaction.setAmount(amount);

        if (commerceId != 0) {
            Commerce commerce = this.commerceRepository.findByCommerceIdAndDisabled(commerceId, false);
            transaction.setCommerce(commerce);
        }

        this.balanceTransactionRepository.save(transaction);

        fidelityCard.getBalanceTransactions().add(transaction);
    }


    @Override
    public Set<BalanceTransaction> getCreditedTransactions(String customerUsername) {
        return this.getTransactionsByType(customerUsername, TransactionType.CREDIT);
    }

    @Override
    public Set<BalanceTransaction> getDebitedTransactions(String customerUsername) {
        return this.getTransactionsByType(customerUsername, TransactionType.DEBIT);
    }

    private Set<BalanceTransaction> getTransactionsByType(String fidelityCardId, TransactionType type) {
        FidelityCard fidelityCard = this.fidelityCardRepository.findById(fidelityCardId).get();

        if (fidelityCard.getBalanceTransactions() == null) {
            return Collections.emptySet();
        }

        return fidelityCard.getBalanceTransactions().stream()
                .filter(transaction -> transaction.getType() == type)
                .collect(Collectors.toSet());
    }

}
