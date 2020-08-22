package com.project.travel.Controller;

import com.project.travel.Data.Database;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Controller
public class KierunkiController {


    public List<RoomModel> getDataBaseData(int continentId, int countryId, int hotelId, int roomId) throws SQLException {

        String sqlStatement = "SELECT " +
                " \"Rooms\".room_id," +
                " \"Country\".country_id," +
                " \"Continent\".continent_id," +
                " \"Rooms\".persons, \"Rooms\".price, \"Rooms\".description, \"Rooms\".city, \"Rooms\".date, \"Rooms\".boarding," +
                " \"Hotels\".name as hotel_name," +
                " \"Hotels\".description," +
                " \"Country\".name as country_name," +
                " \"Continent\".name\n as continent_name" +
                " FROM \"Rooms\" \n" +
                " INNER JOIN \"Hotels\" ON \"Hotels\".hotel_id=\"Rooms\".hotel_id\n" +
                " INNER JOIN \"Country\" ON \"Country\".country_id=\"Hotels\".country_id\n" +
                " INNER JOIN \"Continent\" ON \"Continent\".continent_id=\"Country\".continent_fki";

        if (continentId >= 0) {
            System.out.println("asking only for continent_id=" + continentId);

            sqlStatement = appendWhere(sqlStatement, "\"Continent\".continent_id=" + continentId);
        }
        if (countryId >= 0) {
            System.out.println("asking only for country_id=" + countryId);
            sqlStatement = appendWhere(sqlStatement, "\"Country\".country_id=" + countryId);
        }

        if (hotelId >= 0) {
            System.out.println("asking only for hotel_id=" + hotelId);
            sqlStatement = appendWhere(sqlStatement, "\"Hotels\".hotel_id=" + hotelId);
        }

        if (roomId >= 0) {
            System.out.println("asking only for room_id=" + hotelId);
            sqlStatement = appendWhere(sqlStatement, "\"Rooms\".room_id=" + roomId);
        }

        System.out.println(sqlStatement);

        var resultSet = Database
                .getConnection()
                .createStatement()
                .executeQuery(sqlStatement);

        List<RoomModel> roomsList = new ArrayList<>();
        while (resultSet.next()) {
            roomsList.add(new RoomModel(
                    resultSet.getInt("room_id"),
                    resultSet.getInt("country_id"),
                    resultSet.getInt("continent_id"),
                    resultSet.getInt("persons"),
                    resultSet.getString("continent_name"),
                    resultSet.getString("country_name"),
                    resultSet.getString("hotel_name"),
                    resultSet.getString("price"),
                    resultSet.getString("description"),
                    resultSet.getString("city"),
                    resultSet.getString("date"),
                    resultSet.getString("boarding")
            ));
        }
        System.out.println(roomsList);

        return roomsList;
    }


    @GetMapping("/kierunki")
    public void kierunki(
            Model model,
            @RequestParam(defaultValue = "-1") int continentId,
            @RequestParam(defaultValue = "-1") int countryId,
            @RequestParam(defaultValue = "-1") int roomIdToAdd
    ) throws SQLException {
        System.out.println("Wybrano room: "  +roomIdToAdd) ;
        List<RoomModel> roomsList = getDataBaseData(continentId, countryId, -1, -1);
        List<RoomModel> continentsList = roomsList.stream().filter(distinctByKey(p -> p.getContinent())).collect(Collectors.toList());

        model.addAttribute("roomsList", roomsList);
        model.addAttribute("continentsList", continentsList);

        if (roomIdToAdd != -1) {
            addRoomToOrder(roomIdToAdd);
        }
    }

    private void addRoomToOrder(int roomIdToAdd) {
        String sqlStatementRemove = "DELETE FROM \"Order\"";
        try {
            PreparedStatement statement = Database
                    .getConnection()
                    .prepareStatement(sqlStatementRemove);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sqlStatement = "INSERT INTO \"Order\" (room_id) VALUES (?)";
        try {
            PreparedStatement statement = Database
                    .getConnection()
                    .prepareStatement(sqlStatement);
            statement.setInt(1, roomIdToAdd);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String appendWhere(String sql, String condition) {
        if (sql.contains("WHERE")) {
            return sql + " AND " + condition;
        } else {
            return sql + " WHERE " + condition;
        }
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        final Set<Object> seen = new HashSet<>();
        return t -> seen.add(keyExtractor.apply(t));
    }
}

class OrderModel {


    public final int roomId;

    OrderModel(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderModel that = (OrderModel) o;
        return roomId == that.roomId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId);
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "roomId=" + roomId +
                '}';
    }
}

class RoomModel implements Serializable {
    public final int roomId;
    public final int countryId;
    public final int continentId;
    public final int persons;
    public final String continent;
    public final String country;
    public final String hotel_name;
    public final String price;
    public final String description;
    public final String city;
    public final String date;
    public final String boarding;


    RoomModel(int roomId, int countryId, int continentId, int persons, String continent, String country, String hotel_name, String price, String description, String city, String date, String boarding) {
        this.roomId = roomId;
        this.countryId = countryId;
        this.continentId = continentId;
        this.persons = persons;
        this.continent = continent;
        this.country = country;
        this.hotel_name = hotel_name;
        this.price = price;
        this.description = description;
        this.city = city;
        this.date = date;
        this.boarding = boarding;


    }

    public String getCountry() {
        return this.country;
    }

    public int getCountryId() {
        return this.countryId;
    }

    public String getContinent() {
        return this.continent;
    }

    public int getContinentId() {
        return this.continentId;
    }

    @Override
    public String toString() {
        return "RoomModel{" +
                "roomId=" + roomId +
                " countryId=" + countryId +
                " continentId=" + continentId +
                ", persons=" + persons +
                ", continent='" + continent + '\'' +
                ", country='" + country + '\'' +
                ", hotel_name='" + hotel_name + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
