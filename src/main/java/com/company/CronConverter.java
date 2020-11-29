package com.company;

import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.rightPad;

class CronConverter {

    protected static final String OUTPUT_VALUE_SEPARATOR = " ";

    private final TimeUnit[] timeUnits = {new Minutes(), new Hours(), new DaysOfMonth(), new Months(), new DayOfWeek()};

    List<String> convert(String[] rawInput) {
        Arrays.stream(timeUnits).forEach(unit -> unit.initialize(rawInput));
        final int displayNameLength = selectLongestDisplayName() + 2;
        return convertToReadable(rawInput, displayNameLength);
    }

    private int selectLongestDisplayName() {
        return Arrays.stream(timeUnits)
                .map(TimeUnit::displayName)
                .map(String::length)
                .reduce(Integer::max)
                .orElse(15);
    }

    private List<String> convertToReadable(String[] input, int displayNameFieldLength) {
        List<String> result = new ArrayList<>();
        for (TimeUnit timeUnit : timeUnits) {
            result.add(rightPad(timeUnit.displayName(), displayNameFieldLength) + buildValues(timeUnit.values(input[timeUnit.index()])));
        }
        result.add(rightPad("command", displayNameFieldLength) + buildCommand(input));
        return result;
    }

    private String buildValues(SortedSet<Integer> values) {
        return values.stream()
                .map(Objects::toString)
                .collect(Collectors.joining(OUTPUT_VALUE_SEPARATOR));
    }

    private String buildCommand(String[] input) {
        return String.join(" ", Arrays.copyOfRange(input, 5, input.length));
    }

}
