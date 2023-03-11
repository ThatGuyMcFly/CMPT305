package com.example.lab6_2;

import java.util.List;
import java.util.Set;

public interface PropertyAssessmentDAO {
    PropertyAssessment getByAccountNumber(int accountNumber);
    List<PropertyAssessment> getByNeighbourhood(String neighbourhood);
    List<PropertyAssessment> getByAddress(String address);
    List<PropertyAssessment> getByAssessmentClass(String assessmentClass);
    List<PropertyAssessment> getAssessments();
    Set<String> getAssessmentClasses();
    List<PropertyAssessment> getByAssessedValueMinimum(int min);
    List<PropertyAssessment> getByAssessedValueMaximum(int max);
}
