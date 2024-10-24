package com.dynamicpdf.dynamicpdf;

import com.dynamicpdf.dynamicpdf.dto.Invoice;
import com.dynamicpdf.dynamicpdf.dto.Item;
import com.dynamicpdf.dynamicpdf.service.PdfService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PdfServiceTest {

    @InjectMocks
    private PdfService pdfService;

    private Invoice testInvoice;

    @BeforeEach
    void setUp() {
        Item item = new Item();
        item.setName("Test Item");
        item.setQuantity(String.valueOf(2));
        item.setRate(100.0);
        item.setAmount(200.0);

        testInvoice = new Invoice();
        testInvoice.setSeller("Test Seller");
        testInvoice.setSellerAddress("123 Seller St");
        testInvoice.setSellerGstin("SELLER123GSTIN");
        testInvoice.setBuyer("Test Buyer");
        testInvoice.setBuyerAddress("456 Buyer Ave");
        testInvoice.setBuyerGstin("BUYER456GSTIN");
        testInvoice.setItems(Arrays.asList(item));
    }

    @Test
    void generateInvoicePdf_ShouldGeneratePdfSuccessfully() {
        // When
        ByteArrayOutputStream result = pdfService.generateInvoicePdf(testInvoice);
        // Then
        assertNotNull(result);
    }

}