package com.company;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CronConverterTest {

    private final CronConverter printer = new CronConverter();

    @Test
    public void shouldRecognizeSimpleInput() {
        String[] input = {"1", "2", "3", "4", "5", "/user/runMeNever"};

        List<String> result = printer.convert(input);

        assertTrue(result.contains("minute        1"));
        assertTrue(result.contains("hour          2"));
        assertTrue(result.contains("day of month  3"));
        assertTrue(result.contains("month         4"));
        assertTrue(result.contains("day of week   5"));
        assertTrue(result.contains("command       /user/runMeNever"));
    }

    @Test
    public void shouldRecognizeMultipleMinutesInput() {
        String[] input = {"1,10,20", "2", "3", "4", "5", "/user/runMeNever"};

        List<String> result = printer.convert(input);

        assertTrue(result.contains("minute        1 10 20"));
    }

    @Test
    public void shouldRejectMinutesValueOutOfRange() {
        String[] input = {"60", "2", "3", "4", "5", "/user/runMeNever"};

        Throwable exception = assertThrows(InvalidInputException.class, () -> printer.convert(input));
        assertEquals(exception.getMessage(), "Invalid input for minute: '60'");
    }

    @Test
    public void shouldRejectInvalidRangeInput() {
        String[] input = {"10", "2-", "3", "4", "5", "/user/runMeNever"};

        Throwable exception = assertThrows(InvalidInputException.class, () -> printer.convert(input));
        assertEquals(exception.getMessage(), "Invalid input for hour: '2-'");
    }

    @Test
    public void shouldComputeIntervalValuesForMinutes() {
        String[] input = {"*/15", "2", "3", "4", "5", "/user/runMeNever"};

        List<String> result = printer.convert(input);

        assertTrue(result.contains("minute        0 15 30 45"));
    }

    @Test
    public void shouldRecognizeSingleHoursInput() {
        String[] input = {"1", "2", "3", "4", "5", "/user/runMeNever"};

        List<String> result = printer.convert(input);

        assertTrue(result.contains("hour          2"));
    }

    @Test
    public void shouldComputeIntervalValuesForHours() {
        String[] input = {"*", "*/4", "3", "4", "5", "/user/runMeNever"};

        List<String> result = printer.convert(input);

        assertTrue(result.contains("hour          0 4 8 12 16 20"));
    }

    @Test
    public void shouldComputeIntervalValuesForDayOfMonth() {
        String[] input = {"*", "*/4", "*/13", "4", "5", "/user/runMeNever"};

        List<String> result = printer.convert(input);

        assertTrue(result.contains("day of month  1 14 27"));
    }

    @Test
    public void shouldComputeRangeOfValues() {
        String[] input = {"1-3,5-7", "1-4", "13-17", "4-6", "4-5", "/user/runMeNever"};

        List<String> result = printer.convert(input);

        assertTrue(result.contains("minute        1 2 3 5 6 7"));
        assertTrue(result.contains("hour          1 2 3 4"));
        assertTrue(result.contains("day of month  13 14 15 16 17"));
        assertTrue(result.contains("month         4 5 6"));
        assertTrue(result.contains("day of week   4 5"));
        assertTrue(result.contains("command       /user/runMeNever"));
    }

    @Test
    public void shouldComputeSortedSetOfValuesFromUnorderedAndRedundantInput() {
        String[] input = {"9-12,1-3,5-7,1-2", "1-4", "13-17", "4-6", "4-5", "/user/runMeNever"};

        List<String> result = printer.convert(input);

        assertTrue(result.contains("minute        1 2 3 5 6 7 9 10 11 12"));
        assertTrue(result.contains("hour          1 2 3 4"));
        assertTrue(result.contains("day of month  13 14 15 16 17"));
        assertTrue(result.contains("month         4 5 6"));
        assertTrue(result.contains("day of week   4 5"));
        assertTrue(result.contains("command       /user/runMeNever"));
    }

    @Test
    public void shouldRejectMixedInput() {
        String[] input = {"*/3,4", "2", "3", "4", "5", "/user/runMeNever"};

        Throwable exception = assertThrows(InvalidInputException.class, () -> printer.convert(input));
        assertEquals(exception.getMessage(), "Invalid input for minute: '*/3,4'");
    }

    @Test
    public void shouldComputeIntervalValuesForMonth() {
        String[] input = {"*", "*/4", "*/13", "*/4", "5", "/user/runMeNever"};

        List<String> result = printer.convert(input);

        assertTrue(result.contains("month         1 5 9"));
    }

    @Test
    public void shouldAcceptBorderTopValueForMonth() {
        String[] input = {"*", "*/4", "31", "*/4", "5", "/user/runMeNever"};

        List<String> result = printer.convert(input);

        assertTrue(result.contains("day of month  31"));
    }

    @Test
    public void shouldComputeIntervalValuesForEachMonth() {
        String[] input = {"*", "*/4", "*/13", "*", "5", "/user/runMeNever"};

        List<String> result = printer.convert(input);

        assertTrue(result.contains("month         1 2 3 4 5 6 7 8 9 10 11 12"));
    }

    @Test
    public void shouldAcceptCommandWithParameters() {
        String[] input = {"1,10,20", "2", "3", "4", "5", "/user/runMeNever", "-r", "now"};

        List<String> result = printer.convert(input);

        assertTrue(result.contains("command       /user/runMeNever -r now"));
    }
}