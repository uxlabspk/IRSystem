package io.github.uxlabspk.irsystem.utils;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class InvertedIndexBuilder {
    private final Map<String, Set<String>> invertedIndex;
    private final Set<String> stop_words;

    public InvertedIndexBuilder() {
        this.invertedIndex = new TreeMap<>();
        this.stop_words = initializeStopwords();
    }
    
    private Set<String> initializeStopwords() {
        return new HashSet<>(Set.of(
                "a", "an", "and", "are", "as", "at", "be", "by", "for", "from",
                "has", "he", "in", "is", "it", "its", "of", "on", "that", "the",
                "to", "was", "were", "will", "with", "this", "their", "there", "these",
                "those", "they", "we", "which", "who", "what", "when", "where", "why"
        ));

    }
    
    private void indexDocument(String documentId, String content) {
        String[] terms = tokenize(content);
        
        // Process each term
        for (String term : terms) {
            if (term.length() <= 2 || stop_words.contains(term)) {
                continue;
            }
            
            // Add the term to the inverted index
            invertedIndex.computeIfAbsent(term, _ -> new HashSet<>()).add(documentId);
        }
    }
    
    private String[] tokenize(String content) {
        String normalizedContent = content.toLowerCase();
        
        // Remove special characters and replace with spaces
        normalizedContent = normalizedContent.replaceAll("[^a-z0-9\\s]", " ");

        return normalizedContent.split("\\s+");
    }
    
    public void indexDirectory(String textDirectory) throws IOException {
        File dir = new File(textDirectory);
        File[] textFiles = dir.listFiles((_, name) -> name.toLowerCase().endsWith(".txt"));
        
        if (textFiles == null || textFiles.length == 0) {
            System.out.println("No text files found in " + textDirectory);
            return;
        }
        
        System.out.println("Indexing " + textFiles.length + " documents...");
        
        for (File textFile : textFiles) {
            String documentId = textFile.getName().replace(".txt", "");
            String content = new String(Files.readAllBytes(textFile.toPath()));
            indexDocument(documentId, content);
            System.out.println("Indexed document: " + documentId);
        }
    }
    
    public void saveInvertedIndex(String outputFile) throws IOException {
        System.out.println("Saving inverted index with " + invertedIndex.size() + " terms to " + outputFile);
        
        StringBuilder sb = new StringBuilder();
        
        // Write header
        sb.append("INVERTED INDEX\n");
        sb.append("==============\n");
        sb.append("Format: term: doc1, doc2, ...\n\n");
        
        // Write index entries
        for (Map.Entry<String, Set<String>> entry : invertedIndex.entrySet()) {
            String term = entry.getKey();
            Set<String> documents = entry.getValue();

            if (documents.isEmpty()) {
                continue;
            }
            
            sb.append(term).append(": ");
            sb.append(String.join(", ", documents));
            sb.append("\n");
        }

        Files.write(Paths.get(outputFile), sb.toString().getBytes());
    }
}