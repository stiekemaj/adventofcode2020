package net.stiekema.jeroen.aoc2020;

import org.junit.jupiter.api.Test;

import java.util.List;

class CollectionsTest {

    @Test
    public void permutations_sizeOfTwo() {
        List<List<Integer>> result = Collections.permutations(List.of(1, 2, 3, 4), 2);
        System.out.println(result);
    }

    @Test
    public void permutations_sizeOfThree() {
        List<List<Character>> result = Collections.permutations(List.of('a', 'b', 'c', 'd', 'e', 'f'), 3);
        System.out.println(result);
    }

    @Test
    public void permutations_sizeOfFour() {
        List<List<Character>> result = Collections.permutations(List.of('a', 'b', 'c', 'd', 'e', 'f', 'g'), 4);
        System.out.println(result);
    }
}
