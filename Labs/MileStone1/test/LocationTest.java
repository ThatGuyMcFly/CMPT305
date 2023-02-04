import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    String latitude = "10.1010101";
    String longitude = "20.2020202";
    Location location = new Location(latitude, longitude);
    @Test
    void getLatitude() {
        assertEquals(latitude, location.getLatitude());
    }

    @Test
    void getLongitude() {
        assertEquals(longitude, location.getLongitude());
    }

    @Test
    void testEquals() {
        Location sameLocation = new Location(latitude, longitude);
        Location differentLocation = new Location("-11.11111", "22.22222");

        assertEquals(location, location);
        assertNotEquals(location, null);
        assertEquals(sameLocation, location);
        assertNotEquals(differentLocation, location);
    }

    @Test
    void testHashCode() {
        assertEquals(Objects.hash(latitude, longitude), location.hashCode());
    }

    @Test
    void testToString() {
        String expectedString = "Location{" +
                                "latitude='" + latitude + '\'' +
                                ", longitude='" + longitude + '\'' +
                                '}';

        assertEquals(expectedString, location.toString());
    }
}