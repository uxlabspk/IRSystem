package io.github.uxlabspk.irsystem.utils;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class InvertedIndexLoader {

    private final Map<String, Set<Integer>> invertedIndex = new HashMap<>();

    public InvertedIndexLoader() throws IOException {
        Path path = Paths.get("src/main/resources/inverted_index.txt");

        if (Files.exists(path)) {
            loadIndex(path);
        } else {
            // first convert the pdfs into txt format
            var pdfToTextConverter = new PDFToTextConverter();
            pdfToTextConverter.convertPdf();

            // second generate the inverted index file
            var invertedIndexBuilder = new InvertedIndexBuilder();
            invertedIndexBuilder.indexDirectory("output");
            invertedIndexBuilder.saveInvertedIndex("src/main/resources/inverted_index.txt");

            loadIndex(path);
        }
    }

    public void loadIndex(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path);
        for (String line : lines) {
            String[] parts = line.split(":");
            if (parts.length == 2) {
                String term = parts[0].trim().toLowerCase();
                Set<Integer> docs = Arrays.stream(parts[1].split(","))
                                          .map(String::trim)
                                          .map(Integer::parseInt)
                                          .collect(Collectors.toSet());
                invertedIndex.put(term, docs);
            }
        }
    }

    public Map<String, Set<Integer>> getInvertedIndex() {
        return invertedIndex;
    }
}