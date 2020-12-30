package net.stiekema.jeroen.aoc2020;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Day07 {
    public void execute() {
        InputStream in = getClass().getResourceAsStream("/day7_1.txt");
        List<String> lines = new BufferedReader(new InputStreamReader(in)).lines()
                .collect(Collectors.toList());

        Map<String, Bag> bags = new HashMap<>();
        lines.stream()
                .map(str -> str.replaceAll("\\.?$", ""))
                .forEach(line -> putToMap(line, bags));

        Bag shinyGoldBag = bags.get("shiny gold bag");

        long nrOfBagsWithShinyGoldBag = bags.entrySet()
                .stream()
                .filter(t -> t.getValue().contains(shinyGoldBag))
                .count();

        System.out.println("Part 1: " + nrOfBagsWithShinyGoldBag);
        System.out.println("Part 2: " + shinyGoldBag.getNumberOfBags());
    }

    private void putToMap(String line, Map<String, Bag> bags) {
        String[] split = line.split("contain");

        String name = split[0].trim().replace("bags", "bag");
        Bag bag = bags.getOrDefault(name, new Bag(name));
        bags.put(name, bag);

        if (split[1].trim().equals("no other bags")) {
            return;
        }

        Arrays.stream(split[1].split(","))
                .map(String::trim)
                .forEach(s -> {
                    int amount = Integer.parseInt(s.substring(0, s.indexOf(' ')).trim());
                    String bagName = s.substring(s.indexOf(' ')).trim().replace("bags", "bag");
                    Bag containingBag = bags.getOrDefault(bagName, new Bag(bagName));
                    bags.put(bagName, containingBag);
                    bag.addContent(containingBag, amount);
                });
    }

    private static class Bag {
        private String name;
        private Map<Bag, Integer> contents;

        public Bag(String name) {
            this.name = name;
            this.contents = new HashMap<>();
        }

        public void addContent(Bag bag, int amount) {
            contents.put(bag, amount);
        }

        public int getNumberOfBags() {
            int number = 0;
            for (Map.Entry<Bag, Integer> content : contents.entrySet()) {
                number += content.getValue();
                number += (content.getValue() * content.getKey().getNumberOfBags());
            }
            return number;
        }

        public boolean contains(Bag bag) {
            boolean found = false;
            for (Map.Entry<Bag, Integer> content : contents.entrySet()) {
                if (content.getKey().equals(bag)) {
                    found = true;
                } else {
                    found = content.getKey().contains(bag);
                }

                if (found) {
                    break;
                }
            }

            return found;
        }

        @Override
        public String toString() {
            return "Bag{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        new Day07().execute();
    }
}
