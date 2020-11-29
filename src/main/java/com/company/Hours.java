package com.company;

import java.util.SortedSet;
import java.util.regex.Pattern;

class Hours extends TimeUnit {

    private final Pattern VALUE_PATTERN = Pattern.compile("^(1?[0-9]|2[0-3])$");
    private final Pattern INTERVAL_PATTERN = Pattern.compile("^(\\*(/(1?[0-9]|2[0-3]))?)$");
    private final int minimumValue = 0;
    private final int range = 23;

    @Override
    public int index() {
        return 1;
    }

    @Override
    public String displayName() {
        return "hour";
    }

    @Override
    public SortedSet<Integer> values(String input) {
        return extractValues(input, minimumValue, range);
    }

    @Override
    public boolean isValid(String input) {
        return checkValidity(input, INTERVAL_PATTERN, VALUE_PATTERN);
    }
}
