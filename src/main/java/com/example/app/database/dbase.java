package com.example.app.database;

import com.example.app.Page.Slimmekas;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class dbase {
    private static final String URL = "jdbc:mysql://localhost:3306/thechallenge";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    // Haal alle kassen op
    public List<Slimmekas> getSlimmekasData() {
        List<Slimmekas> slimmekasList = new ArrayList<>();
        String query = "SELECT KasID, Sensortype, Dagnummer, Temperatuur, Luchtvochtigheid, Timestamp, Kashomepage FROM slimmekas";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int kasID = rs.getInt("KasID");
                String sensortype = rs.getString("Sensortype");
                int dagnummer = rs.getInt("Dagnummer");
                double temperatuur = rs.getDouble("Temperatuur");
                double luchtvochtigheid = rs.getDouble("Luchtvochtigheid");
                String timestamp = rs.getString("Timestamp");
                boolean isHomepage = rs.getBoolean("Kashomepage");

                Slimmekas kas = new Slimmekas(kasID, sensortype, dagnummer, temperatuur, luchtvochtigheid, timestamp, isHomepage);
                slimmekasList.add(kas);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Fout bij het ophalen van kasgegevens.");
        }

        return slimmekasList;
    }

    // Reset alle kassen zodat geen enkele de homepage is
    public void resetHomepageKas() {
        String query = "UPDATE slimmekas SET Kashomepage = 0";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Fout bij het resetten van de homepage kas.");
        }
    }

    // Stel een specifieke kas in als homepage
    public void setKasAsHomepage(int kasID) {
        resetHomepageKas(); // Reset alle kassen eerst

        String query = "UPDATE slimmekas SET Kashomepage = 1 WHERE KasID = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, kasID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Fout bij het instellen van de homepage kas.");
        }
    }

    // Haal de huidige homepage-kas op
    public Slimmekas getHomepageKas() {
        String query = "SELECT * FROM slimmekas WHERE Kashomepage = 1";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                int kasID = rs.getInt("KasID");
                String sensortype = rs.getString("Sensortype");
                int dagnummer = rs.getInt("Dagnummer");
                double temperatuur = rs.getDouble("Temperatuur");
                double luchtvochtigheid = rs.getDouble("Luchtvochtigheid");
                String timestamp = rs.getString("Timestamp");

                return new Slimmekas(kasID, sensortype, dagnummer, temperatuur, luchtvochtigheid, timestamp, true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Fout bij het ophalen van de homepage kas.");
        }

        return null; // Geen kas gevonden
    }
}
