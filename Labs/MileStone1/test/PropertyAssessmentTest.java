import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

class PropertyAssessmentTest {
    String accountNumber1 = "101010101";
    String accountNumber2 = "202020202";
    String accountNumber3 = "303030303";
    String suite = "1";
    String houseNumber = "101";
    String streetName = "101 Ave";
    String garage1 = "n";
    String garage2 = "y";
    String neighbourhoodId = "1010";
    String neighbourhood = "Neighbourhood";
    String ward = "Ward";
    String assessedValue1 = "100000";
    String assessedValue2 = "200000";
    String assessedValue3 = "300000";
    String latitude = "10.1010101";
    String longitude = "20.2020202";
    String pointLocation = "POINT(" + latitude + " " + longitude + ")";
    String emptyAssessmentPercentage = "";
    String assessmentPercentage1 = "100";
    String assessmentPercentage2 = "50";
    String assessmentPercentage3 = "25";
    String assessmentClass1 = "RESIDENTIAL";
    String assessmentClass2 = "COMMERCIAL";
    String assessmentClass3 = "FARMLAND";
    String[] propertyAssessmentDataWith1AssessmentClass = new String[] {accountNumber1, suite, houseNumber, streetName, garage1, neighbourhoodId, neighbourhood, ward, assessedValue1, latitude, longitude, pointLocation, assessmentPercentage1, emptyAssessmentPercentage, emptyAssessmentPercentage, assessmentClass1 };
    String[] propertyAssessmentDataWith1AssessmentClassAlternate = new String[] {accountNumber2, suite, houseNumber, streetName, garage1, neighbourhoodId, neighbourhood, ward, assessedValue1, latitude, longitude, pointLocation, assessmentPercentage1, emptyAssessmentPercentage, emptyAssessmentPercentage, assessmentClass2 };
    String[] propertyAssessmentDataWith2AssessmentClasses = new String[] {accountNumber2, suite, houseNumber, streetName, garage2, neighbourhoodId, neighbourhood, ward, assessedValue2, latitude, longitude, pointLocation, assessmentPercentage2, assessmentPercentage2, emptyAssessmentPercentage, assessmentClass1, assessmentClass2 };
    String[] propertyAssessmentDataWith3AssessmentClasses = new String[] {accountNumber3, suite, houseNumber, streetName, garage1, neighbourhoodId, neighbourhood, ward, assessedValue3, latitude, longitude, pointLocation, assessmentPercentage2, assessmentPercentage3, assessmentPercentage3, assessmentClass1, assessmentClass2, assessmentClass3 };
    PropertyAssessment propertyAssessment1 = new PropertyAssessment(propertyAssessmentDataWith1AssessmentClass);
    PropertyAssessment propertyAssessment2 = new PropertyAssessment(propertyAssessmentDataWith2AssessmentClasses);
    PropertyAssessment propertyAssessment3 = new PropertyAssessment(propertyAssessmentDataWith3AssessmentClasses);
    PropertyAssessment propertyAssessment4 = new PropertyAssessment(propertyAssessmentDataWith1AssessmentClassAlternate);


    @Test
    void getAccountNumber() {
        int propertyAssessmentAccountNumber = propertyAssessment1.getAccountNumber();

        assertEquals(propertyAssessmentAccountNumber, Integer.parseInt(accountNumber1));
    }

    @Test
    void getAddress() {
        Address testAddress = new Address(suite, houseNumber, streetName);

        Address propertyAssessmentAddress = propertyAssessment1.getAddress();

        assertEquals(propertyAssessmentAddress, testAddress);
    }

    @Test
    void hasGarage() {
        assertFalse(propertyAssessment1.hasGarage());
        assertTrue(propertyAssessment2.hasGarage());
    }

    @Test
    void getNeighbourhoodId() {
        String propertyAssessmentNeighbourhoodId = propertyAssessment1.getNeighbourhoodId();

        assertEquals(propertyAssessmentNeighbourhoodId, neighbourhoodId);
    }

    @Test
    void getNeighbourhood() {
        String propertyAssessmentNeighbourhood = propertyAssessment1.getNeighbourhood();

        assertEquals(propertyAssessmentNeighbourhood, neighbourhood);
    }

    @Test
    void getWard() {
        String propertyAssessmentWard = propertyAssessment1.getWard();

        assertEquals(propertyAssessmentWard, ward);
    }

    @Test
    void getAssessedValue() {
        int propertyAssessmentAssessedValue = propertyAssessment1.getAssessedValue();

        assertEquals(propertyAssessmentAssessedValue, Integer.parseInt(assessedValue1));
    }

    @Test
    void getLocation() {
        Location testLocation = new Location(latitude, longitude);

        Location propertyAssessmentLocation = propertyAssessment1.getLocation();

        assertEquals(propertyAssessmentLocation, testLocation);
    }

    @Test
    void getAssessmentClasses() {
        List<String> testAssessmentClasses1 = new ArrayList<>();
        List<String> testAssessmentClasses2 = new ArrayList<>();
        List<String> testAssessmentClasses3 = new ArrayList<>();

        testAssessmentClasses1.add(assessmentClass1);

        testAssessmentClasses2.add(assessmentClass1);
        testAssessmentClasses2.add(assessmentClass2);

        testAssessmentClasses3.add(assessmentClass1);
        testAssessmentClasses3.add(assessmentClass2);
        testAssessmentClasses3.add(assessmentClass3);

        assertEquals(propertyAssessment1.getAssessmentClasses(), testAssessmentClasses1);
        assertEquals(propertyAssessment2.getAssessmentClasses(), testAssessmentClasses2);
        assertEquals(propertyAssessment3.getAssessmentClasses(), testAssessmentClasses3);
    }

    @Test
    void getAssessmentClassPercentages() {
        List<Integer> testAssessmentPercentages1 = new ArrayList<>();
        List<Integer> testAssessmentPercentages2 = new ArrayList<>();
        List<Integer> testAssessmentPercentages3 = new ArrayList<>();

        testAssessmentPercentages1.add(Integer.parseInt(assessmentPercentage1));

        testAssessmentPercentages2.add(Integer.parseInt(assessmentPercentage2));
        testAssessmentPercentages2.add(Integer.parseInt(assessmentPercentage2));

        testAssessmentPercentages3.add(Integer.parseInt(assessmentPercentage2));
        testAssessmentPercentages3.add(Integer.parseInt(assessmentPercentage3));
        testAssessmentPercentages3.add(Integer.parseInt(assessmentPercentage3));

        assertEquals(propertyAssessment1.getAssessmentClassPercentages(), testAssessmentPercentages1);
        assertEquals(propertyAssessment2.getAssessmentClassPercentages(), testAssessmentPercentages2);
        assertEquals(propertyAssessment3.getAssessmentClassPercentages(), testAssessmentPercentages3);
    }

    @Test
    void compareTo() {
        int testCompare = Integer.parseInt(assessedValue1) - Integer.parseInt(assessedValue2);
        int compare = propertyAssessment1.compareTo(propertyAssessment2);

        assertEquals(compare, testCompare);
    }

    @Test
    void testEquals() {
        PropertyAssessment samePropertyAssessment = new PropertyAssessment(propertyAssessmentDataWith1AssessmentClass);
        assertEquals(propertyAssessment1, propertyAssessment1);
        assertNotEquals(null, propertyAssessment1);
        assertNotEquals(propertyAssessment1, propertyAssessment2);
        assertEquals(propertyAssessment1, samePropertyAssessment);
        assertEquals(samePropertyAssessment, propertyAssessment1);
    }

    @Test
    void testHashCode() {
        PropertyAssessment samePropertyAssessment = new PropertyAssessment(propertyAssessmentDataWith1AssessmentClass);

        assertEquals(samePropertyAssessment.hashCode(), propertyAssessment1.hashCode());
    }

    @Test
    void testToString() {

        Address address = new Address(suite, houseNumber, streetName);
        Location location = new Location(latitude, longitude);

        List<String> assessmentClassPercentages = new ArrayList<>();
        assessmentClassPercentages.add(assessmentPercentage1);

        List<String> assessmentClasses = new ArrayList<>();
        assessmentClasses.add(assessmentClass1);

        String expectedString = "PropertyAssessment{" +
                "accountNumber='" + accountNumber1 + '\'' +
                ", address=" + address +
                ", garage=" + "false" +
                ", neighbourhoodId='" + neighbourhoodId + '\'' +
                ", neighbourhood='" + neighbourhood + '\'' +
                ", ward='" + ward + '\'' +
                ", assessedValue=" + assessedValue1 +
                ", location=" + location +
                ", assessmentClassPercentages=" + assessmentClassPercentages +
                ", assessmentClasses=" + assessmentClasses +
                '}';

        assertEquals(propertyAssessment1.toString(), expectedString);
    }
}