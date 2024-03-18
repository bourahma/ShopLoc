package com.mimka.shoplocbe.controllers;

import com.mimka.shoplocbe.dto.fidelity.BalanceTransactionDTO;
import com.mimka.shoplocbe.dto.fidelity.CreditBalanceDTO;
import com.mimka.shoplocbe.dto.fidelity.FidelityCardDTO;
import com.mimka.shoplocbe.dto.fidelity.PointTransactionDTO;
import com.mimka.shoplocbe.exception.InvalidCreditAmountException;
import com.mimka.shoplocbe.facades.FidelityCardFacade;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@RestController
@CrossOrigin(origins = "${allowed.origin}")
@PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
@RequestMapping("/fidelity-card")
public class FidelityCardController {
    private final FidelityCardFacade fidelityCardFacade;

    @Autowired
    public FidelityCardController(FidelityCardFacade fidelityCardFacade) {
        this.fidelityCardFacade = fidelityCardFacade;
    }

    @GetMapping(value = "/")
    public FidelityCardDTO getCustomerFidelityCard (Principal principal) {
        return this.fidelityCardFacade.getCustomerFidelityCard(principal);
    }

    @PostMapping(value = "/credit")
    public FidelityCardDTO creditFidelityCardBalance (@RequestBody @Valid CreditBalanceDTO creditBalanceDTO) throws InvalidCreditAmountException {
        return this.fidelityCardFacade.creditFidelityCardBalance(creditBalanceDTO);
    }

    @GetMapping(value = "/history-balance/credits/{fidelityCardId}")
    public Set<BalanceTransactionDTO> getCreditedTransactions(@PathVariable String fidelityCardId) {
        return this.fidelityCardFacade.getCreditedTransactions(fidelityCardId);
    }

    @GetMapping(value = "/history-balance/debits/{fidelityCardId}")
    public Set<BalanceTransactionDTO> getDebitedTransactions (@PathVariable String fidelityCardId) {
        return this.fidelityCardFacade.getDebitedTransactions(fidelityCardId);
    }

    @GetMapping(value = "/history-points/earned/{fidelityCardId}")
    public Set<PointTransactionDTO> getEarnedPointsTransactions(@PathVariable String fidelityCardId) {
        return this.fidelityCardFacade.getEarnedPointsTransactions(fidelityCardId);
    }

    @GetMapping(value = "/history-points/spent/{fidelityCardId}")
    public Set<PointTransactionDTO> getSpentPointsTransactions(@PathVariable String fidelityCardId) {
        return this.fidelityCardFacade.getSpentPointsTransactions(fidelityCardId);
    }
}