package com.sweetshop.dao;

import com.sweetshop.model.SweetItem;
import com.sweetshop.util.DBConnection;
import java.sql.*;
import java.util.*;

public class SweetItemDAO {

    // ✅ Get all sweets with stock > 0 (used in BillingFrame)
    public List<SweetItem> getAllItems() {
        List<SweetItem> items = new ArrayList<>();
        String query = "SELECT * FROM sweet_items WHERE quantity > 0 ORDER BY name ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                SweetItem item = new SweetItem(
                    rs.getInt("item_id"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getDouble("price"),
                    rs.getInt("quantity")
                );
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    // ✅ Fetch single sweet by ID
    public SweetItem getSweetById(int sweetId) {
        SweetItem sweet = null;
        String query = "SELECT * FROM sweet_items WHERE item_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, sweetId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                sweet = new SweetItem(
                    rs.getInt("item_id"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getDouble("price"),
                    rs.getInt("quantity")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sweet;
    }

    // ✅ Update stock after sale — subtracts sold qty & deletes if zero
    public void updateStock(int itemId, int soldQty) {
        String selectQuery = "SELECT quantity FROM sweet_items WHERE item_id = ?";
        String updateQuery = "UPDATE sweet_items SET quantity = ? WHERE item_id = ?";
        String deleteQuery = "DELETE FROM sweet_items WHERE item_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {

            selectStmt.setInt(1, itemId);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                int currentQty = rs.getInt("quantity");
                int newQty = currentQty - soldQty;

                if (newQty <= 0) {
                    try (PreparedStatement del = conn.prepareStatement(deleteQuery)) {
                        del.setInt(1, itemId);
                        del.executeUpdate();
                    }
                } else {
                    try (PreparedStatement upd = conn.prepareStatement(updateQuery)) {
                        upd.setInt(1, newQty);
                        upd.setInt(2, itemId);
                        upd.executeUpdate();
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ Add new sweet (used in inventory add)
    public boolean addItem(SweetItem item) {
        String query = "INSERT INTO sweet_items (name, category, price, quantity) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, item.getName());
            pstmt.setString(2, item.getCategory());
            pstmt.setDouble(3, item.getPrice());
            pstmt.setInt(4, item.getQuantity());
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Update quantity manually (used in inventory management)
    public boolean updateQuantity(int itemId, int newQty) {
        String query = "UPDATE sweet_items SET quantity = ? WHERE item_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, newQty);
            pstmt.setInt(2, itemId);
            pstmt.executeUpdate();

            // if quantity hits zero, remove item from DB
            if (newQty <= 0) {
                String del = "DELETE FROM sweet_items WHERE item_id = ?";
                try (PreparedStatement delStmt = conn.prepareStatement(del)) {
                    delStmt.setInt(1, itemId);
                    delStmt.executeUpdate();
                }
            }
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
