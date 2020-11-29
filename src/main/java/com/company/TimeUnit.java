package com.company;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;

abstract class TimeUnit {

    protected static final char ASTERISK = '*';
    protected static final String VALUE_DELIMITER = ",";
    protected static final String INTERVAL_DELIMITER = "/";
    protected static final String RANGE_DELIMITER = "-";

    private final TreeSet<Integer> timeValues = new TreeSet<>();

    abstract int index();

    abstract String displayName();

    abstract SortedSet<Integer> values(String input);

    abstract void initialize(String[] rawInput);

    protected boolean checkValidity(String input, Pattern intervalPattern, Pattern valuePattern) {
        boolean isValidIntervalPattern = intervalPattern.matcher(input).matches();
        String[] parts = input.split(VALUE_DELIMITER);
        for (String part : parts) {
            final boolean valid = valuePattern.matcher(part).matches()
                    || isValueRange(part, valuePattern)
                    || isValidIntervalPattern;
            if (!valid) {
                throw new InvalidInputException(this.displayName(), String.join(",", parts));
            }
        }
        return true;
    }

    private boolean isValueRange(String input, Pattern valuePattern) {
        String[] tokens = input.split(RANGE_DELIMITER);
        return tokens.length == 2
                && valuePattern.matcher(tokens[0]).matches()
                && valuePattern.matcher(tokens[1]).matches()
                && Integer.parseInt(tokens[0]) <= Integer.parseInt(tokens[1]);
    }

    protected SortedSet<Integer> extractValues(String input, int minimumValue, int range) {
        if (input.charAt(0) == ASTERISK) {
            provideIntervalValues(input, minimumValue, range);
        } else {
            provideSelectedValues(input);
        }
        return timeValues;
    }

    protected void provideIntervalValues(String input, Integer minimum, int range) {
        final int interval = determineInterval(input);
        int value = minimum;
        timeValues.add(value);
        while (value + interval <= range) {
            value += interval;
            timeValues.add(value);
        }
    }

    protected int determineInterval(String input) {
        final String[] intervalParameters = input.split(INTERVAL_DELIMITER);
        return intervalParameters.length == 2 ? Integer.parseInt(intervalParameters[1]) : 1;
    }

    protected void provideSelectedValues(String input) {
        Arrays.stream(input.split(VALUE_DELIMITER))
                .forEach(this::provideValuesForRange);
    }

    private void provideValuesForRange(String input) {
        String[] tokens = input.split(RANGE_DELIMITER);
        int beginning = Integer.parseInt(tokens[0]);
        int end = tokens.length == 2 ? Integer.parseInt(tokens[1]) : beginning;
        timeValues.add(beginning);
        for (int i = beginning + 1; i <= end; i++) {
            timeValues.add(i);
        }
    }
}
