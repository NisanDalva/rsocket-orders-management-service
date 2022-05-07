package com.RSocketOrdersManagementService;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "orders")
public class OrderEntity {
    
    @Id private String orderId;
    private String userEmail;
    private Date createdTimestamp;
    private Date fulfilledTimestamp;
    private List<Product> products;
    
    public OrderEntity() {
    }

    public OrderEntity(String orderId, String userEmail, Date createdTimestamp, Date fulfilledTimestamp,
            List<Product> products) {
        this.orderId = orderId;
        this.userEmail = userEmail;
        this.createdTimestamp = createdTimestamp;
        this.fulfilledTimestamp = fulfilledTimestamp;
        this.products = products;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Date getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Date createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public Date getFulfilledTimestamp() {
        return fulfilledTimestamp;
    }

    public void setFulfilledTimestamp(Date fulfilledTimestamp) {
        this.fulfilledTimestamp = fulfilledTimestamp;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "OrderEntity [createdTimestamp=" + createdTimestamp + ", fulfilledTimestamp=" + fulfilledTimestamp
                + ", orderId=" + orderId + ", products=" + products + ", userEmail=" + userEmail + "]";
    }
}
