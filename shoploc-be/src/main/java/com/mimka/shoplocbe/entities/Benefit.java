package com.mimka.shoplocbe.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Benefit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "benefit_sequence")
    @SequenceGenerator(name = "benefit_sequence", sequenceName = "benefit_seq", allocationSize = 1, initialValue = 1)
    private Long benefit_id;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "description", nullable = false)
    private String description;
}