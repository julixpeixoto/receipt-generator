package me.julix.receipt.Receipt.Generator.helper;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import me.julix.receipt.Receipt.Generator.model.Services;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PDFWriterHelper {
    public static void generatePDF(List<Services> data, String fileName, String customerName, String receiverName, String paymentMethod, String paymentDate, BigDecimal total) throws IOException {
        Document document = new Document();
        String tempDir = "temp/";
        final PdfWriter instance = PdfWriter.getInstance(document, new FileOutputStream(tempDir + fileName+ ".pdf"));
        document.open();
        instance.getInfo().put(PdfName.CREATOR, new PdfString(Document.getVersion()));
        paymentDate = paymentDate != null ? paymentDate : DateTimeHelper.dateToDDMMYYYY(LocalDate.now());

        Paragraph paragraph = new Paragraph("RECIBO", new Font(Font.HELVETICA, 14, Font.BOLD));
        Paragraph customer = new Paragraph("PAGO POR: " + customerName, new Font(Font.HELVETICA, 12, Font.NORMAL));
        Paragraph receiver = new Paragraph("RECEBIDO POR: " + receiverName, new Font(Font.HELVETICA, 12, Font.NORMAL));
        Paragraph payment = new Paragraph("MEIO DE PAGAMENTO: " + paymentMethod, new Font(Font.HELVETICA, 12, Font.NORMAL));
        Paragraph date = new Paragraph("DATA DE PAGAMENTO: " + paymentDate, new Font(Font.HELVETICA, 12, Font.NORMAL));
        paragraph.setAlignment(1);
        paragraph.setSpacingAfter(12f);
        customer.setSpacingAfter(12f);
        receiver.setSpacingAfter(12f);
        payment.setSpacingAfter(12f);
        date.setSpacingAfter(12f);
        document.add(paragraph);
        document.add(customer);
        document.add(receiver);
        document.add(payment);
        document.add(date);

        PdfPTable table = null;
        table = new PdfPTable(3);

        table.addCell(new Phrase("DATA", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
        table.addCell(new Phrase("DESCRIÇÃO", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
        table.addCell(new Phrase("VALOR", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));

        for (Services srv: data) {
            table.addCell(srv.getData());
            table.addCell(srv.getDescricao());
            table.addCell(srv.getValor());
        }

        PdfPCell totalCell = new PdfPCell(new Phrase("TOTAL", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
        PdfPCell valueCell = new PdfPCell(new Phrase(String.valueOf(total), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL)));
        totalCell.setColspan(2);
        table.addCell(totalCell);
        table.addCell(valueCell);

        document.add(table);

        Image qrCode = Image.getInstance(tempDir + "qrcode.png");
        document.add(qrCode);

        document.close();
    }
}
