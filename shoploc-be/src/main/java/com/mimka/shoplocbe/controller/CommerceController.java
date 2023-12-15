package com.mimka.shoplocbe.controller;

import com.mimka.shoplocbe.dto.commerce.CommerceDTO;
import com.mimka.shoplocbe.service.CommerceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/commerce")
public class CommerceController {

    private CommerceServiceImpl commerceServiceImpl;

    @Autowired
    public CommerceController(CommerceServiceImpl commerceServiceImpl) {
        this.commerceServiceImpl = commerceServiceImpl;
    }

    @GetMapping("/")
    @ResponseStatus(value = HttpStatus.OK)
    public List<CommerceDTO> commerces () {
        return this.commerceServiceImpl.getCommerces();
    }
}
