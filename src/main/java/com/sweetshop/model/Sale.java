package com.sweetshop.model;

public class Sale {
    private int saleId;
    private int userId;
    private int customerId;
    private String saleDate;
    private double totalAmount;

    public Sale() {}

    public Sale(int saleId, int userId, int customerId, String saleDate, double totalAmount) {
        this.saleId = saleId;
        this.userId = userId;
        this.customerId = customerId;
        this.saleDate = saleDate;
        this.totalAmount = totalAmount;
    }

    // Getters and Setters
    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    // ✅ Backward compatibility (for older references)
    public String getDate() {
        return saleDate;
    }

    public void setDate(String date) {
        this.saleDate = date;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "saleId=" + saleId +
                ", userId=" + userId +
                ", customerId=" + customerId +
                ", saleDate='" + saleDate + '\'' +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
