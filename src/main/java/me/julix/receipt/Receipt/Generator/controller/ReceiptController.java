package me.julix.receipt.Receipt.Generator.controller;

import me.julix.receipt.Receipt.Generator.helper.CSVHelper;
import me.julix.receipt.Receipt.Generator.model.Services;
import me.julix.receipt.Receipt.Generator.service.Receipt;
import org.json.JSONObject;
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
            String message = "";

            if (CSVHelper.hasCSVFormat(file)) {
                try {
                    List<Services> svs = fileService.save(file);

                    return new ResponseEntity<Object>(svs, HttpStatus.OK);
                } catch (Exception e) {
                    message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                    return new ResponseEntity<Object>("svs", HttpStatus.OK);
                }
            }

            message = "Please upload a csv file!";
            return new ResponseEntity<Object>("svs", HttpStatus.OK);
            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }

        @GetMapping("/csv")
        public String getAllTutorials() {
            try {
                //List<Services> services = fileService.getClass();

                /* if (services.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } */

                //return new ResponseEntity<>("teste", HttpStatus.OK);
                return JSONObject.quote("Hello World");
            } catch (Exception e) {
                return JSONObject.quote("erro");

                //return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
}
