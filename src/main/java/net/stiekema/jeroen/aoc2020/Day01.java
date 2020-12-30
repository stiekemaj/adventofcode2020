package net.stiekema.jeroen.aoc2020;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Day01 {

    public void execute() {
        InputStream in = getClass().getResourceAsStream("/day1_1.txt");
        List<Integer> lines = new BufferedReader(new InputStreamReader(in)).lines()
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        System.out.println("Result part 1: " + findSum2020(lines, 2));
        System.out.println("Result part 2: " + findSum2020(lines, 3));
    }

    private Integer findSum2020(List<Integer> lines, int numberOfElements) {
        Optional<Integer> result = Collections.permutations(lines, numberOfElements).stream()
                .filter(group -> group.stream().reduce(0, Integer::sum) == 2020)
                .map(group -> group.stream().reduce(1, (a, b) -> a * b))
                .findFirst();
        return result.orElseThrow(() -> new RuntimeException("No result found"));
    }

    public static void main(String[] args) {
        new Day01().execute();
    }
}
