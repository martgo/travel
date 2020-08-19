//package com.project.travel.Controller.Kraje;
//
//import com.project.travel.Data.Database;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.sql.SQLException;
//
//@Controller
//public class AfrykaController {
//
//    @GetMapping("/afryka")
//    public void afryka() throws SQLException {
//        var resultSet = Database
//                .getConnection()
//                .createStatement()
////                .executeQuery("SELECT * FROM \"Rooms\" WHERE continent_name='afryka'");
//                .executeQuery("SELECT * FROM \"Rooms\"");
//        while(resultSet.next()) {
//            System.out.println(resultSet.getString("hotel_id"));
//            System.out.println(resultSet.getDouble("price"));
//        }
//    }
//}
//
//// ORM