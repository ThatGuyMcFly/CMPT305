package com.example.lab6_2;

public enum Source {

    CSV("CSV File"),
    API("Edmonton's Open Data Portal");

    private final String source;
    private Source(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }
}
