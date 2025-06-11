package io.github.uxlabspk.irsystem.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class PDFToTextConverter {

    public void convertPdf() {
        var folder = new File("pdfs");

        var outputDir = new File("output");
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        for (var file : Objects.requireNonNull(folder.listFiles())) {
            if (file.getName().endsWith(".pdf")) {
                try (PDDocument document = PDDocument.load(file)) {
                    txtConverter(outputDir, file, document);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static void txtConverter(File outputDir, File file, PDDocument document) throws IOException {
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(document);

        String outputFileName = file.getName().replace(".pdf", ".txt");
        FileWriter writer = new FileWriter(new File(outputDir, outputFileName));
        writer.write(text);
        writer.close();
        System.out.println("Converted: " + file.getName());
    }
}