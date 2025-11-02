package com.sweetshop.mockmodel;

public class Sale {
    private int saleId;
    private int customerId;
    private double totalAmount;
    private String saleDate;

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public void setSaleDate(String saleDate) { this.saleDate = saleDate; }
}
