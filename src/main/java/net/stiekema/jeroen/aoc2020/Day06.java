package net.stiekema.jeroen.aoc2020;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day06 {

    public void execute() {
        InputStream in = getClass().getResourceAsStream("/day6_1.txt");
        List<String> lines = new BufferedReader(new InputStreamReader(in)).lines()
                .collect(Collectors.toList());

        List<List<String>> questionGroups = getQuestionGroups(lines);
        Long numberOfQuestions = questionGroups
                .stream()
                .map(uniqueQuestionsAnsweredByAnyOne())
                .reduce(0L, Long::sum);

        System.out.println("Part 1: " + numberOfQuestions);

        numberOfQuestions = questionGroups
                .stream()
                .map(uniqueQuestionsAnsweredByEveryone())
                .reduce(0L, Long::sum);

        System.out.println("Part 2: "+ numberOfQuestions);

    }

    private Function<List<String>, Long> uniqueQuestionsAnsweredByAnyOne() {
        return (questions) ->
                questions.stream()
                        .flatMap(testString -> testString.codePoints().mapToObj(c -> (char) c))
                        .distinct()
                        .count();
    }

    private Function<List<String>, Long> uniqueQuestionsAnsweredByEveryone() {
        return questions -> questions.stream()
                .map(testString -> testString.codePoints().mapToObj(c -> (char) c))
                .reduce((a, b) -> a.distinct().filter(b.collect(Collectors.toList())::contains))
                .stream()
                .flatMap(Function.identity())
                .count();
    }

    private List<List<String>> getQuestionGroups(List<String> lines) {
        List<List<String>> questionGroups = new ArrayList<>();

        List<String> group = new ArrayList<>();
        for (String line : lines) {
            if (StringUtils.isEmpty(line)) {
                questionGroups.add(group);
                group = new ArrayList<>();
            } else {
                group.add(line);
            }
        }

        if (!group.isEmpty()) {
            questionGroups.add(group);
        }

        return questionGroups;
    }


    public static void main(String[] args) {
        new Day06().execute();
    }
}
