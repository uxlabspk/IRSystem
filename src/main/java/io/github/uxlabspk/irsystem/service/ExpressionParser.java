package io.github.uxlabspk.irsystem.service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

public class ExpressionParser {

    private final Map<String, Set<Integer>> index;

    public ExpressionParser(Map<String, Set<Integer>> index) {
        this.index = index;
    }

    public Set<Integer> evaluate(String expr) {
        // Very basic expression parser
        Stack<Set<Integer>> values = new Stack<>();
        Stack<String> ops = new Stack<>();

        String[] tokens = expr.split("\\s+");
        for (String token : tokens) {
            switch (token) {
                case "and":
                    ops.push("AND"); break;
                case "or":
                    ops.push("OR"); break;
                case "not":
                    ops.push("NOT"); break;
                default:
                    values.push(index.getOrDefault(token, new HashSet<>()));
                    while (!ops.isEmpty() && ops.peek().equals("NOT")) {
                        ops.pop();
                        Set<Integer> set = values.pop();
                        Set<Integer> allDocs = index.values().stream()
                                .flatMap(Set::stream).collect(Collectors.toSet());
                        allDocs.removeAll(set);
                        values.push(allDocs);
                    }
                    if (values.size() >= 2 && !ops.isEmpty()) {
                        Set<Integer> right = values.pop();
                        Set<Integer> left = values.pop();
                        String op = ops.pop();
                        if (op.equals("AND")) {
                            left.retainAll(right);
                            values.push(left);
                        } else if (op.equals("OR")) {
                            left.addAll(right);
                            values.push(left);
                        }
                    }
                    break;
            }
        }

        return values.isEmpty() ? new HashSet<>() : values.pop();
    }
}
