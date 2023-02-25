import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        CsvPropertyAssessmentDAO csvPropertyAssessmentDAO = new CsvPropertyAssessmentDAO();
//
//        printPropertyAssessmentsData(csvPropertyAssessmentDAO, "Stats:");



        ApiPropertyAssessmentDAO apiPropertyAssessmentDAO = new ApiPropertyAssessmentDAO();

        String input;

        input = getUserInput("Account number:");

        PropertyAssessment propertyAssessment = apiPropertyAssessmentDAO.getByAccountNumber(Integer.parseInt(input));

//        input = getUserInput("neighbourhood:");
//
//        List<PropertyAssessment> neighbourhoodPropertyAssessments = apiPropertyAssessmentDAO.getByNeighbourhood(input);

        List<PropertyAssessment> neighbourhoodPropertyAssessments = apiPropertyAssessmentDAO.getByNeighbourhood(input);

        input = getUserInput("Assessment Class:");

        List<PropertyAssessment> assessmentClassPropertyAssessments = apiPropertyAssessmentDAO.getByAssessmentClass(input);
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
    private static void printPropertyAssessmentsData(CsvPropertyAssessmentDAO propertyAssessments, String message) {
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
}