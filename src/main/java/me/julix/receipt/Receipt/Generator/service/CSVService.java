package me.julix.receipt.Receipt.Generator.service;

import me.julix.receipt.Receipt.Generator.helper.CSVHelper;
import me.julix.receipt.Receipt.Generator.model.Services;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CSVService {
    public List<Services> save(MultipartFile file){
        try{
            List<Services> services = CSVHelper.csvExtract(file.getInputStream());
            return services;
        }catch (IOException e){
            throw new RuntimeException("Falha ao gravar o arquivo CSV: " + e.getMessage());
        }
    }
}
