package io.github.uxlabspk.irsystem.service;

import io.github.uxlabspk.irsystem.utils.InvertedIndexLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class IRService {

    private final InvertedIndexLoader loader;

    @Autowired
    public IRService(InvertedIndexLoader loader) {
        this.loader = loader;
    }

    public Set<Integer> search(String query) {
        Map<String, Set<Integer>> index = loader.getInvertedIndex();

        // Support basic AND, OR, NOT with parentheses
        String processedQuery = query.toLowerCase().replaceAll("[^a-z0-9 ()!&|]", "");
        ExpressionParser parser = new ExpressionParser(index);
        return parser.evaluate(processedQuery);
    }
}
