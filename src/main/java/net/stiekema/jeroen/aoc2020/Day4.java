package net.stiekema.jeroen.aoc2020;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day4 {
    public void execute() {
        InputStream in = getClass().getResourceAsStream("/day4_1.txt");
        List<String> lines = new BufferedReader(new InputStreamReader(in)).lines()
                .collect(Collectors.toList());

        List<Passport> passports = parseToPassports(lines);

        System.out.println("Part 1: " + passports.stream().filter(Passport::isValidPart1).count());
        System.out.println("Part 2: " + passports.stream().filter(Passport::isValidPart2).count());
    }

    private List<Passport> parseToPassports(List<String> lines) {
        List<Passport> passports = new ArrayList<>();
        PassportBuilder builder = new PassportBuilder();
        for (String line : lines) {
            if (StringUtils.isEmpty(line)) {
                passports.add(builder.build());
                builder = new PassportBuilder();
            }
            parsePassportLine(line, builder);
        }
        passports.add(builder.build());
        return passports;
    }

    private void parsePassportLine(String line, PassportBuilder builder) {
        String[] fields = line.split(" ");
        Arrays.stream(fields).forEach(field -> {
            String[] keyValue = field.split(":");
            if (keyValue[0].equals("byr")) {
                builder.setByr(keyValue[1]);
            } else if (keyValue[0].equals("iyr")) {
                builder.setIyr(keyValue[1]);
            } else if (keyValue[0].equals("eyr")) {
                builder.setEyr(keyValue[1]);
            } else if (keyValue[0].equals("hgt")) {
                builder.setHgt(keyValue[1]);
            } else if (keyValue[0].equals("hcl")) {
                builder.setHcl(keyValue[1]);
            } else if (keyValue[0].equals("ecl")) {
                builder.setEcl(keyValue[1]);
            } else if (keyValue[0].equals("pid")) {
                builder.setPid(keyValue[1]);
            } else if (keyValue[0].equals("cid")) {
                builder.setCid(keyValue[1]);
            }
        });
    }

    private static class PassportBuilder {
        private String byr;
        private String iyr;
        private String eyr;
        private String hgt;
        private String hcl;
        private String ecl;
        private String pid;
        private String cid;

        public void setByr(String byr) {
            this.byr = byr;
        }

        public void setIyr(String iyr) {
            this.iyr = iyr;
        }

        public void setEyr(String eyr) {
            this.eyr = eyr;
        }

        public void setHgt(String hgt) {
            this.hgt = hgt;
        }

        public void setHcl(String hcl) {
            this.hcl = hcl;
        }

        public void setEcl(String ecl) {
            this.ecl = ecl;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public Passport build() {
            return new Passport(byr, iyr, eyr, hgt, hcl, ecl, pid, cid);
        }
    }

    static class Passport {
        private final String byr;
        private final String iyr;
        private final String eyr;
        private final String hgt;
        private final String hcl;
        private final String ecl;
        private final String pid;
        private final String cid;

        public Passport(String byr, String iyr, String eyr, String hgt, String hcl, String ecl, String pid, String cid) {
            this.byr = byr;
            this.iyr = iyr;
            this.eyr = eyr;
            this.hgt = hgt;
            this.hcl = hcl;
            this.ecl = ecl;
            this.pid = pid;
            this.cid = cid;
        }

        public boolean isValidPart1() {
            return StringUtils.isNotEmpty(byr)
                    && StringUtils.isNotEmpty(iyr)
                    && StringUtils.isNotEmpty(eyr)
                    && StringUtils.isNotEmpty(hgt)
                    && StringUtils.isNotEmpty(hcl)
                    && StringUtils.isNotEmpty(ecl)
                    && StringUtils.isNotEmpty(pid);
        }

        public boolean isValidPart2() {
            return isValidPart1()
                    && isNumbericBetweenInclusive(byr, 1920, 2002)
                    && isNumbericBetweenInclusive(iyr, 2010, 2020)
                    && isNumbericBetweenInclusive(eyr, 2020, 2030)
                    && isValidHeight(hgt)
                    && matchRegex(hcl, "^#([0-9]{6}|[a-f]{6})$")
                    && matchRegex(ecl, "^(amb|blu|brn|gry|grn|hzl|oth)$")
                    && matchRegex(pid, "^[0-9]{9}$");
        }

        private boolean isNumbericBetweenInclusive(String value, int min, int max) {
            try {
                int i = Integer.parseInt(value);
                boolean returnValue = i >= min && i <= max;
                return returnValue;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        private boolean isValidHeight(String value) {
            if (!matchRegex(value, "^[1-9][0-9]*(cm|in)$")) {
                return false;
            }
            if (value.endsWith("cm")) {
                boolean returnValue = isNumbericBetweenInclusive(value.substring(0, value.indexOf("cm")), 150, 193);
                return returnValue;
            } else if (value.endsWith("in")) {
                boolean returnValue = isNumbericBetweenInclusive(value.substring(0, value.indexOf("in")), 59, 76);
                return returnValue;
            } else {
                return false;
            }
        }

        private boolean matchRegex(String value, String regex) {
            Pattern pattern = Pattern.compile(regex);
            boolean returnValue = pattern.matcher(value).find();
            return returnValue;
        }
    }

    public static void main(String[] args) {
        new Day4().execute();
    }
}
