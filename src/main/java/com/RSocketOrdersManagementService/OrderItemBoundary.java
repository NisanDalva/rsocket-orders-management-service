package com.RSocketOrdersManagementService;

public class OrderItemBoundary {
    
    private String orderId;
    private String productId;
    private Integer quantity;
    
    public OrderItemBoundary() {
    }

    public OrderItemBoundary(String orderId, String productId, Integer quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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
        return "OrderItemBoundary [orderId=" + orderId + ", productId=" + productId + ", quantity=" + quantity + "]";
    }

}
