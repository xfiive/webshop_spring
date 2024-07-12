package com.example.pj_webshop.components.emails;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class HtmlToPdfConverter {

    public byte[] convertHtmlToPdf(String htmlContent) throws IOException {
        Document doc = Jsoup.parse(htmlContent);
        doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(doc.html());
            renderer.layout();
            renderer.createPDF(outputStream);
            return outputStream.toByteArray();
        }
    }
}
