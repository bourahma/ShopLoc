package com.mimka.shoplocbe.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Administrator")
public class Administrator extends User {

}