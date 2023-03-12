package com.example.lab6_2;

public class Filter {
    private final String address;
    private final String neighbourhood;
    private final String assessmentClass;
    private final int minimumAssessedValue;
    private final int maximumAssessedValue;

    public static class Builder {
        private String address = "";
        private String neighbourhood = "";
        private String assessmentClass = "";
        private int minimumAssessedValue = -1;
        private int maximumAssessedValue = -1;

        public Builder address(String val) {
            address = val; return this;
        }

        public Builder neighbourhood(String val) {
            neighbourhood = val; return this;
        }

        public Builder assessmentClass(String val) {
            assessmentClass = val; return this;
        }

        public Builder minimumAssessedValue(int val) {
            minimumAssessedValue = val; return this;
        }

        public Builder maximumAssessedValue(int val) {
            maximumAssessedValue = val; return this;
        }

        public Filter build() {
            return new Filter(this);
        }
    }

    private Filter(Builder builder) {
        address = builder.address;
        neighbourhood = builder.neighbourhood;
        assessmentClass = builder.assessmentClass;
        minimumAssessedValue = builder.minimumAssessedValue;
        maximumAssessedValue = builder.maximumAssessedValue;
    }

    public String getAddress() {
        return address;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public String getAssessmentClass() {
        return assessmentClass;
    }

    public int getMinimumAssessedValue() {
        return minimumAssessedValue;
    }

    public int getMaximumAssessedValue() {
        return maximumAssessedValue;
    }
}
