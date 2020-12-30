package net.stiekema.jeroen.aoc2020;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

public class Day05 {
    public void execute() {
        InputStream in = getClass().getResourceAsStream("/day5_1.txt");
        List<String> lines = new BufferedReader(new InputStreamReader(in)).lines()
                .collect(Collectors.toList());

        Map<String, Integer> boardingPassToSeatNumberMap = lines.stream()
                .collect(toMap(line -> line, line -> getRow(line) * 8 + getSeat(line)));

        Optional<Map.Entry<String, Integer>> max = boardingPassToSeatNumberMap
                .entrySet()
                .stream()
                .max(comparingByValue());

        System.out.println("Part 1: " + max.get().getValue());


        LinkedHashMap<String, Integer> sortedBoardingPassToSeatNumberMap = boardingPassToSeatNumberMap
                .entrySet()
                .stream()
                .sorted(comparingByValue())
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

        System.out.println("Part 2: " + findMissingSeatNumber(new ArrayList<>(sortedBoardingPassToSeatNumberMap.values())));
    }

    private int findMissingSeatNumber(ArrayList<Integer> seatNumbers) {
        Integer lastSeatNr = null;
        for (Integer seatNumber : seatNumbers) {
            if (lastSeatNr != null && seatNumber - lastSeatNr == 2) {
                return lastSeatNr + 1;
            }
            lastSeatNr = seatNumber;
        }
        return -1;
    }

    private int getRow(String boardingPass) {
        char[] chars = boardingPass.substring(0, 7).toCharArray();
        List<Character> charList = IntStream.range(0, chars.length).mapToObj(i -> chars[i]).collect(Collectors.toList());
        int row = getRow(charList, 0, 127);
        return row;
    }

    private int getRow(List<Character> characters, int partitionStart, int partitionEnd) {
        if (partitionStart == partitionEnd) {
            return partitionStart;
        }

        Character character = characters.remove(0);
        if (character == 'F') {
            return getRow(characters, partitionStart, partitionEnd - 1 - (partitionEnd - partitionStart) / 2);
        } else {
            return getRow(characters, 1 + partitionStart + (partitionEnd - partitionStart) / 2, partitionEnd);
        }
    }

    private int getSeat(String boardingPass) {
        char[] chars = boardingPass.substring(7).toCharArray();
        List<Character> charList = IntStream.range(0, chars.length).mapToObj(i -> chars[i]).collect(Collectors.toList());
        int seat = getSeat(charList, 0, 7);
        return seat;
    }

    private int getSeat(List<Character> characters, int partitionStart, int partitionEnd) {
        if (partitionStart == partitionEnd) {
            return partitionStart;
        }

        Character character = characters.remove(0);
        if (character == 'L') {
            return getSeat(characters, partitionStart, partitionEnd - 1 - (partitionEnd - partitionStart) / 2);
        } else {
            return getSeat(characters, 1 + partitionStart + (partitionEnd - partitionStart) / 2, partitionEnd);
        }
    }

    public static void main(String[] args) {
        new Day05().execute();
    }
}
