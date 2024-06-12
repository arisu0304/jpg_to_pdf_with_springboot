package com.bit.jpg_to_pdf_converter.service;

import org.springframework.stereotype.Service;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

@Service
public class ImageToPdfService {

    public byte[] convertToPdf(byte[] imageBytes) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (ByteArrayInputStream in = new ByteArrayInputStream(imageBytes)) {
                BufferedImage bImageFromConvert = ImageIO.read(in);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                ImageIO.write(bImageFromConvert, "png", out);
                byte[] bImage = out.toByteArray();

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page, AppendMode.APPEND, true, true)) {
                    contentStream.drawImage(PDImageXObject.createFromByteArray(document, bImage, ""), 0, 0);
                }
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);
            return baos.toByteArray();
        }
    }
}
