package net.stiekema.jeroen.aoc2020;

import java.util.ArrayList;
import java.util.List;

public final class Collections {
    private Collections() {
    }

    public static <T> List<List<T>> permutations(List<T> input, int size) {
        List<List<T>> result = new ArrayList<>();
        permutations(input, new ArrayList<>(), size, result);
        return result;
    }

    private static <T> void permutations(List<T> input, List<T> current, int size, List<List<T>> results) {
        if (size == 0) {
            return;
        }

        if (size == 1) {
            input.forEach(element -> results.add(listOf(current, element)));
        } else {
            for (int i = 0; i < input.size(); i++) {
                List<T> subList = input.subList(i + 1, input.size());
                permutations(subList, listOf(current, input.get(i)), size - 1, results);
            }
        }
    }

    private static <T> List<T> listOf(List<T> list, T element) {
        List<T> returnList = new ArrayList<>(list);
        returnList.add(element);
        return returnList;
    }
}
