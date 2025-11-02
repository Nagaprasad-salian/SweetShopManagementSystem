package com.sweetshop.util;

import com.sweetshop.model.Sale;
import com.sweetshop.model.SaleItem;
import com.sweetshop.model.SweetItem;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PDFGenerator {

    // ✅ Generate detailed invoice PDF file
    public static void generateInvoice(Sale sale, List<SaleItem> saleItems, List<SweetItem> sweets) {
        Document document = new Document();
        try {
            String fileName = "invoices/invoice_" + sale.getSaleId() + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            document.add(new Paragraph("Sweet Shop Invoice", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
            document.add(new Paragraph("--------------------------------------------------"));
            document.add(new Paragraph("Invoice ID: " + sale.getSaleId()));
            document.add(new Paragraph("Date: " + sale.getDate()));
            document.add(new Paragraph("User ID: " + sale.getUserId()));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(5);
            table.addCell("Item ID");
            table.addCell("Name");
            table.addCell("Category");
            table.addCell("Quantity");
            table.addCell("Subtotal");

            for (SaleItem item : saleItems) {
                SweetItem sweet = sweets.stream()
                        .filter(i -> i.getItemId() == item.getItemId())
                        .findFirst()
                        .orElse(null);

                if (sweet != null) {
                    table.addCell(String.valueOf(sweet.getItemId()));
                    table.addCell(sweet.getName());
                    table.addCell(sweet.getCategory());
                    table.addCell(String.valueOf(item.getQuantity()));
                    table.addCell(String.format("%.2f", item.getSubtotal()));
                }
            }

            document.add(table);
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Total Amount: ₹" + String.format("%.2f", sale.getTotalAmount())));
            document.add(new Paragraph("Thank you for your purchase!"));
            document.close();

            System.out.println("✅ Invoice generated: " + fileName);

        } catch (Exception e) {
            System.err.println("❌ Error generating PDF invoice:");
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

    // ✅ Simple text-only invoice version (used in BillingFrame)
    public static void generateInvoiceText(String billText, double totalAmount) {
        Document document = new Document();
        try {
            String fileName = "invoices/invoice_simple_" + System.currentTimeMillis() + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            document.add(new Paragraph("Sweet Shop Invoice", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
            document.add(new Paragraph("--------------------------------------------------"));
            document.add(new Paragraph(billText));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Total Amount: ₹" + String.format("%.2f", totalAmount)));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Thank you! Visit Again ❤️"));

            System.out.println("✅ Simple invoice generated: " + fileName);

        } catch (Exception e) {
            System.err.println("❌ Error generating simple invoice:");
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
}
