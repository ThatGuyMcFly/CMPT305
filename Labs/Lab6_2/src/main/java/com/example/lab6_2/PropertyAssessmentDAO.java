package com.example.lab6_2;

import java.util.List;
import java.util.Set;

public interface PropertyAssessmentDAO {
    PropertyAssessment getByAccountNumber(int accountNumber);
    List<PropertyAssessment> getByNeighbourhood(String neighbourhood);
    List<PropertyAssessment> getByAssessmentClass(String assessmentClass);
    List<PropertyAssessment> getAssessments();
    List<PropertyAssessment> getAssessments(int offset);
    Set<String> getAssessmentClasses();
    List<PropertyAssessment> getAssessedValueRange(int min, int max);
}
