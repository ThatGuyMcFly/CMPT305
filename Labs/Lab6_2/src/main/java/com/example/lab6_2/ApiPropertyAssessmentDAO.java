package com.example.lab6_2;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class ApiPropertyAssessmentDAO implements PropertyAssessmentDAO{

    private final String endPoint;

    public ApiPropertyAssessmentDAO(){
        this("https://data.edmonton.ca/resource/q7d6-ambg.csv");
    }

    public ApiPropertyAssessmentDAO(String endPoint){
        this.endPoint = endPoint;
    }

    String getData(String queryAddition) {
        String url = endPoint + "?";// + URLEncoder.encode(queryAddition, StandardCharsets.UTF_8);

        if (queryAddition.contains(" ")){
            url = url + URLEncoder.encode(queryAddition, StandardCharsets.UTF_8);
        } else {
            url = url + queryAddition;
        }

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e){
            return "";
        }
    }

    @Override
    public PropertyAssessment getByAccountNumber(int accountNumber) {
        String response = getData("account_number = '" + accountNumber + "'");

        String[] propertyAssessmentStringArray = response.replaceAll("\"", "").split("\n");

        if (propertyAssessmentStringArray.length > 1){
            return new PropertyAssessment(propertyAssessmentStringArray[1].split(","));
        }

        return null;
    }

    private List<PropertyAssessment> processData(String data) {
        List<PropertyAssessment> propertyAssessmentList = new ArrayList<>();

        String[] propertyAssessmentStringArray = data.replaceAll("\"", "").split("\n");

        for (int i = 1; i < propertyAssessmentStringArray.length; i++) {
            propertyAssessmentList.add(new PropertyAssessment(propertyAssessmentStringArray[i].split(",")));
        }

        return propertyAssessmentList;
    }

    @Override
    public List<PropertyAssessment> getByNeighbourhood(String neighbourhood) {
        String response = getData("neighbourhood = '" + neighbourhood.toUpperCase() + "'");

        return processData(response);
    }

    @Override
    public List<PropertyAssessment> getByAssessmentClass(String assessmentClass) {
        String response = getData("$where=mill_class_1 = '" + assessmentClass.toUpperCase() + "' OR mill_class_2 = '" + assessmentClass.toUpperCase() + "'" + " OR mill_class_3 = '" + assessmentClass.toUpperCase() + "'");

        return processData(response);
    }

    @Override
    public List<PropertyAssessment> getAssessments() {
        return getAssessments(0);
    }

    @Override
    public List<PropertyAssessment> getAssessments(int offset) {
        String response = getData("$limit=1000&$offset=" + offset + "&$order=account_number");

        return processData(response);
    }

    @Override
    public Set<String> getAssessmentClasses() {
        Set<String> assessmentClassSet = new HashSet<>();

        for (int i = 1; i < 4; i++) {
            String response = getData("$select=distinct+mill_class_" + i);

            String[] assessmentClassArray = response.replaceAll("\"", "").split("\n");

            assessmentClassSet.addAll(Arrays.asList(assessmentClassArray).subList(1, assessmentClassArray.length));
        }

        return assessmentClassSet;
    }
}
