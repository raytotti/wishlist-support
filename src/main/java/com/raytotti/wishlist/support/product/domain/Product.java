package com.raytotti.wishlist.support.product.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Document
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Product {

    @Id
    private String id;
    private String code;
    private String description;
    private String thumbnail;
    private BigDecimal price;
    private String category;
    private String brand;
    private String additionalInfo;

    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    Product(ProductBuilder builder) {
        this.id = null;
        this.code = Objects.requireNonNull(builder.getCode());
        this.description = Objects.requireNonNull(builder.getDescription());
        this.thumbnail = Objects.requireNonNull(builder.getThumbnail());
        this.price = Objects.requireNonNull(builder.getPrice());
        this.category = Objects.requireNonNull(builder.getCategory());
        this.brand = Objects.requireNonNull(builder.getBrand());
        this.additionalInfo = Objects.requireNonNull(builder.getAdditionalInfo());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(code, product.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
