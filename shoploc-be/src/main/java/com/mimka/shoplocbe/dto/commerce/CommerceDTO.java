package com.mimka.shoplocbe.dto.commerce;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.time.LocalTime;
@Getter
@Setter
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class CommerceDTO {

    private Long commerceId;

    private String commerceName;

    private LocalTime openingHour;

    private LocalTime closingHour;
}
