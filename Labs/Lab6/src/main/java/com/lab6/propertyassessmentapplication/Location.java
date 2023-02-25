package com.lab6.propertyassessmentapplication;

import java.util.Objects;

public class Location {

    private final String latitude;

    private final String longitude;

    /**
     * Constructor for the Location Class
     * @param latitude - the location's latitude
     * @param longitude - the location longitude
     */
    public Location (String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Getter for the location's latitude
     * @return the location's latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Getter for the location's longitude
     * @return the location's longitude
     */
    public String getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(latitude, location.latitude) &&
                Objects.equals(longitude, location.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }

    @Override
    public String toString() {
        return "Location{" +
                "latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
