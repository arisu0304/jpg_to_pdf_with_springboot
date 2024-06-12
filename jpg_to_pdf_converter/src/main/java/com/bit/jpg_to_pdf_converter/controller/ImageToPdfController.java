package com.bit.jpg_to_pdf_converter.controller;

import com.bit.jpg_to_pdf_converter.service.ImageToPdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Controller
public class ImageToPdfController {

    @Autowired
    private ImageToPdfService imageToPdfService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/convert")
    public ResponseEntity<byte[]> convertToPdf(@RequestParam("file") MultipartFile file) {
        try {
            byte[] pdfBytes = imageToPdfService.convertToPdf(file.getBytes());
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
