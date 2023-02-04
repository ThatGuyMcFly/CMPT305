import java.util.Scanner;

public class Lab3Main {
    public static void main(String[] args){
        String propertyAssessmentCsvFile = "Property_Assessment_Data_2022.csv";

        String input = getUserInput("CSV File name:");

        if(input.equals("")) {
            input = propertyAssessmentCsvFile;
        }

        PropertyAssessments propertyAssessments = new PropertyAssessments(input);

        if (propertyAssessments.isEmpty()) {
            System.out.println("Error: Can't open file " + input);
        }

        if (!propertyAssessments.isEmpty()) {
            input = getUserInput("Assessment Class:");

            PropertyAssessments assessmentClassPropertyAssessments = propertyAssessments.getAssessmentClassPropertyAssessments(input);

            printPropertyAssessmentsData(assessmentClassPropertyAssessments, "Statistics (assessment class = " + input + ")");
        }
    }

    /**
     * Presents user with a message and gets the input from the user
     * @param message the message to be displayed to the user
     * @return the user's input
     */
    private static String getUserInput (String message) {
        Scanner scanner = new Scanner(System.in);

        System.out.print(message + " ");

        return scanner.nextLine();
    }

    /**
     * Prints statistical information from a collection of property assessments
     * @param propertyAssessments the list of property assessments
     * @param message the message to print before the statistics
     */
    private static void printPropertyAssessmentsData(PropertyAssessments propertyAssessments, String message) {
        System.out.println(message);
        System.out.println("n = " + propertyAssessments.getNumberOfProperties());
        System.out.format("min = $%,d%n", propertyAssessments.getMinMax()[0]);
        System.out.format("max = $%,d%n", propertyAssessments.getMinMax()[1]);
        System.out.format("range = $%,d%n", propertyAssessments.getRange());
        System.out.format("mean = $%,.0f%n", propertyAssessments.getMean());
        System.out.format("median = $%,.0f%n", propertyAssessments.getMedian());
        System.out.println();
    }
}
