package com.mimka.shoplocbe.dto.commerce;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommerceTypeDTO {

    private Long commerceTypeId;

    private String label;

    private String description;
}
