package com.system.readycrudop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Table(name = "Product")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String ProductName;

    private String description;

    private BigDecimal price;


}
