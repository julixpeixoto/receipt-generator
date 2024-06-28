package me.julix.receipt.Receipt.Generator.service;

import com.google.zxing.WriterException;
import me.julix.receipt.Receipt.Generator.dto.DataDto;
import me.julix.receipt.Receipt.Generator.dto.ResponseDto;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
            generatePdf(services, "", "", "", "", new BigDecimal(0));

            return ResponseEntity.ok(services);
        } catch (IOException | WriterException | MessagingException e) {
            throw new RuntimeException("Falha ao gravar o arquivo CSV: " + e.getMessage());
        }
    }

    public ResponseEntity<Object> generateByRequest(DataDto data) throws IOException, WriterException, MessagingException {
        List<Services> services = new ArrayList<>();

        data.getReceipts().forEach(receipt -> {
            Services service = new Services();
            service.setData(DateTimeHelper.dateToDDMMYYYY(receipt.getDate()));
            service.setDescricao(receipt.getDescription());
            service.setValor(receipt.getValue().toString());
            services.add(service);
        });

        BigDecimal totalValue = data.getReceipts().stream()
                .map(receipt -> BigDecimal.valueOf(receipt.getValue()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        String filePath = generatePdf(services, data.getCustomerName(), data.getReceiverName(), data.getPaymentMethod(), data.getPaymentDate(), totalValue);
        //sendEmail(filePath, data.getCustomerEmail());

        ResponseDto responseDto = new ResponseDto();
        responseDto.setFile(filePath);
        return ResponseEntity.ok(responseDto);
    }

    private String generatePdf(List<Services> services, String customerName, String receiverName, String paymentMethod, String paymentDate, BigDecimal total) throws IOException, WriterException, MessagingException {
        UUID uuid = UUID.randomUUID();
        String tempDir = "temp/";
        String fileName = uuid + ".pdf";
        String filePath = tempDir + fileName;
        String url = siteDomain + "/" + filesPath + "/" + fileName;

        QRCodeHelper.generateQRcode(url);
        PDFWriterHelper.generatePDF(services, uuid.toString(), customerName, receiverName, paymentMethod, paymentDate, total);

        return filePath;
    }

    private void sendEmail(String filePath, String destinationEmail) throws MessagingException, IOException {
        sendEmailHelper.sendEmailWithAttachment(destinationEmail, "Comprovante", "Teste...", filePath);
    }
}
