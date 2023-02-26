package com.example.lab6_2;

import java.util.List;

public interface PropertyAssessmentDAO {
    PropertyAssessment getByAccountNumber(int accountNumber);
    List<PropertyAssessment> getByNeighbourhood(String neighbourhood);
    List<PropertyAssessment> getByAssessmentClass(String assessmentClass);
    List<PropertyAssessment> getAssessments();
    List<PropertyAssessment> getAssessments(int offset);
}
