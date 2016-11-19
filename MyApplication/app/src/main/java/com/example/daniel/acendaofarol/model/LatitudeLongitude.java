package com.example.daniel.acendaofarol.model;

/**
 * Classe que representa a Latitude e Longitude atual
 */
public class LatitudeLongitude {
    private double latitude;
    private double longitude;

    public double getLatitude(){
        return this.latitude;
    }

    public double getLongitude(){
        return this.longitude;
    }

    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

    public void setLongitude(double longitude){
        this.longitude = longitude;
    }
}