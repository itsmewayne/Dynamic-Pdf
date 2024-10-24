package com.dynamicpdf.dynamicpdf.controller;

import com.dynamicpdf.dynamicpdf.dto.Invoice;
import com.dynamicpdf.dynamicpdf.service.PdfService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PdfController {
    private final PdfService pdfService;
    @PostMapping("/generatePdf")
    public ResponseEntity<byte[]> generatePdf(@RequestBody @Valid  Invoice invoice) throws Exception {
        ByteArrayOutputStream outputStream = pdfService.generateInvoicePdf(invoice);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("invoice.pdf").build());
        return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
    }

}
