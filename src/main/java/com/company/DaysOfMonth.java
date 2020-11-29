package com.company;

import java.util.SortedSet;
import java.util.regex.Pattern;

class DaysOfMonth extends TimeUnit {

    private final Pattern VALUE_PATTERN = Pattern.compile("^([1-9]|[1-2][0-9]|3[0-1])$");
    private final Pattern INTERVAL_PATTERN = Pattern.compile("^(\\*(/([1-2]?[0-9]|3[0-1]))?)$");
    private final int index = 2;
    private final int minimumValue = 1;
    private final int range = 31;
    private String value;

    @Override
    public String displayName() {
        return "day of month";
    }

    @Override
    public SortedSet<Integer> values() {
        return extractValues(value, minimumValue, range);
    }

    @Override
    void initialize(String[] rawInput) {
        value = rawInput[index];
        assert checkValidity(value, INTERVAL_PATTERN, VALUE_PATTERN);
    }
}
