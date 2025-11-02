package com.sweetshop.mockdao;

import com.sweetshop.dao.SalesDAO;
import com.sweetshop.model.Sale;
import com.sweetshop.model.SaleItem;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory mock of SalesDAO used for tests. Extends real SalesDAO so it can be injected.
 * Saves sales/saleItems into memory lists and returns generated IDs.
 */
public class MockSalesDAO extends SalesDAO {

    private final List<Sale> sales = new ArrayList<>();
    private final List<SaleItem> saleItems = new ArrayList<>();
    private int saleIdCounter = 0;
    private int saleItemIdCounter = 0;

    @Override
    public int saveSale(Sale sale) {
        saleIdCounter++;
        sale.setSaleId(saleIdCounter);
        sales.add(sale);
        return sale.getSaleId();
    }

    @Override
    public void saveSaleItem(SaleItem item) {
        saleItemIdCounter++;
        item.setSaleItemId(saleItemIdCounter);
        saleItems.add(item);
    }

    @Override
    public List<Sale> getAllSales() {
        return new ArrayList<>(sales);
    }

    @Override
    public List<SaleItem> getSaleItemsBySaleId(int saleId) {
        List<SaleItem> out = new ArrayList<>();
        for (SaleItem si : saleItems) {
            if (si.getSaleId() == saleId) out.add(si);
        }
        return out;
    }
}
