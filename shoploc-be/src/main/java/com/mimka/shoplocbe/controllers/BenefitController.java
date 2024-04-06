package com.mimka.shoplocbe.controllers;

import com.mimka.shoplocbe.dto.benefit.BenefitDTO;
import com.mimka.shoplocbe.dto.benefit.BenefitHistoryDTO;
import com.mimka.shoplocbe.exception.BenefitException;
import com.mimka.shoplocbe.facades.BenefitFacade;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/benefit")
@CrossOrigin(origins = "${allowed.origin}")
public class BenefitController {

    private final BenefitFacade benefitFacade;

    @Autowired
    public BenefitController(BenefitFacade benefitFacade) {
        this.benefitFacade = benefitFacade;
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('SCOPE_CUSTOMER')")
    public List<BenefitDTO> benefits ( ) {
        return this.benefitFacade.getBenefits();
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMINISTRATOR')")
    @ResponseStatus(HttpStatus.CREATED)
    public BenefitDTO createBenefit (@RequestBody @Valid BenefitDTO benefitDTO) {
        return this.benefitFacade.createBenefit(benefitDTO);
    }

    @GetMapping("/{benefitId}")
    @PreAuthorize("hasAnyAuthority('SCOPE_CUSTOMER')")
    public BenefitHistoryDTO availBenefit ( Principal principal, @PathVariable long benefitId) throws BenefitException {
        return this.benefitFacade.availBenefit(principal.getName(), benefitId);
    }

    @GetMapping("/history")
    @PreAuthorize("hasAnyAuthority('SCOPE_CUSTOMER')")
    public List<BenefitHistoryDTO> customerBenefitHistory (Principal principal) {
        return this.benefitFacade.customerBenefitHistory(principal.getName());
    }
}
