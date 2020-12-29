package net.stiekema.jeroen.aoc2020;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day2 {
    public void execute() {
        InputStream in = getClass().getResourceAsStream("/day2_1.txt");
        List<String> lines = new BufferedReader(new InputStreamReader(in)).lines().collect(Collectors.toList());

        PasswordEntryFactory factory = new PasswordEntryLowHighPolicyFactory();
        List<PasswordEntry> passwordEntriesPart1 = lines.stream().map(factory::create).collect(Collectors.toList());
        System.out.println("Number of valid passwords: " + getNrOfValidPasswords(passwordEntriesPart1, isValidPassword()));

        factory = new PasswordEntryExactlyOnePolicyFactory();
        List<PasswordEntry> passwordEntriesPart2 = lines.stream().map(factory::create).collect(Collectors.toList());
        System.out.println("Number of valid passwords: " + getNrOfValidPasswords(passwordEntriesPart2, isValidPassword()));
    }

    private long getNrOfValidPasswords(List<PasswordEntry> passwordEntries, Predicate<PasswordEntry> validationPredicate) {
        return passwordEntries.stream()
                .filter(validationPredicate)
                .count();
    }

    private Predicate<PasswordEntry> isValidPassword() {
        return (passwordEntry -> passwordEntry.getPasswordPolicy().isValid(passwordEntry.getPassword()));
    }

    private static abstract class PasswordEntryFactory {
        private static final Pattern PATTERN = Pattern.compile("^([1-9][0-9]*?)-([1-9][0-9]*?)\\s([a-z]):\\s([a-z]+?)$");

        public PasswordEntry create(String line) {
            Matcher matcher = PATTERN.matcher(line);
            if (!matcher.find()) {
                throw new IllegalArgumentException("Illegal line: " + line);
            }

            PasswordPolicy passwordPolicy = createPasswordPolicy(
                    matcher.group(3).charAt(0),
                    Integer.parseInt(matcher.group(1)),
                    Integer.parseInt(matcher.group(2))
            );
            String password = matcher.group(4);
            return new PasswordEntry(password, passwordPolicy);
        }

        protected abstract PasswordPolicy createPasswordPolicy(char character, int a, int b);
    }

    private static class PasswordEntryLowHighPolicyFactory extends PasswordEntryFactory {
        @Override
        protected PasswordPolicy createPasswordPolicy(char character, int a, int b) {
            return new LowHighPolicy(character, a, b);
        }
    }

    private static class PasswordEntryExactlyOnePolicyFactory extends PasswordEntryFactory {
        @Override
        protected PasswordPolicy createPasswordPolicy(char character, int a, int b) {
            return new ExactlyOnePolicy(character, a, b);
        }
    }

    private static class PasswordEntry {
        private final PasswordPolicy passwordPolicy;
        private final String password;

        private PasswordEntry(String password, PasswordPolicy passwordPolicy) {
            this.password = password;
            this.passwordPolicy = passwordPolicy;
        }

        public PasswordPolicy getPasswordPolicy() {
            return passwordPolicy;
        }

        public String getPassword() {
            return password;
        }
    }

    private interface PasswordPolicy {
        boolean isValid(String password);
    }

    private static class LowHighPolicy implements PasswordPolicy {
        private final char letter;
        private final int lowestNumber;
        private final int highestNumber;

        public LowHighPolicy(char letter, int lowestNumber, int highestNumber) {
            this.letter = letter;
            this.lowestNumber = lowestNumber;
            this.highestNumber = highestNumber;
        }

        @Override
        public boolean isValid(String password) {
            char[] chars = password.toCharArray();
            long nrOfOccurrences = IntStream.range(0, chars.length).mapToObj(i -> chars[i])
                    .filter(c -> letter == c)
                    .count();
            return nrOfOccurrences >= lowestNumber && nrOfOccurrences <= highestNumber;
        }
    }

    private static class ExactlyOnePolicy implements PasswordPolicy {
        private final char letter;
        private final int firstIndex;
        private final int secondIndex;

        public ExactlyOnePolicy(char letter, int firstIndex, int secondIndex) {
            this.letter = letter;
            this.firstIndex = firstIndex;
            this.secondIndex = secondIndex;
        }

        @Override
        public boolean isValid(String password) {
            char[] chars = password.toCharArray();
            return chars[firstIndex - 1] == letter ^ chars[secondIndex - 1] == letter;
        }
    }

    public static void main(String[] args) {
        new Day2().execute();
    }
}
