package com.dolla.e_commerce.model;

import java.util.List;

public class Product {
    private int productId;
    private String productTitle;
    private String productSubTitle;
    private List<String> productSize;
    private String productDescription;
    private String productPrice;
    private List<String> productImages;

    public Product(int productId, String productTitle, String productSubTitle, List<String> productSize, String productDescription, String productPrice, List<String> productImages) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.productSubTitle = productSubTitle;
        this.productSize = productSize;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productImages = productImages;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductSubTitle() {
        return productSubTitle;
    }

    public void setProductSubTitle(String productSubTitle) {
        this.productSubTitle = productSubTitle;
    }

    public List<String> getProductSize() {
        return productSize;
    }

    public void setProductSize(List<String> productSize) {
        this.productSize = productSize;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public List<String> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<String> productImages) {
        this.productImages = productImages;
    }
}