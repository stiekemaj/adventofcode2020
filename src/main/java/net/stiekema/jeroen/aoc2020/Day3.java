package net.stiekema.jeroen.aoc2020;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day3 {

    public void execute() {
        InputStream in = getClass().getResourceAsStream("/day3_1.txt");
        List<String> lines = new BufferedReader(new InputStreamReader(in)).lines()
                .collect(Collectors.toList());

        System.out.println("Part 1: Number of encountered trees: " + numberOfTrees(lines, 3, 1));

        Long answerPart2 = Stream.of(
                numberOfTrees(lines, 1, 1),
                numberOfTrees(lines, 3, 1),
                numberOfTrees(lines, 5, 1),
                numberOfTrees(lines, 7, 1),
                numberOfTrees(lines, 1, 2)
        ).reduce(1L, (a, b) -> a * b);

        System.out.println("Part 2: " + answerPart2);
    }

    private long numberOfTrees(List<String> lines, int xSlope, int ySlope) {
        Matrix matrix = new Matrix(lines, xSlope, ySlope);
        List<Character> traversedSquares = new ArrayList<>();
        while (!matrix.isFinished()) {
            traversedSquares.add(matrix.nextJump());
        }
        return traversedSquares.stream().filter(c -> c == '#').count();
    }

    private static class Matrix {
        private final char[][] cells;
        private final int xSlope;
        private final int ySlope;

        private int x = 0;
        private int y = 0;

        Matrix(List<String> lines, int xSlope, int ySlope) {
            this.xSlope = xSlope;
            this.ySlope = ySlope;
            cells = new char[lines.size()][];
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                cells[i] = line.toCharArray();
            }
        }

        boolean isFinished() {
            return y >= cells.length - 1;
        }

        char nextJump() {
            if (isFinished()) {
                throw new RuntimeException("Matrix already finished");
            }

            x = (x + xSlope) % (cells[0].length);
            y = y + ySlope;
            return cells[y][x];
        }
    }

    public static void main(String[] args) {
        new Day3().execute();
    }
}
