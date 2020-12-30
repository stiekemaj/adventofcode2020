package net.stiekema.jeroen.aoc2020;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day04Test {

    @Test
    public void testIsValidPart2_valid() {
        assertTrue(new Day04.Passport("1920", "2010", "2020", "150cm", "#000000", "amb", "000000000", null).isValidPart2());
        assertTrue(new Day04.Passport("1921", "2011", "2021", "151cm", "#999999", "amb", "000000001", null).isValidPart2());
        assertTrue(new Day04.Passport("2002", "2019", "2029", "192cm", "#aaaaaa", "oth", "999999998", null).isValidPart2());
        assertTrue(new Day04.Passport("2002", "2019", "2029", "59in", "#aaaaaa", "oth", "999999998", null).isValidPart2());
        assertTrue(new Day04.Passport("2002", "2019", "2029", "76in", "#aaaaaa", "oth", "999999998", null).isValidPart2());
        assertTrue(new Day04.Passport("2001", "2020", "2030", "193cm", "#ffffff", "oth", "999999999", null).isValidPart2());
    }

    @Test
    public void testIsValidPart2_invalidByr() {
        assertFalse(new Day04.Passport("1919", "2010", "2020", "150cm", "#000000", "amb", "000000000", null).isValidPart2());
        assertFalse(new Day04.Passport("2003", "2020", "2030", "193cm", "#ffffff", "oth", "999999999", null).isValidPart2());
    }

    @Test
    public void testIsValidPart2_invalidIyr() {
        assertFalse(new Day04.Passport("1920", "2009", "2020", "150cm", "#000000", "amb", "000000000", null).isValidPart2());
        assertFalse(new Day04.Passport("1920", "2021", "2030", "193cm", "#ffffff", "oth", "999999999", null).isValidPart2());
    }

    @Test
    public void testIsValidPart2_invalidEyr() {
        assertFalse(new Day04.Passport("1920", "2010", "2019", "150cm", "#000000", "amb", "000000000", null).isValidPart2());
        assertFalse(new Day04.Passport("1920", "2010", "2031", "193cm", "#ffffff", "oth", "999999999", null).isValidPart2());
    }

    @Test
    public void testIsValidPart2_invalidHgt() {
        assertFalse(new Day04.Passport("1920", "2010", "2020", "cm150", "#000000", "amb", "000000000", null).isValidPart2());
        assertFalse(new Day04.Passport("1920", "2010", "2020", "150", "#000000", "amb", "000000000", null).isValidPart2());
        assertFalse(new Day04.Passport("1920", "2010", "2020", "149cm", "#000000", "amb", "000000000", null).isValidPart2());
        assertFalse(new Day04.Passport("1920", "2010", "2020", "194cm", "#ffffff", "oth", "999999999", null).isValidPart2());
        assertFalse(new Day04.Passport("1920", "2010", "2020", "58in", "#aaaaaa", "oth", "999999998", null).isValidPart2());
        assertFalse(new Day04.Passport("1920", "2010", "2020", "77in", "#aaaaaa", "oth", "999999998", null).isValidPart2());
        assertFalse(new Day04.Passport("1920", "2010", "2020", "190in", "#aaaaaa", "oth", "999999998", null).isValidPart2());
    }


    @Test
    public void testIsValidPart2_invalidEcl() {
        assertFalse(new Day04.Passport("1920", "2010", "2020", "150cm", "#000000", "ambu", "000000000", null).isValidPart2());
        assertFalse(new Day04.Passport("1920", "2010", "2020", "150cm", "#ffffff", "other", "999999999", null).isValidPart2());
    }

    @Test
    public void testIsValidPart2_invalidPid() {
        assertFalse(new Day04.Passport("1920", "2010", "2020", "150cm", "#000000", "amb", "00000000", null).isValidPart2());
        assertFalse(new Day04.Passport("1920", "2010", "2020", "150cm", "#ffffff", "oth", "9999999999", null).isValidPart2());
    }

}
