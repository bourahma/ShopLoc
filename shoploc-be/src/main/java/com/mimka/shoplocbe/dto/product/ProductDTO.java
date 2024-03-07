package com.mimka.shoplocbe.dto.product;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Long productId;

    @NotBlank(message = "Le nom du produit est requis")
    private String productName;

    @NotBlank(message = "La description est requise")
    @Size(max = 255, message = "La description ne peut pas dépasser {max} caractères")
    private String description;

    @Positive(message = "Le prix doit être un nombre positif")
    private double price;

    @Min(value = 0, message = "La quantité doit être un nombre entier positif")
    private int quantity;

    @Positive(message = "Le prix en points de fidélité doit être un nombre positif ou nul.")
    private double rewardPointsPrice;

    @NotNull(message = "Le statut de cadeau est requis")
    private boolean isGift;

    private String productCategoryLabel;

    private MultipartFile multipartFile;

    private Long productCategoryId;

    private Long commerceId;

    private Long discountId;


    private PromotionDTO promotion;

}
