package org.rhythm.utils;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MarkdownUtil {
    public static void main(String[] args) {
        String markdownText = "this *Markdown* text，**convert**to PDF。";
        convertMarkdownToPdf(markdownText, "output1.pdf");
    }

    public static void convertMarkdownToPdf(String markdownText, String outputPath) {
        // 使用 flexmark-java 解析 Markdown 文本为 HTML
        Parser parser = Parser.builder().build();
        org.commonmark.node.Node document = parser.parse(markdownText);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        String html = renderer.render(document);

        // 将 HTML 转换为 PDF
        try (FileOutputStream outputStream = new FileOutputStream(outputPath)) {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDocument = new PdfDocument(writer);
            ConverterProperties converterProperties = new ConverterProperties();
            HtmlConverter.convertToPdf(new ByteArrayInputStream(html.getBytes()), pdfDocument, converterProperties);
            pdfDocument.close();
            writer.close();
            System.out.println("PDF 文件已生成：" + outputPath);
        } catch (IOException e) {
            System.out.println("生成 PDF 文件时出错：" + e.getMessage());
        }
    }
}
