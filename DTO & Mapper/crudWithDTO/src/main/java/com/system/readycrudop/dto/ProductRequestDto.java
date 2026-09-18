package com.system.readycrudop.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductRequestDto {

    @NotBlank(message = "Product name must be given")
    @Size(min = 2, max = 100, message = "Product name must be given")
    private String  name;

    @Size(max = 1000, message = "Description must be of 1000 characters")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Digits(integer = 8, fraction = 2, message = "Price format must be up to 8 integer digits and 2 decimals")
    private BigDecimal price;

}
