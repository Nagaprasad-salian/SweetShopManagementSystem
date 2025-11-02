package com.sweetshop.ui;

import com.sweetshop.dao.SweetItemDAO;
import com.sweetshop.model.Sale;
import com.sweetshop.model.SaleItem;
import com.sweetshop.model.SweetItem;
import com.sweetshop.model.User;
import com.sweetshop.service.BillingService;
import com.sweetshop.util.PDFGenerator;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * BillingFrame - Final version (auto hides 0-quantity sweets and refreshes inventory)
 */
public class BillingFrame extends JFrame {
    private JComboBox<String> sweetCombo;
    private JTextField quantityField;
    private JTextArea billArea;
    private JButton addButton, generateButton, backButton, refreshButton;

    private BillingService billingService;
    private User currentUser;

    // runtime list of SaleItem objects representing the in-progress bill
    private List<SaleItem> saleItems = new ArrayList<>();
    // cached list of SweetItems loaded from DB (used to map name->id/price)
    private List<SweetItem> loadedSweets = new ArrayList<>();

    public BillingFrame(User user) {
        this.currentUser = user;
        this.billingService = new BillingService();

        setTitle("🧾 Billing - Sweet Shop");
        setSize(700, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(255, 250, 240));

        // Top panel - item selection
        JPanel topPanel = new JPanel();
        topPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 6));
        topPanel.setOpaque(false);

        sweetCombo = new JComboBox<>();
        sweetCombo.setPreferredSize(new Dimension(320, 28));
        quantityField = new JTextField(6);

        addButton = new JButton("Add");
        generateButton = new JButton("Generate Bill");
        refreshButton = new JButton("Refresh");
        backButton = new JButton("Back");

        topPanel.add(new JLabel("Select Sweet:"));
        topPanel.add(sweetCombo);
        topPanel.add(new JLabel("Quantity:"));
        topPanel.add(quantityField);
        topPanel.add(addButton);
        topPanel.add(refreshButton);
        topPanel.add(backButton);

        // Bill area
        billArea = new JTextArea();
        billArea.setEditable(false);
        billArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(billArea);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(generateButton, BorderLayout.SOUTH);

        // Load sweets into combo
        loadSweetItems();

        // Handlers
        addButton.addActionListener(e -> addItemToBill());
        generateButton.addActionListener(e -> generateBill());
        refreshButton.addActionListener(e -> loadSweetItems());
        backButton.addActionListener(e -> {
            dispose();
            new DashboardFrame(currentUser).setVisible(true);
        });
    }

    // ✅ Load sweets with quantity > 0 from DB into dropdown
    private void loadSweetItems() {
        try {
            SweetItemDAO sweetDAO = new SweetItemDAO();
            loadedSweets = sweetDAO.getAllItems(); // already filters quantity > 0

            sweetCombo.removeAllItems();
            if (loadedSweets.isEmpty()) {
                sweetCombo.addItem("No sweets available");
                return;
            }

            for (SweetItem s : loadedSweets) {
                sweetCombo.addItem(s.getName() + " - ₹" + s.getPrice() + " (Stock:" + s.getQuantity() + ")");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sweetCombo.removeAllItems();
            sweetCombo.addItem("Error loading sweets");
        }
    }

    // ✅ Add currently selected item to the in-memory bill
    private void addItemToBill() {
        String selected = (String) sweetCombo.getSelectedItem();
        String qtyText = quantityField.getText().trim();

        if (selected == null || selected.startsWith("No") || qtyText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a sweet and enter quantity!");
            return;
        }

        try {
            int qty = Integer.parseInt(qtyText);
            if (qty <= 0) throw new NumberFormatException();

            String nameKey = selected.split(" - ")[0].trim();
            SweetItem match = null;
            for (SweetItem s : loadedSweets) {
                if (s.getName().equals(nameKey)) {
                    match = s;
                    break;
                }
            }

            if (match == null) {
                JOptionPane.showMessageDialog(this, "Selected item not available anymore. Refresh and try again.");
                return;
            }

            if (qty > match.getQuantity()) {
                JOptionPane.showMessageDialog(this, "Requested quantity exceeds stock. Available: " + match.getQuantity());
                return;
            }

            double price = match.getPrice();
            double subtotal = price * qty;

            billArea.append(String.format("%-25s x %-3d = ₹%.2f%n", match.getName(), qty, subtotal));

            SaleItem saleItem = new SaleItem();
            saleItem.setItemId(match.getItemId());
            saleItem.setQuantity(qty);
            saleItem.setSubtotal(subtotal);

            saleItems.add(saleItem);
            quantityField.setText("");
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Enter a valid positive integer for quantity!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding item to bill: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ✅ Generate bill, save sale, update stock & remove zero-stock items
    private void generateBill() {
        try {
            if (saleItems.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No items in the bill!");
                return;
            }

            double grandTotal = saleItems.stream().mapToDouble(SaleItem::getSubtotal).sum();

            Sale sale = billingService.createSale(currentUser.getUserId(), grandTotal);
            boolean ok = billingService.processSale(sale, saleItems);

            if (!ok) {
                JOptionPane.showMessageDialog(this, "Failed to save the sale. Check logs.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // ✅ Generate invoice
            PDFGenerator.generateInvoice(sale, saleItems, loadedSweets);
            JOptionPane.showMessageDialog(this, "Invoice generated and sale recorded successfully!");

            billArea.append("\n-------------------------------\n");
            billArea.append(String.format("Grand Total: ₹%.2f%n", grandTotal));

            saleItems.clear();

            // ✅ Refresh dropdown after stock update (hides 0 qty items)
            loadSweetItems();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error generating/saving invoice. See logs.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
