package com.mimka.shoplocbe.dto.benefits;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BenefitDto {

    private Long benefit_id;

    private String type;

    private String description;
}