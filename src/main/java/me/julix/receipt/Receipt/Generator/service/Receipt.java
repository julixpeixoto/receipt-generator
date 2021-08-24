package me.julix.receipt.Receipt.Generator.service;

import com.google.zxing.WriterException;
import me.julix.receipt.Receipt.Generator.helper.CSVHelper;
import me.julix.receipt.Receipt.Generator.helper.HashHelper;
import me.julix.receipt.Receipt.Generator.helper.PDFWriterHelper;
import me.julix.receipt.Receipt.Generator.helper.QRCodeHelper;
import me.julix.receipt.Receipt.Generator.model.Services;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class Receipt {
    @Value("${app.siteDomain}")
    private String siteDomain;
    @Value("${app.filesPath}")
    private String filesPath;

    public List<Services> save(MultipartFile file){
        try{
            List<Services> services = CSVHelper.csvExtract(file.getInputStream());

            String md5 = HashHelper.getRandomHashMd5();
            String url = siteDomain + "//" + filesPath + "//" + md5 + ".pdf";

            QRCodeHelper.generateQRcode(url);
            PDFWriterHelper.generatePDF(services, md5);

            return services;
        }catch (IOException | WriterException e){
            throw new RuntimeException("Falha ao gravar o arquivo CSV: " + e.getMessage());
        }
    }
}
