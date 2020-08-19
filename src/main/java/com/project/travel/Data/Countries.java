//package com.project.travel.Data;
//
//import java.sql.*;
//import java.util.List;
//
//public class Countries {
//
//    Connection connection;
//    Statement statement;
//    WykazPanstw parent;
//
//    public Countries(WykazPanstw parent, String localhostNr, String dbName, String password) {
//        this.parent = parent;
//        String url = "jdbc:postgresql://localhost:" + localhostNr + "/" + dbName;
//        try {
//            Class.forName("org.postgresql.Driver");
//            connection = DriverManager.getConnection(url, "postgres", password);
//            statement = connection.createStatement();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//        public void getData (List< Continents > list) throws SQLException {
//            ResultSet data = statement.executeQuery("SELECT * FROM fabrykasushi");
//            while (data.next()) {
//                String kraj = data.getString("kraj");
//                int dlugoscWyjazdu = data.getInt("dlugoscWyjazdu");
//                String hotel = data.getString("hotel");
//                String polozenie= data.getString("polozenie");
//                String plaza=data.getString("plaza");
//                String pokoj = data.getString("pokoj");
//                String allinclusive=data.getString("allinclusive");
//                String opis=data.getString("opis");
//                String udogodnienia = data.getString("udogodnienia");
//                int cena = data.getInt("cena");
//                int cenaDziecko = data.getInt("cenaDziecko");
//                list.add(new Continents(kraj, dlugoscWyjazdu, hotel, polozenie,plaza, pokoj, allinclusive, opis, udogodnienia, cena, cenaDziecko));
//            }
//        }
//    }
