package com.example.lab6_2;

import java.util.Objects;

public class AssessmentClass {

    private final String assessmentClassName;
    private final int assessmentClassPercentage;
    public AssessmentClass(String assessmentClassName, int assessmentClassPercentage){
        this.assessmentClassName = assessmentClassName;
        this.assessmentClassPercentage = assessmentClassPercentage;
    }

    public String getAssessmentClassName() {
        return assessmentClassName;
    }

    public int getAssessmentClassPercentage() {
        return assessmentClassPercentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssessmentClass that = (AssessmentClass) o;
        return assessmentClassPercentage == that.assessmentClassPercentage && Objects.equals(assessmentClassName, that.assessmentClassName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assessmentClassName, assessmentClassPercentage);
    }

    @Override
    public String toString() {
        return "AssessmentClass{" +
                "assessmentClass='" + assessmentClassName + '\'' +
                ", assessmentClassPercentage=" + assessmentClassPercentage +
                '}';
    }
}
