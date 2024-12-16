package com.example.app.Page;

public class Slimmekas {
    private int kasID;
    private String sensortype;
    private int dagnummer;
    private double temperatuur;
    private double luchtvochtigheid;
    private String timestamp;
    private boolean isHomepage; // Nieuw veld toegevoegd

    // Constructor
    public Slimmekas(int kasID, String sensortype, int dagnummer, double temperatuur, double luchtvochtigheid, String timestamp, boolean isHomepage) {
        this.kasID = kasID;
        this.sensortype = sensortype;
        this.dagnummer = dagnummer;
        this.temperatuur = temperatuur;
        this.luchtvochtigheid = luchtvochtigheid;
        this.timestamp = timestamp;
        this.isHomepage = isHomepage;
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
        return timestamp;
    }

    public boolean isHomepage() {
        return isHomepage;
    }

    public void setHomepage(boolean isHomepage) {
        this.isHomepage = isHomepage;
    }
}
