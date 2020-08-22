package com.project.travel.Controller;

import com.project.travel.Data.Database;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.xml.transform.Source;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PanelController {


    @GetMapping("/panel")
    public void panelPage(Model model) throws SQLException {
        List<RoomModel> roomsList = getOrderModel();
        model.addAttribute("roomsList", roomsList);

    }

    public List<RoomModel> getOrderModel() throws SQLException {
        String sqlStatement = "SELECT " + "*" +
                " FROM \"Order\" \n";
        var resultSet = Database
                .getConnection()
                .createStatement()
                .executeQuery(sqlStatement);

        resultSet.next();
        var roomId = resultSet.getInt("room_id");
        System.out.println(roomId);
        String sqlRoomStatement = "SELECT " +
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
                " INNER JOIN \"Continent\" ON \"Continent\".continent_id=\"Country\".continent_fki\n" +
                " WHERE \"Rooms\".room_id=" + Integer.toString(roomId) + "\n";
        var orders = Database
                .getConnection()
                .createStatement()
                .executeQuery(sqlRoomStatement);
        List<RoomModel> roomsList = new ArrayList<>();
        while (orders.next()) {
            roomsList.add(new RoomModel(
                    orders.getInt("room_id"),
                    orders.getInt("country_id"),
                    orders.getInt("continent_id"),
                    orders.getInt("persons"),
                    orders.getString("continent_name"),
                    orders.getString("country_name"),
                    orders.getString("hotel_name"),
                    orders.getString("price"),
                    orders.getString("description"),
                    orders.getString("city"),
                    orders.getString("date"),
                    orders.getString("boarding")
            ));
        }
        return roomsList;
    }

    class OrderModel {
        public int roomId;

        OrderModel(List<OrderItemModel> items, int totalPrice) {
            this.items = items;
            this.totalPrice = totalPrice;
        }

        public final List<OrderItemModel> items;
        public final int totalPrice;
    }

    class OrderItemModel {
        public final String roomName;
        public final int osoby;
        public final int price;

        OrderItemModel(String roomName, int osoby, int price) {
            this.roomName = roomName;
            this.osoby = osoby;
            this.price = price;
        }
    }
}


