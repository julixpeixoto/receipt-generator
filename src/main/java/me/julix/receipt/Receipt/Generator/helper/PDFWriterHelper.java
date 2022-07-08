package me.julix.receipt.Receipt.Generator.helper;

import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfString;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Document;
import me.julix.receipt.Receipt.Generator.model.Services;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PDFWriterHelper {
    public static void generatePDF(List<Services> data, String fileName, String customerName) throws IOException {
        Document document = new Document();
        String tempDir = "temp/";
        final PdfWriter instance = PdfWriter.getInstance(document, new FileOutputStream(tempDir + fileName+ ".pdf"));
        document.open();
        instance.getInfo().put(PdfName.CREATOR, new PdfString(Document.getVersion()));

        Paragraph paragraph = new Paragraph("COMPROVANTE", new Font(Font.HELVETICA, 14, Font.BOLD));
        Paragraph customer = new Paragraph("Cliente: " + customerName, new Font(Font.HELVETICA, 12, Font.NORMAL));
        paragraph.setAlignment(1);
        paragraph.setSpacingAfter(12f);
        customer.setSpacingAfter(12f);
        document.add(paragraph);
        document.add(customer);

        PdfPTable table = null;
        table = new PdfPTable(3);

        table.addCell("DATA");
        table.addCell("DESCRIÇÃO");
        table.addCell("VALOR");

        for (Services srv: data) {
            table.addCell(srv.getData());
            table.addCell(srv.getDescricao());
            table.addCell(srv.getValor());
        }

        table.addCell("teste");
        document.add(table);

        Image qrCode = Image.getInstance(tempDir + "qrcode.png");
        document.add(qrCode);

        document.close();
    }
}
