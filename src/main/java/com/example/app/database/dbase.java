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
        String query = "SELECT KasID, Sensortype, Dagnummer, Temperatuur, Luchtvochtigheid, Timestamp, Kashomepage, isLoaded FROM slimmekas";

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

    // Haal alleen nieuwe kassen op die nog niet zijn ingeladen (isLoaded = 0)
    public List<Slimmekas> getNewSlimmekasData() {
        List<Slimmekas> newSlimmekasList = new ArrayList<>();
        String query = "SELECT KasID, Sensortype, Dagnummer, Temperatuur, Luchtvochtigheid, Timestamp FROM slimmekas WHERE isLoaded = 0";

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

                Slimmekas kas = new Slimmekas(kasID, sensortype, dagnummer, temperatuur, luchtvochtigheid, timestamp, false);
                newSlimmekasList.add(kas);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Fout bij het ophalen van nieuwe kasgegevens.");
        }

        return newSlimmekasList;
    }

    // Update isLoaded status van kassen naar 1 (ingeladen)
    public void updateKassenIsLoaded(List<Slimmekas> newKassen) {
        String query = "UPDATE slimmekas SET isLoaded = 1 WHERE KasID = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            for (Slimmekas kas : newKassen) {
                pstmt.setInt(1, kas.getKasID());
                pstmt.addBatch();
            }

            pstmt.executeBatch();  // Voer alle updates uit in een batch
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Fout bij het updaten van isLoaded status.");
        }
    }

    // Haal de kas op die op de homepage staat
    public Slimmekas getHomepageKas() {
        Slimmekas homepageKas = null;
        String query = "SELECT KasID, Sensortype, Dagnummer, Temperatuur, Luchtvochtigheid, Timestamp, Kashomepage FROM slimmekas WHERE Kashomepage = 1 LIMIT 1";

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
                boolean isHomepage = rs.getBoolean("Kashomepage");

                homepageKas = new Slimmekas(kasID, sensortype, dagnummer, temperatuur, luchtvochtigheid, timestamp, isHomepage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Fout bij het ophalen van de gepinde kas.");
        }

        return homepageKas;
    }

    // Zet een kas vast op de homepage
    public void setKasAsHomepage(int kasID) {
        String queryUpdate = "UPDATE slimmekas SET Kashomepage = 1 WHERE KasID = ?";
        String queryReset = "UPDATE slimmekas SET Kashomepage = 0 WHERE Kashomepage = 1"; // Reset de huidige homepage

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmtUpdate = conn.prepareStatement(queryUpdate);
             PreparedStatement pstmtReset = conn.prepareStatement(queryReset)) {

            // Reset de huidige homepage
            pstmtReset.executeUpdate();

            // Zet de geselecteerde kas als homepage
            pstmtUpdate.setInt(1, kasID);
            pstmtUpdate.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Fout bij het instellen van de kas op de homepage.");
        }
    }

    // Reset de kas op de homepage (zet Kashomepage terug naar 0)
    public void resetHomepageKas() {
        String query = "UPDATE slimmekas SET Kashomepage = 0 WHERE Kashomepage = 1";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Fout bij het resetten van de homepage kas.");
        }
    }
}