package com.company;

import java.util.SortedSet;
import java.util.regex.Pattern;

class DaysOfMonth extends TimeUnit {

    private final Pattern VALUE_PATTERN = Pattern.compile("^([1-9]|[1-2][0-9]|3[0-1])$");
    private final Pattern INTERVAL_PATTERN = Pattern.compile("^(\\*(/([1-2]?[0-9]|3[0-1]))?)$");
    private final int minimumValue = 1;
    private final int range = 31;

    @Override
    public int index() {
        return 2;
    }

    @Override
    public String displayName() {
        return "day of month";
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
