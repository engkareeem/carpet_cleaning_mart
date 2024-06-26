package com.carpetcleaningmart.utils;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.carpetcleaningmart.model.Customer;
import com.carpetcleaningmart.model.Order;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.*;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;

public class Invoice {

    private Invoice(){
        // Do nothing
    }

    public static void generateInvoice(Order order, Customer customer, double discount, double totalPrice) {
        try {
            // Create a new PDF document
            PdfWriter writer = new PdfWriter(new FileOutputStream("invoice.pdf"));
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Set up the fonts
            PdfFont bold = PdfFontFactory.createFont("Helvetica-Bold");
            PdfFont normal = PdfFontFactory.createFont("Helvetica");

            // Add the invoice header
            Paragraph header = new Paragraph("Carpet Cleaner Mart").setFont(bold).setFontSize(20).setMarginBottom(20).setTextAlignment(TextAlignment.CENTER);
            document.add(header);


            // Add the invoice details
            LocalDate date = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            Paragraph details = new Paragraph()
                    .add("Date: " + date.format(formatter) + "%n")
                    .add(String.format("Invoice Number: %s%n", order.getId()))
                    .add(String.format("Customer Name: %s%n", customer.getName()))
                    .add(String.format("Customer Email: %s%n%n", customer.getEmail()))
                    .setFont(normal);
            document.add(details);

            // Add the invoice table
            Table table = new Table(new UnitValue[]{new UnitValue(4, UnitValue.PERCENT), new UnitValue(8, UnitValue.PERCENT), new UnitValue(4, UnitValue.PERCENT), new UnitValue(1, UnitValue.PERCENT)})
                    .setWidth(500)
                    .setMarginTop(20)
                    .setMarginBottom(20).setHorizontalAlignment(HorizontalAlignment.CENTER).setHorizontalAlignment(HorizontalAlignment.CENTER);

            Cell cell1 = new Cell().add(new Paragraph("Item").setFont(bold).setHorizontalAlignment(HorizontalAlignment.CENTER));
            Cell cell2 = new Cell().add(new Paragraph("Description").setFont(bold).setHorizontalAlignment(HorizontalAlignment.CENTER));
            Cell cell3 = new Cell().add(new Paragraph("Price").setFont(bold).setHorizontalAlignment(HorizontalAlignment.CENTER));
            Cell cell4 = new Cell().add(new Paragraph("Discount").setFont(bold).setHorizontalAlignment(HorizontalAlignment.CENTER));
            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);


            Cell itemCell = new Cell().add(new Paragraph(order.getCategory().toString().toLowerCase()).setFont(normal));
            Cell qtyCell = new Cell().add(new Paragraph(order.getDescription()).setFont(normal));
            Cell priceCell = new Cell().add(new Paragraph(String.format("$%.2f", order.getPrice())).setFont(normal));
            Cell discountCell = new Cell().add(new Paragraph(String.format("%.2f", discount*100) + "%").setFont(normal));
            table.addCell(itemCell);
            table.addCell(qtyCell);
            table.addCell(priceCell);
            table.addCell(discountCell);


            Cell totalLabelCell = new Cell(1, 3).add(new Paragraph("Total").setFont(bold));
            Cell totalValueCell = new Cell().add(new Paragraph(String.format("$%.2f", totalPrice)).setFont(normal));
            table.addCell(totalLabelCell);
            table.addCell(totalValueCell);

            document.add(table);


            Rectangle pageSize = pdfDoc.getPage(1).getPageSize();
            Paragraph footer = new Paragraph("Invoice generated by Carpet Cleaner Mart Services").setFont(normal)
                    .setFixedPosition(pageSize.getLeft(), 50, pageSize.getWidth())
                    .setMargin(0)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(8);
            document.add(footer);

            document.close();
        }catch (Exception exception) {
            Printer.printError(exception.getMessage());
        }
    }
}