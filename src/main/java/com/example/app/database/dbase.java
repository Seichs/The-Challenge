package com.example.app.database;

import com.example.app.Page.Slimmekas;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class dbase {

    private static final String URL = "jdbc:mysql://localhost:3306/thechallenge";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    // Haal de Slimmekas data op
    public List<Slimmekas> getSlimmekasData() {
        List<Slimmekas> slimmekasList = new ArrayList<>();
        String query = "SELECT KasID, Sensortype, Dagnummer, Temperatuur, Luchtvochtigheid, Timestamp FROM slimmekas";

        // Try-with-resources zorgt ervoor dat de resources automatisch worden gesloten
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Itereer door de resultaten van de query
            while (rs.next()) {
                // Haal de gegevens op uit de ResultSet
                int kasID = rs.getInt("KasID");
                String sensortype = rs.getString("Sensortype");
                int dagnummer = rs.getInt("Dagnummer");
                double temperatuur = rs.getDouble("Temperatuur");
                double luchtvochtigheid = rs.getDouble("Luchtvochtigheid");
                String timestamp = rs.getString("Timestamp");  // Haal de timestamp als String op

                // Maak een Slimmekas object en voeg het toe aan de lijst
                Slimmekas kas = new Slimmekas(kasID, sensortype, dagnummer, temperatuur, luchtvochtigheid, timestamp);
                slimmekasList.add(kas);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Retourneer de lijst met Slimmekas objecten
        return slimmekasList;
    }
}
