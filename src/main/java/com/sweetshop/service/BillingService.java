package com.sweetshop.service;

import com.sweetshop.dao.SalesDAO;
import com.sweetshop.dao.SweetItemDAO;
import com.sweetshop.model.Sale;
import com.sweetshop.model.SaleItem;
import java.time.LocalDateTime;
import java.util.List;

public class BillingService {

    private  SalesDAO salesDAO;
    private  SweetItemDAO sweetItemDAO;

    public BillingService() {
        this.salesDAO = new SalesDAO();
        this.sweetItemDAO = new SweetItemDAO();
    }

    // ✅ Calculate total amount from sale items
    public double calculateTotal(List<SaleItem> saleItems) {
    double total = 0.0;
    for (SaleItem item : saleItems) {
        // Each SaleItem already stores its subtotal (price * quantity)
        total += item.getSubtotal();
    }
    return total;
}


    // ✅ Process sale: save to DB and update stock
    public boolean processSale(Sale sale, List<SaleItem> saleItems) {
        try {
            // 1️⃣ Save sale to DB
            int saleId = salesDAO.saveSale(sale);

            // 2️⃣ Save each sale item and update stock
            for (SaleItem item : saleItems) {
                item.setSaleId(saleId);
                salesDAO.saveSaleItem(item);

                // Deduct stock in inventory
                sweetItemDAO.updateStock(item.getItemId(), item.getQuantity());
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Create sale object before processing
    public Sale createSale(int userId, double total) {
        Sale sale = new Sale();
        sale.setUserId(userId);
        sale.setDate(LocalDateTime.now().toString());
        sale.setTotalAmount(total);
        return sale;
    }
    // Add these setters to support TDD injection of mock DAOs
public void setSweetItemDAO(com.sweetshop.dao.SweetItemDAO dao) {
    this.sweetItemDAO = dao;
}

public void setSalesDAO(com.sweetshop.dao.SalesDAO dao) {
    this.salesDAO = dao;
}

}
