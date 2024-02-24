package com.mimka.shoplocbe.controllers;

import com.mimka.shoplocbe.dto.fidelityCard.BalanceTransactionDTO;
import com.mimka.shoplocbe.dto.fidelityCard.CreditBalanceDTO;
import com.mimka.shoplocbe.dto.fidelityCard.FidelityCardDTO;
import com.mimka.shoplocbe.dto.fidelityCard.PointTransactionDTO;
import com.mimka.shoplocbe.facades.FidelityCardFacade;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@CrossOrigin(origins = "${allowed.origin}")
@RestController
@RequestMapping("/fidelity-card")
public class FidelityCardController {
    private final FidelityCardFacade fidelityCardFacade;

    public FidelityCardController(FidelityCardFacade fidelityCardFacade) {
        this.fidelityCardFacade = fidelityCardFacade;
    }

    @GetMapping(value = "/")
    public FidelityCardDTO getCustomerFidelityCard (Principal principal) {
        return this.fidelityCardFacade.getCustomerFidelityCard(principal);
    }

    @PostMapping(value = "/credit")
    public FidelityCardDTO creditFidelityCardBalance (@RequestBody @Valid CreditBalanceDTO creditBalanceDTO) {
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