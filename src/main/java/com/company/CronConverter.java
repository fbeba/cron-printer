package com.company;

import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.rightPad;

class CronConverter {

    protected static final String OUTPUT_VALUE_SEPARATOR = " ";
    protected static final int NAME_AND_VALUE_MINIMAL_SPACING = 2;
    protected static final int DEFAULT_NAME_LENGTH = 15;

    private final List<TimeUnit> timeUnits = List.of(new Minutes(), new Hours(), new DaysOfMonth(), new Months(), new DayOfWeek());

    List<String> convert(String[] rawInput) {
        timeUnits.forEach(unit -> unit.initialize(rawInput));
        final int displayNameLength = selectLongestDisplayName() + NAME_AND_VALUE_MINIMAL_SPACING;
        return convertToReadable(rawInput, displayNameLength);
    }

    private int selectLongestDisplayName() {
        return timeUnits.stream()
                .map(TimeUnit::displayName)
                .map(String::length)
                .reduce(Integer::max)
                .orElse(DEFAULT_NAME_LENGTH);
    }

    private List<String> convertToReadable(String[] input, int displayNameFieldLength) {
        List<String> result = timeUnits.stream()
                .map(unit -> rightPad(unit.displayName(), displayNameFieldLength) + buildValues(unit.values()))
                .collect(Collectors.toList());
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
