package com.example.app.Page;

public class Slimmekas {
    private int kasID;
    private String sensortype;
    private int dagnummer;
    private double temperatuur;
    private double luchtvochtigheid;
    private String timestamp;  // Sla de timestamp als String op

    // Constructor
    public Slimmekas(int kasID, String sensortype, int dagnummer, double temperatuur, double luchtvochtigheid, String timestamp) {
        this.kasID = kasID;
        this.sensortype = sensortype;
        this.dagnummer = dagnummer;
        this.temperatuur = temperatuur;
        this.luchtvochtigheid = luchtvochtigheid;
        this.timestamp = timestamp;  // Ontvang de timestamp als String
    }

    // Getters
    public int getKasID() {
        return kasID;
    }

    public String getSensortype() {
        return sensortype;
    }

    public int getDagnummer() {
        return dagnummer;
    }

    public double getTemperatuur() {
        return temperatuur;
    }

    public double getLuchtvochtigheid() {
        return luchtvochtigheid;
    }

    public String getTimestamp() {
        return timestamp;  // Return de timestamp als String
    }
}
