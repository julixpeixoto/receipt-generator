package me.julix.receipt.Receipt.Generator.service;

import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import me.julix.receipt.Receipt.Generator.helper.CSVHelper;
import me.julix.receipt.Receipt.Generator.helper.HashHelper;
import me.julix.receipt.Receipt.Generator.helper.QRCodeHelper;
import me.julix.receipt.Receipt.Generator.model.Services;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class CSVService {
    public List<Services> save(MultipartFile file){
        try{
            List<Services> services = CSVHelper.csvExtract(file.getInputStream());

            byte[] array = new byte[7]; // length is bounded by 7
            new Random().nextBytes(array);
            String generatedString = new String(array, Charset.forName("UTF-8"));

            String md5 = HashHelper.getHashMd5(generatedString);
            String url = "http://julix.me/comprovantes/" + md5 + ".pdf";
            QRCodeHelper.generateQRcode(url);

            return services;
        }catch (IOException | WriterException e){
            throw new RuntimeException("Falha ao gravar o arquivo CSV: " + e.getMessage());
        }
    }
}
