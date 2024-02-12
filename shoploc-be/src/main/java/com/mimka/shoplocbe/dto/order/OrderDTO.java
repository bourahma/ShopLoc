package com.mimka.shoplocbe.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private long commerceId;

    private String commerceName;

    private LocalDate orderDate;

    private String status;

    private Set<OrderProductDTO> products;
}
