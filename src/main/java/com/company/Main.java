package com.company;

public class Main {

    public static void main(String[] args) {
        new CronConverter()
                .convert(args[0].split(" "))
                .forEach(System.out::println);
    }
}
