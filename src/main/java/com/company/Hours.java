package com.company;

import java.util.SortedSet;
import java.util.regex.Pattern;

class Hours extends TimeUnit {

    private final Pattern VALUE_PATTERN = Pattern.compile("^(1?[0-9]|2[0-3])$");
    private final Pattern INTERVAL_PATTERN = Pattern.compile("^(\\*(/(1?[0-9]|2[0-3]))?)$");
    private final int index = 1;
    private final int minimumValue = 0;
    private final int range = 23;
    private String value;

    @Override
    public String displayName() {
        return "hour";
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
