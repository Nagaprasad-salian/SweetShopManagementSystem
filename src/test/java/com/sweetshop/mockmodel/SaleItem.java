package com.sweetshop.mockmodel;

public class SaleItem {
    private int id;
    private int saleId;
    private int sweetId;
    private int quantity;
    private double price;

    // Default constructor
    public SaleItem() {}

    // Constructor with all fields (for flexibility)
    public SaleItem(int id, int saleId, int sweetId, int quantity, double price) {
        this.id = id;
        this.saleId = saleId;
        this.sweetId = sweetId;
        this.quantity = quantity;
        this.price = price;
    }

    // ✅ Extra constructor (used by BillingService dummy method)
    public SaleItem(int sweetId, int saleId, int quantity, double price) {
        this.sweetId = sweetId;
        this.saleId = saleId;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getSaleId() { return saleId; }
    public void setSaleId(int saleId) { this.saleId = saleId; }

    public int getSweetId() { return sweetId; }
    public void setSweetId(int sweetId) { this.sweetId = sweetId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
