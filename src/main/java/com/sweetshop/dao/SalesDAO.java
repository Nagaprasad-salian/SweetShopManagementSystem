package com.sweetshop.dao;

import com.sweetshop.model.Sale;
import com.sweetshop.model.SaleItem;
import com.sweetshop.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalesDAO {

    // ✅ Save Sale and return generated sale_id
    public int saveSale(Sale sale) {
        String sql = "INSERT INTO sales (user_id, date, total_amount) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, sale.getUserId());
            pstmt.setString(2, sale.getDate());
            pstmt.setDouble(3, sale.getTotalAmount());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // ✅ Save Sale Items for each sale
    public void saveSaleItem(SaleItem item) {
        String sql = "INSERT INTO sale_items (sale_id, item_id, quantity, subtotal) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, item.getSaleId());
            pstmt.setInt(2, item.getItemId());
            pstmt.setInt(3, item.getQuantity());
            pstmt.setDouble(4, item.getSubtotal());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ Get all sales (for Reports)
    public List<Sale> getAllSales() {
        List<Sale> sales = new ArrayList<>();
        String sql = "SELECT * FROM sales";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Sale sale = new Sale();
                sale.setSaleId(rs.getInt("sale_id"));
                sale.setUserId(rs.getInt("user_id"));
                sale.setDate(rs.getString("date"));
                sale.setTotalAmount(rs.getDouble("total_amount"));
                sales.add(sale);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sales;
    }

    // ✅ Get sale items for a given sale (used for PDF generation or reports)
    public List<SaleItem> getSaleItemsBySaleId(int saleId) {
        List<SaleItem> items = new ArrayList<>();
        String sql = "SELECT * FROM sale_items WHERE sale_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, saleId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                SaleItem item = new SaleItem();
                item.setSaleItemId(rs.getInt("sale_item_id"));
                item.setSaleId(rs.getInt("sale_id"));
                item.setItemId(rs.getInt("item_id"));
                item.setQuantity(rs.getInt("quantity"));
                item.setSubtotal(rs.getDouble("subtotal"));
                items.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
}
