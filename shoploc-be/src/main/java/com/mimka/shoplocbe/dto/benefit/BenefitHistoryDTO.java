package com.mimka.shoplocbe.dto.benefit;

import com.mimka.shoplocbe.entities.Benefit;
import com.mimka.shoplocbe.entities.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BenefitHistoryDTO {

    private Long id;

    private String dateAcquisition;

    private List<Customer> customerId;

    private List<Benefit> benefitId;
}
