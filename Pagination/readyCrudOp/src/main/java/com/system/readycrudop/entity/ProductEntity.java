package com.system.readycrudop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Table(name = "Product")
@Getter
@Setter
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Product name must be given")
    @Size(min = 2, max = 100, message = "Product name must be given")
    private String productName;

    @Size(max = 1000, message = "Description must be of 1000 characters")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Digits(integer = 8, fraction = 2, message = "Price format must be up to 8 integer digits and 2 decimals")
    private BigDecimal price;

}
