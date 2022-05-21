package com.RSocketOrdersManagementService;

public class UserBoundary {

    private String userEmail;

    public UserBoundary() {
    }

    public UserBoundary(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public String toString() {
        return "UserBoundary [userEmail=" + userEmail + "]";
    }
}
