package com.mimka.shoplocbe.dto.product;

import com.mimka.shoplocbe.dto.product.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GiftHistoryDTO {

    private Long giftHistoryId;

    private LocalDate purchaseDate;

    private ProductDTO productDTO;
}
