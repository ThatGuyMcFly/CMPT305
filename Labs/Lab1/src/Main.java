import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    /**
     * Main function
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String propertyAssementCsvFile = "Property_Assessment_Data_2022.csv";

        try {
            List<String[]> propertyAssessmentData = getPropertyAssessmentDataFromCsvFile(propertyAssementCsvFile);
            printPropertyData(propertyAssessmentData);
        } catch (IOException error) {
            System.out.println("Error: " + error);
        }
    }

    /**
     * Gets a list of string arrays that contains the property assesment data in the specified CSV file
     * @param propertyAssessmentCsvFile the specified CSV file that contains the property assestment data
     * @return a list of string arrays
     * @throws IOException
     */
    private static List<String[]> getPropertyAssessmentDataFromCsvFile(String propertyAssessmentCsvFile) throws IOException {
        
        BufferedReader reader = Files.newBufferedReader(Paths.get(propertyAssessmentCsvFile));
        reader.readLine();

        List<String[]> propertyAssetmentData = new ArrayList<String[]>();

        String line;

        while((line = reader.readLine()) != null) {
            String[] propertyAssessment = line.split(",");

            propertyAssetmentData.add(propertyAssessment);
        }

        return propertyAssetmentData;
    }

    /**
     * Prints data about the property assessment file
     * @param propertyAssessmentData The data collected from the property assessment file
     */
    private static void printPropertyData (List<String[]> propertyAssessmentData) {
        int[] minMaxValue = getMinMaxValue(propertyAssessmentData);

        Set<String> wards = getWards(propertyAssessmentData);

        Set<String> propertyAssessmentClasses = getPropertyAssessmentClasses(propertyAssessmentData);

        System.out.println("Lowest Value Property: " + minMaxValue[0]);
        
        System.out.println("Highest Value Property: " + minMaxValue[1]);

        System.out.println("Number of Wards: " + wards.size());

        System.out.println("Property Assessment Classes: " + propertyAssessmentClasses);

        System.out.println("");
    }

    /**
     * A function for getting the lowest and highest property values 
     * @param propertyAssessmentData the 2D String array that contains the property assesment data
     * @return a 2 element array containing the lowest and highest property value [minValue, maxValue]
     */
    private static int[] getMinMaxValue(List<String[]> propertyAssessmentData) {        
        int maxValue = Integer.parseInt(propertyAssessmentData.get(0)[8]);
        int minValue = Integer.parseInt(propertyAssessmentData.get(0)[8]);

        for (int i = 1; i < propertyAssessmentData.size(); i++) {
            
            String[] property = propertyAssessmentData.get(i);
            int propertyValue = Integer.parseInt(property[8]);
            if(propertyValue > maxValue) {
                maxValue = propertyValue;
            }

            if(propertyValue < minValue) {
                minValue = propertyValue;
            }
        }

        int[] minMaxValue = {minValue, maxValue};

        return minMaxValue;
    }

    /**
     * Gets a set of the wards specified in the property assestment data
     * @param propertyAssessmentData Property assessment data
     * @return a set of all the wards
     */
    private static Set<String> getWards(List<String[]> propertyAssessmentData) {

        Set<String> wards = new HashSet<String>();

        for (String[] property: propertyAssessmentData) {
            wards.add(property[7]);
        }

        return wards;
    }

    /**
     * Gets a set of property assessment classes
     * @param propertyAssessmentData property assessment data 
     * @return a set of property assessment classes
     */
    private static Set<String> getPropertyAssessmentClasses(List<String[]> propertyAssessmentData) {
        Set<String> propertyAssessmentClasses = new HashSet<>();

        for(String[] property: propertyAssessmentData) {
            String propeprtyAssessmentClass = property[15];

            propertyAssessmentClasses.add(propeprtyAssessmentClass);
        }

        return propertyAssessmentClasses;
    }
}
