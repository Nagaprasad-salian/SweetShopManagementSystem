package com.sweetshop.mockdao;

import com.sweetshop.model.SweetItem;
import com.sweetshop.dao.SweetItemDAO;
import java.util.*;

public class MockSweetItemDAO extends SweetItemDAO {
    private final Map<Integer, SweetItem> items = new HashMap<>();

    public MockSweetItemDAO() {
        // mock items with id, name, category, price, quantity
        items.put(1, new SweetItem(1, "Ladoo", "Traditional", 10.0, 100));
        items.put(2, new SweetItem(2, "Jalebi", "Traditional", 15.0, 80));
    }

    @Override
    public SweetItem getSweetById(int id) {
        return items.get(id);
    }

    @Override
    public void updateStock(int sweetId, int quantitySold) {
        SweetItem item = items.get(sweetId);
        if (item != null) {
            item.setQuantity(item.getQuantity() - quantitySold);
        }
    }

    // helper method for test verification
    public int getItemQuantity(int id) {
        SweetItem item = items.get(id);
        return (item != null) ? item.getQuantity() : -1;
    }
}
