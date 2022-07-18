package me.julix.receipt.Receipt.Generator.controller;

import com.google.zxing.WriterException;
import me.julix.receipt.Receipt.Generator.dto.DataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ReceiptController {
    @Autowired
    me.julix.receipt.Receipt.Generator.service.Receipt fileService;

    @PostMapping("/upload")
    public ResponseEntity<Object> generateByFile(@RequestParam("file") MultipartFile file) {
        return fileService.generateByFile(file);
    }

    @PostMapping()
    public ResponseEntity<Object> generateByRequest(@Valid @RequestBody DataDto data) throws MessagingException, IOException, WriterException {
        return fileService.generateByRequest(data);
    }
}
