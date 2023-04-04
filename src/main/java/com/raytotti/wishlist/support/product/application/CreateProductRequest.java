package com.raytotti.wishlist.support.product.application;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateProductRequest {

    @NotBlank(message = "{Product.code.NotBlank}")
    private String code;

    @NotBlank(message = "{Product.description.NotBlank}")
    private String description;

    @NotBlank(message = "{Product.thumbnail.NotBlank}")
    private String thumbnail;

    @NotNull(message = "{Product.price.NotNull}")
    private BigDecimal price;

    @NotBlank(message = "{Product.category.NotBlank}")
    private String category;

    @NotBlank(message = "{Product.brand.NotBlank}")
    private String brand;

    @NotBlank(message = "{Product.additionalInfo.NotBlank}")
    private String additionalInfo;
}
