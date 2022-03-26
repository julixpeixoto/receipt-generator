package me.julix.receipt.Receipt.Generator.controller;

import me.julix.receipt.Receipt.Generator.exception.FileNotSupportedException;
import me.julix.receipt.Receipt.Generator.helper.CSVHelper;
import me.julix.receipt.Receipt.Generator.model.Services;
import me.julix.receipt.Receipt.Generator.service.Receipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin("http://localhost:8081")
@RestController
@RequestMapping("/api/csv")
public class ReceiptController {
    @Autowired
    Receipt fileService;

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) {
        if (CSVHelper.hasCSVFormat(file)) {
            try {
                List<Services> data = fileService.save(file);

                return new ResponseEntity<Object>(data, HttpStatus.CREATED);
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }

        throw new FileNotSupportedException();
    }
}
