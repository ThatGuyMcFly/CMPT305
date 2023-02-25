package com.lab6.propertyassessmentapplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AssessmentClass {

    List<String> assessmentClasses = new ArrayList<>();
    List<Integer> assessmentClassPercentages = new ArrayList<>();
    public AssessmentClass(List<String> assessmentClasses, List<Integer> assessmentClassPercentages){
        this.assessmentClasses = assessmentClasses;
        this.assessmentClassPercentages = assessmentClassPercentages;
    }

    public List<String> getAssessmentClasses() {
        return assessmentClasses;
    }

    public List<Integer> getAssessmentClassPercentages() {
        return assessmentClassPercentages;
    }

}
