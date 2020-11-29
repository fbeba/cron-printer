package com.company;

import java.util.SortedSet;
import java.util.regex.Pattern;

class Months extends TimeUnit {

    private final Pattern VALUE_PATTERN = Pattern.compile("^([1-9]|1[0-2])$");
    private final Pattern INTERVAL_PATTERN = Pattern.compile("^(\\*(/([1-9]|1[0-2]))?)$");
    private final int index = 3;
    private final int minimumValue = 1;
    private final int range = 12;
    private String value;

    @Override
    public String displayName() {
        return "month";
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