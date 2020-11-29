package com.company;

import java.util.SortedSet;
import java.util.regex.Pattern;

class DayOfWeek extends TimeUnit {

    private final Pattern VALUE_PATTERN = Pattern.compile("^[0-6]$");
    private final Pattern INTERVAL_PATTERN = Pattern.compile("^(\\*(/([0-6]))?)$");
    private final int minimumValue = 0;
    private final int range = 6;
    private String value;

    @Override
    public int index() {
        return 4;
    }

    @Override
    public String displayName() {
        return "day of week";
    }

    @Override
    public SortedSet<Integer> values(String input) {
        return extractValues(input, minimumValue, range);
    }

    @Override
    void initialize(String[] rawInput) {
        value = rawInput[index()];
        assert checkValidity(value, INTERVAL_PATTERN, VALUE_PATTERN);
    }
}
