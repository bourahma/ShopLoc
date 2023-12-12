package com.mimka.shoplocbe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CommerceProductId implements Serializable {

    @Column(name = "id_commerce")
    private Long commerceId;

    @Column(name = "id_product")
    private Long productId;

}
