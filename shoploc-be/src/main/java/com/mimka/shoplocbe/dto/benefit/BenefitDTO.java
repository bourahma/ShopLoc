package com.mimka.shoplocbe.dto.benefit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BenefitDTO {

    private Long benefitId;

    private Boolean benefitAvailable;

    private String imageUrl;

    private String description;
}