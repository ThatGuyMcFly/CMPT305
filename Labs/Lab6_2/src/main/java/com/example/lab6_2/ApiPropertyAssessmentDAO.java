package com.example.lab6_2;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.IntStream;


public class ApiPropertyAssessmentDAO implements PropertyAssessmentDAO{

    private final String endPoint;

    public ApiPropertyAssessmentDAO(){
        this("https://data.edmonton.ca/resource/q7d6-ambg.csv");
    }

    public ApiPropertyAssessmentDAO(String endPoint){
        this.endPoint = endPoint;
    }

    /**
     * Gets the index of a string within in an array of strings
     * @param stringArray The array of string being searched
     * @param str The string being searched for
     * @return The index of the string in the array, or -1 if the string wasn't found
     */
    private int getIndex(String[] stringArray, String str) {
        return IntStream.range(0, stringArray.length)
                .filter(i -> str.equals(stringArray[i]))
                .findFirst()
                .orElse(-1);
    }

    /**
     * Creates a formatted URL Query
     * @param urlQuery the query to be formatted
     * @return a formatted URL Query
     */
    private String createUrl(String urlQuery) {
        String[] queryArray = urlQuery.split("&");
        StringBuilder url = new StringBuilder(endPoint);

        for (String subQuery: queryArray) {
            // avoids encoding '=' and '&' characters since that was causing issues when sending the queries
            int equalIndex = subQuery.indexOf('=');
            url.append(subQuery, 0, equalIndex + 1).append(URLEncoder.encode(subQuery.substring(equalIndex + 1), StandardCharsets.UTF_8));

            if (getIndex(queryArray, subQuery) != queryArray.length - 1){
                url.append("&");
            }
        }

        return url.toString();
    }

    /**
     * Sends a query and gets the data from the endpoint
     * @param query the query to be sent to the endpoint
     * @return The data returned from the endpoint
     */
    private String getData(String query) {
        String url = createUrl(query);

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

    /**
     * Gets a property assessment with the specified account number
     * @param accountNumber the account number of the property assessment
     * @return the property assessment with the specified account number or null if no property assessment was found
     */
    @Override
    public PropertyAssessment getByAccountNumber(int accountNumber) {
        String response = getData("?account_number=" + accountNumber);

        String[] propertyAssessmentStringArray = response.replaceAll("\"", "").split("\n");

        if (propertyAssessmentStringArray.length > 1){
            return new PropertyAssessment(propertyAssessmentStringArray[1].split(","));
        }

        return null;
    }

    /**
     * Processes the CSV data retrieved form the end point into a list of property assessments
     * @param data The CSV data to be processed
     * @return A List of property assessments
     */
    private List<PropertyAssessment> processData(String data) {
        List<PropertyAssessment> propertyAssessmentList = new ArrayList<>();

        String[] propertyAssessmentStringArray = data.replaceAll("\"", "").split("\n");

        for (int i = 1; i < propertyAssessmentStringArray.length; i++) {
            propertyAssessmentList.add(new PropertyAssessment(propertyAssessmentStringArray[i].split(",")));
        }

        return propertyAssessmentList;
    }

    /**
     * Gets a list of property assessments with a specified neighbourhood
     * @param neighbourhood The neighbourhood of the property assessments being searched for
     * @return A List of property assessments with the specified neighbourhood
     */
    @Override
    public List<PropertyAssessment> getByNeighbourhood(String neighbourhood) {
        String response = getData("?$where=" + createNeighbourhoodQuery(neighbourhood));

        return processData(response);
    }

    /**
     * Gets a list of property assessments with a specified address
     * @param address The address if the property assessments being searched for
     * @return A list of property assessments with the specified address
     */
    @Override
    public List<PropertyAssessment> getByAddress(String address) {
        if(address.isEmpty()) {
            return null;
        }

        String response = getData("?where=" + createAddressQuery(address));

        return processData(response);
    }

    /**
     * Gets a list of property assessments with a specified assessment class
     * @param assessmentClass The assessment class of the property assessments being search for
     * @return A list of property assessments with the specified assessment class
     */
    @Override
    public List<PropertyAssessment> getByAssessmentClass(String assessmentClass) {
        String response = getData("?$where=" + createAssessmentClassQuery(assessmentClass));

        return processData(response);
    }

    /**
     * Gets an unfiltered list of property assessments
     * @return A List of property assessments
     */
    @Override
    public List<PropertyAssessment> getPropertyAssessments() {
        String response = getData("?$limit=1000&$offset=0&$order=account_number");

        return processData(response);
    }

    /**
     * Creates a query for an address
     * @param address the address to be queried
     * @return a query for an address or an empty string if the address is empty
     */
    private String createAddressQuery(String address) {
        if(address.isEmpty()) {
            return "";
        }
        return "suite || ' ' || house_number || ' ' || street_name like " + "'%" + address + "%'";
    }

    /**
     *
     * @param neighbourhood
     * @return
     */
    private String createNeighbourhoodQuery(String neighbourhood) {
        if(neighbourhood.isEmpty()) {
            return "";
        }
        return "neighbourhood like " + "'%" + neighbourhood.toUpperCase() + "%'";
    }

    /**
     *
     * @param assessmentClass
     * @return
     */
    private String createAssessmentClassQuery(String assessmentClass) {
        if (assessmentClass.isEmpty()) {
            return "";
        }
        return "(mill_class_1 = " + "'" + assessmentClass.toUpperCase() + "' OR mill_class_2 = '" + assessmentClass.toUpperCase() + "'" + " OR mill_class_3 = '" + assessmentClass.toUpperCase() + "')";
    }

    /**
     *
     * @param min
     * @param max
     * @return
     */
    private String createAssessedValueRangeQuery(int min, int max) {
        String minString = "";
        String maxString = "";

        if (min < 0 && max < 0) {
            return "";
        }

        if (min < 0) {
            maxString = " <= " + max;
        } else if (max < 0) {
            minString = " >= " + min;
        } else {
            minString = "between " + min;
            maxString = " and " + max;
        }

        return "assessed_value " + minString + maxString;
    }

    /**
     *
     * @param filter
     * @return
     */
    private String createFilterQueryString(Filter filter) {
        String queryStart = "?$where=";

        StringBuilder query = new StringBuilder(queryStart);

        query.append(createAddressQuery(filter.getAddress().toUpperCase()));

        if(query.length() > queryStart.length() && !filter.getNeighbourhood().isEmpty()) {
            query.append(" AND ");
        }

        query.append(createNeighbourhoodQuery(filter.getNeighbourhood()));

        if(query.length() > queryStart.length() && !filter.getAssessmentClass().isEmpty()) {
            query.append(" AND ");
        }

        query.append(createAssessmentClassQuery(filter.getAssessmentClass()));

        if(query.length() > queryStart.length() && (filter.getMinimumAssessedValue() > -1 || filter.getMaximumAssessedValue() > -1)) {
            query.append(" AND ");
        }

        query.append(createAssessedValueRangeQuery(filter.getMinimumAssessedValue(), filter.getMaximumAssessedValue()));

        return query.toString();
    }

    /**
     *
     * @param filter
     * @return
     */
    @Override
    public List<PropertyAssessment> getPropertyAssessments(Filter filter) {
        String response = getData(createFilterQueryString(filter));

        return processData(response);
    }

    /**
     *
     * @return
     */
    @Override
    public Set<String> getAssessmentClasses() {
        Set<String> assessmentClassSet = new HashSet<>();

        for (int i = 1; i < 4; i++) {
            String response = getData("?$select=distinct mill_class_" + i);

            String[] assessmentClassArray = response.replaceAll("\"", "").split("\n");

            assessmentClassSet.addAll(Arrays.asList(assessmentClassArray).subList(1, assessmentClassArray.length));
        }

        return assessmentClassSet;
    }

    /**
     *
     * @param min
     * @return
     */
    @Override
    public List<PropertyAssessment> getByAssessedValueMinimum(int min) {
        return null;
    }

    /**
     *
     * @param max
     * @return
     */
    @Override
    public List<PropertyAssessment> getByAssessedValueMaximum(int max) {
        return null;
    }
}
