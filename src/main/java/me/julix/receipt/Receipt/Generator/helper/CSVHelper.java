package me.julix.receipt.Receipt.Generator.helper;

import me.julix.receipt.Receipt.Generator.model.Services;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERS = {"Data", "Descrição", "Valor"};

    public static boolean hasCSVFormat(MultipartFile file){

        String fileType = file.getContentType();

        if(!TYPE.equals(file.getContentType())){
            return false;
        }

        return true;
    }

    public static List<Services> csvExtract(InputStream is){
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"))){
            CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim());

            List<Services> services = new ArrayList<Services>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord: csvRecords){
                Services service = new Services(
                    csvRecord.get("data"),
                    csvRecord.get("descricao"),
                    csvRecord.get("valor")
                );

                services.add(service);
            }
            return services;
        }catch (IOException e){
            throw new RuntimeException("Falha ao carregar o arquivo CSV: " + e.getMessage());
        }
    }
}
