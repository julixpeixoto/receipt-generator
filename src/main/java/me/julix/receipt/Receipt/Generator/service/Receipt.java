package me.julix.receipt.Receipt.Generator.service;

import com.google.zxing.WriterException;
import me.julix.receipt.Receipt.Generator.dto.DataDto;
import me.julix.receipt.Receipt.Generator.exception.FileNotSupportedException;
import me.julix.receipt.Receipt.Generator.helper.*;
import me.julix.receipt.Receipt.Generator.model.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class Receipt {

    @Value("${app.siteDomain}")
    private String siteDomain;
    @Value("${app.filesPath}")
    private String filesPath;

    @Autowired
    private SendEmailHelper sendEmailHelper;

    public ResponseEntity<Object> generateByFile(MultipartFile file){
        if (!CSVHelper.hasCSVFormat(file)) {
            throw new FileNotSupportedException();
        }

        try{
            List<Services> services = CSVHelper.csvExtract(file.getInputStream());
            generatePdf(services, "");

            return ResponseEntity.ok(services);
        } catch (IOException | WriterException | MessagingException e) {
            throw new RuntimeException("Falha ao gravar o arquivo CSV: " + e.getMessage());
        }
    }

    public ResponseEntity<Object> generateByRequest(DataDto data) throws IOException, WriterException, MessagingException {
        List<Services> services = new ArrayList<>();

        data.getReceipts().forEach(receipt -> {
            Services service = new Services();
            service.setData(receipt.getDate().toString());
            service.setDescricao(receipt.getDescription());
            service.setValor(receipt.getValue().toString());

            services.add(service);
        });

        String filePath = generatePdf(services, data.getCustomerName());
        sendEmail(filePath, data.getCustomerEmail());

        return ResponseEntity.ok(services);
    }

    private String generatePdf(List<Services> services, String customerName) throws IOException, WriterException, MessagingException {
        String md5 = HashHelper.getRandomHashMd5();
        String tempDir = "temp/";
        String fileName = md5 + ".pdf";
        String filePath = tempDir + fileName;
        String url = siteDomain + "//" + filesPath + "//" + fileName;

        QRCodeHelper.generateQRcode(url);
        PDFWriterHelper.generatePDF(services, md5, customerName);

        return filePath;
    }

    private void sendEmail(String filePath, String destinationEmail) throws MessagingException, IOException {
        sendEmailHelper.sendEmailWithAttachment(destinationEmail, "Comprovante", "Teste...", filePath);
    }
}
