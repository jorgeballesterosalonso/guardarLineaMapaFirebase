package com.pass.grabarmapas;

public class Ubicacion {
    private double lat;
    private double longi;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLongi() {
        return longi;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }

    public Ubicacion(double lat, double longi) {
        this.lat = lat;
        this.longi = longi;
    }

    @Override
    public String toString() {
        return "Ubicacion{" +
                "lat=" + lat +
                ", longi=" + longi +
                '}';
    }
}
