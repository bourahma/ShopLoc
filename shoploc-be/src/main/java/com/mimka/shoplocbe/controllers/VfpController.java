package com.mimka.shoplocbe.controllers;

import com.mimka.shoplocbe.dto.vfp.VfpDTO;
import com.mimka.shoplocbe.facades.CustomerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/vfp")
@CrossOrigin(origins = "${allowed.origin}")
public class VfpController {

    private final CustomerFacade customerFacade;

    @Autowired
    public VfpController(CustomerFacade customerFacade) {
        this.customerFacade = customerFacade;
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('SCOPE_CUSTOMER')")
    public VfpDTO isCustomerVfpMember (Principal principal) {
        return this.customerFacade.customerVfpMembership(principal.getName());
    }

    @GetMapping("/history")
    @PreAuthorize("hasAnyAuthority('SCOPE_CUSTOMER')")
    public List<VfpDTO> getVfpMembershipHistory (Principal principal) {
        return this.customerFacade.customerVfpMembershipHistory(principal.getName());
    }
}
