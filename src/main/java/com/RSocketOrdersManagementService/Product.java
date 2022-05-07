package com.RSocketOrdersManagementService;

public class Product {
    
    private String productId;
    private Integer quantity;
    
    public Product() {
    }

    public Product(String productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product [productId=" + productId + ", quantity=" + quantity + "]";
    }
}
