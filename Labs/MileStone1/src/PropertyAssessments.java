import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PropertyAssessments {

    private final List<PropertyAssessment> propertyAssessments;

    public PropertyAssessments() {
        propertyAssessments = new ArrayList<>();
    }

    /**
     * Constructor for the Property Assessments Class
     * @param csvFile path to a csv file
     */
    public PropertyAssessments(String csvFile) {
        List<String[]> propertyAssessmentData;
        try {
            propertyAssessmentData = this.readInCsv(csvFile);
        } catch (IOException error) {
            propertyAssessments = new ArrayList<>();
            return;
        }

        this.propertyAssessments = this.createPropertyAssessments(propertyAssessmentData);
    }

    /**
     * Private method for processing the data is a specified CSV file
     * @param csvFile path to the csv file to be processed
     * @return a list of string arrays the hold the property assessment data
     * @throws IOException throws an IO Exception if file can't be found
     */
    private List<String[]> readInCsv(String csvFile) throws IOException {
        List<String[]> propertyAssessmentData;

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(csvFile))) {
            reader.readLine();

            propertyAssessmentData = new ArrayList<>();

            String line;

            while ((line = reader.readLine()) != null) {
                String[] propertyAssessment = line.split(",");

                propertyAssessmentData.add(propertyAssessment);
            }
        }

        return propertyAssessmentData;
    }

    /**
     * Creates a list of property assessments from the provided property assessment data
     * @param propertyAssessmentData a list of string arrays that contain the property assessment data
     *                               in the following format:
     *          0          1         2              4         5            6               7          8          9           10         11           12                  13                  14                     15                   16                  17                  18
     * { Account Number, Suite, House Number, Street name, Garage, Neighbourhood ID, Neighbourhood, Ward, Assessed Value, Latitude, Longitude, Point Location, Assessment Class % 1, Assessment Class % 2, Assessment Class % 3, Assessment Class 1, Assessment Class 2, Assessment Class 3 }
     *
     * @return a list Property Assessment objects
     */
    private List<PropertyAssessment> createPropertyAssessments(List<String[]> propertyAssessmentData) {
        List<PropertyAssessment> propertyAssessments = new ArrayList<>();

        for (String[] propertyAssessmentDatum : propertyAssessmentData) {
            propertyAssessments.add(new PropertyAssessment(propertyAssessmentDatum));
        }

        return propertyAssessments;
    }

    /**
     * Gets the minimum and maximum assessed value of the property assessments
     * @return an array with the minimum and maximum assessed values in the following format: {minimum, maximum}
     */
    public int[] getMinMax() {

        int min = propertyAssessments.get(0).getAssessedValue();
        int max = min;

        for (int i = 1; i < propertyAssessments.size(); i++) {
            int propertyValue = propertyAssessments.get(i).getAssessedValue();

            if (propertyValue < min) {
                min = propertyValue;
            } else if (propertyValue > max) {
                max = propertyValue;
            }
        }

        return new int[]{min, max};
    }

    /**
     * Gets the range of assessed values
     * @return an integer value of the range
     */
    public int getRange(){
        int[] minMax = getMinMax();

        return minMax[1] - minMax[0];
    }

    /**
     * Gets the mean assessed value
     * @return a double value of the mean
     */
    public double getMean() {
        double sum = propertyAssessments.get(0).getAssessedValue();

        for (int i = 1; i < propertyAssessments.size(); i++) {
            sum += propertyAssessments.get(i).getAssessedValue();
        }

        return sum/propertyAssessments.size();
    }

    /**
     * gets the median assessed value
     * @return a double of the median
     */
    public double getMedian() {
        double median;
        List<PropertyAssessment> orderedPropertyAssessments = new ArrayList<>(propertyAssessments);

        orderedPropertyAssessments.sort(null);
        
        if (orderedPropertyAssessments.size()%2 == 0){
            int index1 = orderedPropertyAssessments.size() / 2;
            int index2 = index1 - 1;

            int propertyValue1 = orderedPropertyAssessments.get(index1).getAssessedValue();
            int propertyValue2 = orderedPropertyAssessments.get(index2).getAssessedValue();

            median = (propertyValue1 + propertyValue2)/2.0;
        } else {
            int index = (orderedPropertyAssessments.size() / 2);

            median = orderedPropertyAssessments.get(index).getAssessedValue();
        }

        return median;
    }

    /**
     * Gets the number of property assessments
     * @return an integer of the number of property assessments
     */
    public int getNumberOfProperties() {
        return propertyAssessments.size();
    }

    /**
     * Gets the property assessment with the specified account number
     * @param accountNumber the account number of a property assessment
     * @return a property assessment object or null if no such account number exists
     */
    public PropertyAssessment getPropertyAssessmentByAccountNumber(int accountNumber) {

        for(PropertyAssessment propertyAssessment: propertyAssessments ) {
            if(propertyAssessment.getAccountNumber() == accountNumber) {
                return propertyAssessment;
            }
        }

        return null;
    }

    /**
     * Adds a property assessment to the list of property assessments
     * @param propertyAssessment the property assessment to be added
     */
    public void addPropertyAssessment(PropertyAssessment propertyAssessment) {
        propertyAssessments.add(propertyAssessment);
    }

    /**
     * Gets a list of property assessments from a specified neighbourhood
     * @param neighbourhood the specified neighbourhood
     * @return a Property Assessments object with all the property assessments from the specified neighbourhood
     */
    public PropertyAssessments getNeighbourhoodPropertyAssessments (String neighbourhood) {
        PropertyAssessments neighbourhoodPropertyAssessments = new PropertyAssessments();

        for(PropertyAssessment propertyAssessment: propertyAssessments) {
            if(propertyAssessment.getNeighbourhood().toLowerCase().equals(neighbourhood.toLowerCase())) {
                neighbourhoodPropertyAssessments.addPropertyAssessment(propertyAssessment);
            }
        }

        return neighbourhoodPropertyAssessments;
    }

    /**
     * Gets a list of property assessments with a specified assessment class
     * @param assessmentClass the specified assessment class
     * @return a Property Assessments object with all the property assessments with a specified assessment class
     */
    public PropertyAssessments getAssessmentClassPropertyAssessments (String assessmentClass) {
        PropertyAssessments assessmentClassPropertyAssessments = new PropertyAssessments();

        for(PropertyAssessment propertyAssessment: propertyAssessments) {
            for(String propertyAssessmentClass: propertyAssessment.getAssessmentClasses()){
                if(propertyAssessmentClass.toLowerCase().equals(assessmentClass.toLowerCase())){
                    assessmentClassPropertyAssessments.addPropertyAssessment(propertyAssessment);
                }
            }
        }

        return assessmentClassPropertyAssessments;
    }

    /**
     * Indicates if the property assessments if empty
     * @return a boolean whether the property assessments is empty
     */
    public boolean isEmpty(){
        return propertyAssessments.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyAssessments that = (PropertyAssessments) o;
        return Objects.equals(propertyAssessments, that.propertyAssessments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(propertyAssessments);
    }
}