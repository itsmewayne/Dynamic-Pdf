package com.dynamicpdf.dynamicpdf.service;

import com.dynamicpdf.dynamicpdf.dto.Invoice;
import com.dynamicpdf.dynamicpdf.dto.Item;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PdfService {

    public ByteArrayOutputStream generateInvoicePdf(Invoice invoice) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            // Initialize PDF writer and document
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDoc = new PdfDocument(writer);
            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            Document doc = new Document(pdfDoc).setFont(font);

            // Create header table for seller and buyer information
            Table headerTable = createHeaderTable(invoice);
            doc.add(headerTable);

            // Create product information table
            Table productTable = createProductTable(invoice.getItems());
            doc.add(productTable);

            // Close the document
            doc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputStream;
    }

    private Table createHeaderTable(Invoice invoice) {
        float[] columnWidths = { 280F, 280F };
        Table headerTable = new Table(columnWidths);

        String sellerInfo = String.format("Seller:\n%s\n%s\nGSTIN: %s",
                invoice.getSeller(), invoice.getSellerAddress(), invoice.getSellerGstin());
        headerTable.addCell(createCell(sellerInfo));

        String buyerInfo = String.format("Buyer:\n%s\n%s\nGSTIN: %s",
                invoice.getBuyer(), invoice.getBuyerAddress(), invoice.getBuyerGstin());
        headerTable.addCell(createCell(buyerInfo));

        return headerTable;
    }

    private Table createProductTable(List<Item> items) {
        float[] columnWidths = { 140, 140, 140, 140 };
        Table productTable = new Table(columnWidths).setTextAlignment(TextAlignment.CENTER);

        // Add headers to the product table
        productTable.addCell(createCell("Item"));
        productTable.addCell(createCell("Quantity"));
        productTable.addCell(createCell("Rate"));
        productTable.addCell(createCell("Amount"));

        // Add items to the product table
        for (Item item : items) {
            productTable.addCell(createCell(item.getName()));
            productTable.addCell(createCell(item.getQuantity()));
            productTable.addCell(createCell(String.valueOf(item.getRate())));
            productTable.addCell(createCell(String.valueOf(item.getAmount())));
        }

        return productTable;
    }

    private Cell createCell(String content) {
        return new Cell().add(new Paragraph(content)).setPadding(10).setTextAlignment(TextAlignment.LEFT);
    }
}