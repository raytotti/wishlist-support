package com.raytotti.wishlist.support.product.domain;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public final class ProductBuilder {

    private String code;
    private String description;
    private String thumbnail;
    private BigDecimal price;
    private String category;
    private String brand;
    private String additionalInfo;

    public ProductBuilder code(String code) {
        this.code = code;
        return this;
    }

    public ProductBuilder description(String description) {
        this.description = description;
        return this;
    }

    public ProductBuilder thumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public ProductBuilder price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public ProductBuilder category(String category) {
        this.category = category;
        return this;
    }

    public ProductBuilder brand(String brand) {
        this.brand = brand;
        return this;
    }

    public ProductBuilder additionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
        return this;
    }

    public Product build() {
        return new Product(this);
    }

}
