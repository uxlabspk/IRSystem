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
        loadIndex();
    }
    public void loadIndex() throws IOException {
        Path path = Paths.get("src/main/resources/inverted_index.txt");
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