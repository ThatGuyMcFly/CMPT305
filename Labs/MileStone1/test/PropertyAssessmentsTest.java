import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class PropertyAssessmentsTest {

    String testCsvFile = "test/test_csv_file.csv";
    String neighbourhood1CsvFile = "test/neighbourhood1.csv";
    String assessmentClassBCsvFile = "test/assessmentClassB.csv";

    PropertyAssessments propertyAssessments;

    @BeforeEach
    void setup() {
        propertyAssessments = new PropertyAssessments(testCsvFile);
    }

    @Test
    void constructor() {
        PropertyAssessments nullPropertyAssessments = new PropertyAssessments("invalidFile");

        assertTrue(nullPropertyAssessments.isEmpty());
    }

    @Test
    void getMinMax() {
        int expectedMin = 1000000;
        int expectedMax = 10000000;
        int[] minMax = propertyAssessments.getMinMax();

        assertEquals(expectedMin, minMax[0]);
        assertEquals(expectedMax, minMax[1]);
    }

    @Test
    void getRange() {

        int expectedRange = 9000000;

        assertEquals(expectedRange, propertyAssessments.getRange());
    }

    @Test
    void getMean() {
        double expectedMean = 5500000;

        assertEquals(expectedMean, propertyAssessments.getMean());
    }

    @Test
    void getMedian() {
        double expectedMedian = 5500000;

        assertEquals(expectedMedian, propertyAssessments.getMedian());

        PropertyAssessments oddNumberedPropertyAssessments = new PropertyAssessments(assessmentClassBCsvFile);

        expectedMedian = 8000000;

        assertEquals(expectedMedian, oddNumberedPropertyAssessments.getMedian());
    }

    @Test
    void getNumberOfProperties() {
        assertEquals(10, propertyAssessments.getNumberOfProperties());
    }

    @Test
    void getPropertyAssessmentByAccountNumber() {
        String[] propertyAssessmentDatum = new String[] {"10", "", "1010", "110 AVE", "N", "4", "NEIGHBOURHOOD 4", "WARD1", "6000000", "53.39951332", "-113.3967087", "POINT (-113.39670873000341 53.39951331897703)", "92", "5", "3", "ASSESSMENT CLASS A", "ASSESSMENT CLASS B", "ASSESSMENT CLASS C"};

        PropertyAssessment expectedPropertyAssessment = new PropertyAssessment(propertyAssessmentDatum);

        assertEquals(expectedPropertyAssessment, propertyAssessments.getPropertyAssessmentByAccountNumber(10));
        assertNull(propertyAssessments.getPropertyAssessmentByAccountNumber(20));
    }

    @Test
    void addPropertyAssessment() {
        String[] propertyAssessmentDatum = new String[] {"11", "", "1011", "111 AVE", "N", "4",	"NEIGHBOURHOOD 4", "WARD1", "10000000", "53.39951332", "-113.3967087", "POINT (-113.39670873000341 53.39951331897703)", "100", "", "", "RESIDENTIAL"};

        PropertyAssessment newPropertyAssessment = new PropertyAssessment(propertyAssessmentDatum);

        assertNull(propertyAssessments.getPropertyAssessmentByAccountNumber(11));

        propertyAssessments.addPropertyAssessment(newPropertyAssessment);

        assertEquals(newPropertyAssessment, propertyAssessments.getPropertyAssessmentByAccountNumber(11));
    }

    @Test
    void getNeighbourhoodPropertyAssessments() {
        PropertyAssessments expectedNeighbourhood1Assessments = new PropertyAssessments(neighbourhood1CsvFile);

        PropertyAssessments neighbourhood1Assessments = propertyAssessments.getNeighbourhoodPropertyAssessments("Neighbourhood 1");

        assertEquals(expectedNeighbourhood1Assessments, neighbourhood1Assessments);
    }

    @Test
    void getAssessmentClassPropertyAssessments() {
        PropertyAssessments expectedAssessmentClassBAssessments = new PropertyAssessments(assessmentClassBCsvFile);

        PropertyAssessments assessmentClassBAssessments = propertyAssessments.getAssessmentClassPropertyAssessments("ASSESSMENT CLASS B");

        assertEquals(expectedAssessmentClassBAssessments, assessmentClassBAssessments);
    }

    @Test
    void isEmpty() {
        PropertyAssessments emptyPropertyAssessments = new PropertyAssessments();

        assertTrue(emptyPropertyAssessments.isEmpty());

        assertFalse(propertyAssessments.isEmpty());
    }

    @Test
    void testEquals() {
        PropertyAssessments samePropertyAssessments = new PropertyAssessments(testCsvFile);
        PropertyAssessments differentPropertyAssessments = new PropertyAssessments(neighbourhood1CsvFile);
        assertEquals(propertyAssessments, propertyAssessments);
        assertEquals(samePropertyAssessments, propertyAssessments);
        assertEquals(propertyAssessments, samePropertyAssessments);
        assertNotEquals(propertyAssessments, null);
        assertNotEquals(differentPropertyAssessments, propertyAssessments);
        assertNotEquals(propertyAssessments, differentPropertyAssessments);
    }

    @Test
    void testHashCode() {
        PropertyAssessments samePropertyAssessments = new PropertyAssessments(testCsvFile);

        assertEquals(samePropertyAssessments.hashCode(), propertyAssessments.hashCode());
    }
}