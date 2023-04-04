package com.raytotti.wishlist.support.product.application;

import com.raytotti.wishlist.support.product.domain.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductResponse {

    private String id;
    private String code;
    private String description;
    private String thumbnail;
    private BigDecimal price;
    private String category;
    private String brand;
    private String additionalInfo;

    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getCode(),
                product.getDescription(),
                product.getThumbnail(),
                product.getPrice(),
                product.getCategory(),
                product.getBrand(),
                product.getAdditionalInfo()
        );
    }
}
