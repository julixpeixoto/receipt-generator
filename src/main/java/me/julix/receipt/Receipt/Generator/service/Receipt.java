package me.julix.receipt.Receipt.Generator.service;

import com.google.zxing.WriterException;
import me.julix.receipt.Receipt.Generator.helper.*;
import me.julix.receipt.Receipt.Generator.model.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@Service
public class Receipt {

    @Value("${app.siteDomain}")
    private String siteDomain;
    @Value("${app.filesPath}")
    private String filesPath;

    @Autowired
    private SendEmailHelper sendEmailHelper;

    public List<Services> save(MultipartFile file){
        try{
            List<Services> services = CSVHelper.csvExtract(file.getInputStream());

            String md5 = HashHelper.getRandomHashMd5();
            String fileName = md5 + ".pdf";
            String url = siteDomain + "//" + filesPath + "//" + fileName;

            QRCodeHelper.generateQRcode(url);
            PDFWriterHelper.generatePDF(services, md5);

            sendEmailHelper.sendEmailWithAttachment("contato@julix.me", "Comprovante", "Teste...", fileName);

            return services;
        }catch (IOException | WriterException | MessagingException e) {
            throw new RuntimeException("Falha ao gravar o arquivo CSV: " + e.getMessage());
        }
    }
}
