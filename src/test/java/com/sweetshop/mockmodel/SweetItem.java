package com.sweetshop.mockmodel;

public class SweetItem {
    private int sweetId;
    private String name;
    private double price;
    private int stock;

    public SweetItem(int sweetId, String name, double price, int stock) {
        this.sweetId = sweetId;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String getName() { return name; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}
