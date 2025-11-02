package com.sweetshop;

import com.sweetshop.model.Sale;
import com.sweetshop.model.SaleItem;
import com.sweetshop.service.BillingService;
import com.sweetshop.mockdao.MockSalesDAO;
import com.sweetshop.mockdao.MockSweetItemDAO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

/**
 * ✅ BillingServiceTDDTest
 * Uses mock DAOs to test BillingService logic independently.
 * Covers total calculation, sale creation, and stock update logic.
 */
public class BillingServiceTDDTest {

    @Test
    public void testCalculateTotal_ValidInput() {
        BillingService service = new BillingService();
        List<SaleItem> items = new ArrayList<>();

        // Constructor: (saleItemId, saleId, itemId, quantity, subtotal)
        // subtotal should represent total for that item = price * quantity
        SaleItem i1 = new SaleItem(0, 0, 1, 2, 20.0); // 2 items at ₹10 each
        SaleItem i2 = new SaleItem(0, 0, 2, 3, 60.0); // 3 items at ₹20 each

        items.add(i1);
        items.add(i2);

        double total = service.calculateTotal(items);
        assertEquals(80.0, total, 0.001, "Total should sum all subtotals correctly");
    }

    @Test
    public void testCreateSale() {
        BillingService service = new BillingService();
        MockSalesDAO mockSalesDAO = new MockSalesDAO();
        service.setSalesDAO(mockSalesDAO);

        Sale sale = service.createSale(1, 100.0);

        assertNotNull(sale, "Sale should not be null");
        assertEquals(1, sale.getUserId(), "User ID must match");
        assertEquals(100.0, sale.getTotalAmount(), 0.001, "Total amount must match");
    }

    @Test
    public void testProcessSale_UpdatesStock() {
        BillingService service = new BillingService();
        MockSalesDAO mockSalesDAO = new MockSalesDAO();
        MockSweetItemDAO mockSweetItemDAO = new MockSweetItemDAO();

        service.setSalesDAO(mockSalesDAO);
        service.setSweetItemDAO(mockSweetItemDAO);

        Sale sale = service.createSale(1, 100.0);

        List<SaleItem> items = new ArrayList<>();
        items.add(new SaleItem(0, 1, 1, 5, 50.0)); // sold 5 ladoos

        boolean ok = service.processSale(sale, items);
        assertTrue(ok, "Sale processing should succeed");

        // ✅ verify stock updated correctly in mock DAO
        assertEquals(95, mockSweetItemDAO.getItemQuantity(1), "Stock should reduce by 5 units");
    }
}
