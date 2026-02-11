package com.deft.will.configurations;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.stereotype.Component;


import java.io.ByteArrayOutputStream;
import java.util.List;

@Component
public class PdfGenerator {

    public byte[] mergeAndCreatePdf(List<String> htmlTemplates){

        StringBuilder sb=new StringBuilder();
        sb.append("""
        <!DOCTYPE html>
        <html xmlns="http://www.w3.org/1999/xhtml">
        <head>
            <meta charset="UTF-8"/>
            <style>
                body { font-family: Arial, sans-serif; margin: 0; padding: 0; }
                .header, .footer { background-color: #f2f2f2; padding: 20px; }
                .body { padding: 20px; }
            </style>
        </head>
        <body>
        """);

        htmlTemplates.forEach(sb::append);
        sb.append("""
        </body>
        </html>
        """);
        return generatePdf(sb.toString());
    }
    private byte[] generatePdf(String html){
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(html, null);
            builder.toStream(os);
            builder.run();
            return os.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("PDF generation failed", e);
        }
    }
}
