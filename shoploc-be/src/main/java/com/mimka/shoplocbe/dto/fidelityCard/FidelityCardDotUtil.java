package com.mimka.shoplocbe.dto.fidelityCard;

import com.mimka.shoplocbe.entities.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FidelityCardDotUtil {
    private final ModelMapper modelMapper;

    @Autowired
    public FidelityCardDotUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        this.modelMapper.addMappings(new PropertyMap<FidelityCard, FidelityCardDTO>() {
            @Override
            protected void configure() {
                map().setFidelityCardId(source.getFidelityCardId());
                map().setPoints(source.getPoints());
                map().setBalance(source.getBalance());
            }
        });
    }

    public FidelityCardDTO fidelityCardDTO(FidelityCard fidelityCard) {
        return modelMapper.map(fidelityCard, FidelityCardDTO.class);
    }

        /*
        private final Converter<Set<PortfolioTransaction>, Set<PortfolioTransactionDTO>> portfolioToPortfolioTransactionDTOConverter = context -> {
        Set<PortfolioTransaction> sourceTransactions = context.getSource();
        return sourceTransactions.stream()
                .map(transaction -> new PortfolioTransactionDTO(
                        transaction.getPortfolioTransactionId(),
                        transaction.getTransactionDate(),
                        transaction.getType(),
                        transaction.getAmount(),
                        Optional.ofNullable(transaction.getCommerce())
                                .map(Commerce::getCommerceId)
                                .orElse(null)))
                .collect(Collectors.toSet());
    };*/

    public BalanceTransactionDTO toBalanceTransactionDTO(BalanceTransaction transaction) {
        return new BalanceTransactionDTO (
                transaction.getTransactionDate(),
                transaction.getType(),
                transaction.getAmount(),
                Optional.ofNullable(transaction.getCommerce())
                        .map(Commerce::getCommerceId)
                        .orElse(null));
    }

    public PointTransactionDTO toPointTransactionDTO (PointTransaction pointTransaction) {
        return new PointTransactionDTO (
                pointTransaction.getTransactionDate(),
                pointTransaction.getType(),
                pointTransaction.getAmount(),
                Optional.ofNullable(pointTransaction.getCommerce())
                        .map(Commerce::getCommerceId)
                        .orElse(null));
    }
}