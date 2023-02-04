import java.util.*;

public class Lab2Main {
    /**
     * Main function
     * @param args
     */
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
            printPropertyAssessmentsData(propertyAssessments, "Descriptive statistics of all property assessments");

            input = getUserInput("Find a property assessment by account number:");

            try {
                PropertyAssessment propertyAssessment = propertyAssessments.getPropertyAssessmentByAccountNumber(Integer.parseInt(input));

                if (propertyAssessment != null) {
                    printPropertyAssessmentData(propertyAssessment);
                } else {
                    System.out.println("Error: invalid account number");
                    System.out.println("Sorry, account number not found");
                }
            } catch (NumberFormatException exception) {
                System.out.println("Error: invalid account number");
                System.out.println("Sorry, account number not found");
            }

            System.out.println();

            input = getUserInput("Neighbourhood:");

            PropertyAssessments neighbourhoodPropertyAssessments = propertyAssessments.getNeighbourhoodPropertyAssessments(input);

            if (neighbourhoodPropertyAssessments.getNumberOfProperties() == 0) {
                System.out.println("Data not found");
            } else {
                printPropertyAssessmentsData(neighbourhoodPropertyAssessments, "Statistics (neighbourhood = " + input + ")");
            }
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

        int[] minMax = propertyAssessments.getMinMax();

        System.out.format("min = $%,d%n", minMax[0]);
        System.out.format("max = $%,d%n", minMax[1]);

        System.out.format("range = $%,d%n", propertyAssessments.getRange());
        System.out.format("mean = $%,.0f%n", propertyAssessments.getMean());
        System.out.format("median = $%,.0f%n", propertyAssessments.getMedian());
        
        System.out.println();
    }

    /**
     * Prints information about a property
     * @param propertyAssessment the property whose information is to be printed
     */
    private static void printPropertyAssessmentData(PropertyAssessment propertyAssessment) {
        System.out.println("Account number: " + propertyAssessment.getAccountNumber());
        printAddress(propertyAssessment);
        System.out.format("Assessed value: $%,d%n", propertyAssessment.getAssessedValue());
        printPropertyAssessmentClassData(propertyAssessment);
        System.out.println("Neighbourhood: " + propertyAssessment.getNeighbourhood() + " (" + propertyAssessment.getWard() + ")");
        System.out.println("Location: (" + propertyAssessment.getLocation().getLatitude() + ", " + propertyAssessment.getLocation().getLongitude() + ")");
        System.out.println();
    }

    /**
     * Prints the Assessment Class data of a specified property
     * @param propertyAssessment The property whose Assessment Class data will be printed
     */
    private static void printPropertyAssessmentClassData(PropertyAssessment propertyAssessment) {
        List<String> assessmentClasses = propertyAssessment.getAssessmentClasses();
        List<Integer> assessmentClassPercentages = propertyAssessment.getAssessmentClassPercentages();
        System.out.print("Assessment class: [");
        for (int i = 0; i < assessmentClasses.size(); i++ ) {
            System.out.print(assessmentClasses.get(i) + " " + assessmentClassPercentages.get(i) + "%");
            if (i < assessmentClasses.size() - 1){
                System.out.print(" ");
            }
        }
        System.out.println("]");
    }

    /**
     * Prints the address of a specified property
     * @param propertyAssessment the property whose address is to be printed
     */
    private static void printAddress(PropertyAssessment propertyAssessment) {
        String suite = propertyAssessment.getAddress().getSuite();
        String houseNumber = propertyAssessment.getAddress().getHouseNumber();
        String street = propertyAssessment.getAddress().getStreet();

        System.out.print("Address: ");

        if (!suite.equals("")) {
            System.out.print(suite + " ");
        }

        if (!houseNumber.equals("")) {
            System.out.print(houseNumber + " ");
        }

        if(!street.equals("")) {
            System.out.print(street);
        }

        System.out.println();
    }
}
