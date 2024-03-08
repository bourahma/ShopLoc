package com.mimka.shoplocbe.controllers;

import com.mimka.shoplocbe.dto.benefit.BenefitDTO;
import com.mimka.shoplocbe.facades.BenefitFacade;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/benefit")
@CrossOrigin(origins = "${allowed.origin}")
public class BenefitController {

    private final BenefitFacade benefitFacade;

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
    public BenefitDTO createBenefit (@RequestBody @Valid BenefitDTO benefitDTO) {
        return this.benefitFacade.createBenefit(benefitDTO);
    }
}
