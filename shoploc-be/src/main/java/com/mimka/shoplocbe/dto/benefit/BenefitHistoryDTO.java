package com.mimka.shoplocbe.dto.benefit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BenefitHistoryDTO {

    private Long benefitHistoryId;

    private String acquisitionDate;

    private Timestamp acquisitionTime;

    private String qrCode;

    private long customerId;

    private BenefitDTO benefitDTO;

}
