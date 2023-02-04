import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    String suite = "1a";
    String houseNumber = "101";
    String streetName = "123 Ave";

    Address address = new Address(suite, houseNumber, streetName);

    @Test
    void getSuite() {
        assertEquals(suite, address.getSuite());
    }

    @Test
    void getHouseNumber() {
        assertEquals(houseNumber, address.getHouseNumber());
    }

    @Test
    void getStreet() {
        assertEquals(streetName, address.getStreet());
    }

    @Test
    void testEquals() {
        Address differentAddress = new Address("2b", "202", "321 Ave");
        Address sameAddress = new Address(suite, houseNumber, streetName);

        assertEquals(address, address);
        assertNotEquals(address, null);
        assertNotEquals(differentAddress, address);
        assertEquals(sameAddress, address);
    }

    @Test
    void testHashCode() {
        assertEquals(Objects.hash(suite, houseNumber, streetName), address.hashCode());
    }

    @Test
    void testToString() {
        String expectedString = "Address{" +
                                "suite='" + suite + '\'' +
                                ", houseNumber='" + houseNumber + '\'' +
                                ", street='" + streetName + '\'' +
                                '}';

        assertEquals(expectedString, address.toString());
    }
}