package com.company;

import java.util.SortedSet;
import java.util.regex.Pattern;

class Minutes extends TimeUnit {

    private final Pattern VALUE_PATTERN = Pattern.compile("^([1-5]?[0-9])$");
    private final Pattern INTERVAL_PATTERN = Pattern.compile("^(\\*(/[1-5]?[0-9])?)$");
    private final int minimumValue = 0;
    private final int range = 59;
    private String value;

    @Override
    public int index() {
        return 0;
    }

    @Override
    public String displayName() {
        return "minute";
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

