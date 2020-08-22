package com.project.travel.Controller;

import com.project.travel.Data.Database;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class kierunkiJsonController {

    @RequestMapping(value = "/countries")
    @ResponseBody
    public String getCountries(@RequestParam("depdrop_all_params[kontynenty]") int kontynenty) throws SQLException {
        System.out.println("aaaa zwracam countries, dla kontynent id:" + kontynenty);

        List<CountryModel> countriesList = getCountriesList(kontynenty);

        var jsonCountriesInner = new JSONArray();

        for (CountryModel singleCountry : countriesList
        ) {
            jsonCountriesInner
                    .put(new JSONObject()
                            .put("id", singleCountry.countryId)
                            .put("name", singleCountry.country)
                    );
        }

        var jsonCountriesOuter = new JSONObject()
                .put("output", jsonCountriesInner)
                .put("selected", "");

        String json = jsonCountriesOuter.toString();

        System.out.println("json:" + json);
        return json;

    }


    @RequestMapping(value = "/hotels")
    @ResponseBody
    public String getHotels(@RequestParam("depdrop_all_params[kraje]") int country) throws SQLException {
        System.out.println("aaaa zwracam hotele, dla country id:" + country);

        List<HotelsModel> hotelsList = getHotelsList(country);

        var jsonCountriesInner = new JSONArray();

        for (HotelsModel singleHotel : hotelsList
        ) {
            jsonCountriesInner
                    .put(new JSONObject()
                            .put("id", singleHotel.hotelId)
                            .put("name", singleHotel.name)
                    );
        }

        var jsonCountriesOuter = new JSONObject()
                .put("output", jsonCountriesInner)
                .put("selected", "");

        String json = jsonCountriesOuter.toString();

        System.out.println("json:" + json);
        return json;

    }

    @RequestMapping(value = "/pokoje")
    @ResponseBody
    public String getRooms(@RequestParam("depdrop_all_params[hotele]") int hotel) throws SQLException {
        System.out.println("aaaa zwracam pokoje , dla hotel  id:" + hotel);
        List<RoomModel> roomsList = new KierunkiController().getDataBaseData(-1, -1, hotel, -1);
        var jsonCountriesInner = new JSONArray();

        for (RoomModel singleRoom : roomsList
        ) {
            jsonCountriesInner
                    .put(new JSONObject()
                            .put("id", singleRoom.roomId)
                            .put("name", singleRoom.persons)
                    );
        }

        var jsonCountriesOuter = new JSONObject()
                .put("output", jsonCountriesInner)
                .put("selected", "");

        String json = jsonCountriesOuter.toString();

        System.out.println("json:" + json);
        return json;

    }

    @RequestMapping(value = "/cena")
    @ResponseBody
    public String getPrice(@RequestParam("depdrop_all_params[pokoje]") int room) throws SQLException {
        System.out.println("aaaa zwracam cene , dla room id:" + room);
        List<RoomModel> roomsList = new KierunkiController().getDataBaseData(-1, -1, -1, room);
        var jsonCountriesInner = new JSONArray();

        for (RoomModel singlePrice : roomsList
        ) {
            jsonCountriesInner
                    .put(new JSONObject()
                            .put("id", singlePrice.roomId)
                            .put("name", singlePrice.price)
                    );
        }

        var jsonCountriesOuter = new JSONObject()
                .put("output", jsonCountriesInner)
                .put("selected", "");

        String json = jsonCountriesOuter.toString();

        System.out.println("json:" + json);
        return json;

    }

    public List<HotelsModel> getHotelsList(int countryId) throws SQLException {

        String sqlStatement = "SELECT " +

                " \"Hotels\".hotel_id as hotel_id," +
                " \"Hotels\".name\n as hotel_name" +
                " FROM \"Hotels\" WHERE country_id=" + countryId + "\n";
        System.out.println(sqlStatement);


        var resultSet = Database
                .getConnection()
                .createStatement()
                .executeQuery(sqlStatement);

        List<HotelsModel> hotelList = new ArrayList<>();
        while (resultSet.next()) {
            hotelList.add(new HotelsModel(
                    resultSet.getInt("hotel_id"),
                    resultSet.getString("hotel_name")
            ));
        }
        System.out.println(hotelList);

        return hotelList;
    }

    public List<CountryModel> getCountriesList(int continentId) throws SQLException {

        String sqlStatement = "SELECT " +

                " \"Country\".country_id as country_id," +
                " \"Country\".name\n as country_name" +
                " FROM \"Country\" WHERE continent_fki=" + continentId + "\n";
        System.out.println(sqlStatement);


        var resultSet = Database
                .getConnection()
                .createStatement()
                .executeQuery(sqlStatement);

        List<CountryModel> countryList = new ArrayList<>();
        while (resultSet.next()) {
            countryList.add(new CountryModel(
                    resultSet.getInt("country_id"),
                    resultSet.getString("country_name")
            ));
        }
        System.out.println(countryList);

        return countryList;
    }

    public List<ContinentModel> getContinentsList() throws SQLException {

        String sqlStatement = "SELECT " +

                " \"Continent\".continent_id as continent_id," +
                " \"Continent\".name\n as continent_name" +
                " FROM \"Continent\" \n";
        System.out.println(sqlStatement);


        var resultSet = Database
                .getConnection()
                .createStatement()
                .executeQuery(sqlStatement);

        List<ContinentModel> continentList = new ArrayList<>();
        while (resultSet.next()) {
            continentList.add(new ContinentModel(
                    resultSet.getInt("continent_id"),
                    resultSet.getString("continent_name")
            ));
        }
        System.out.println(continentList);

        return continentList;
    }

}

class CountryModel {
    public final int countryId;
    public final String country;

    CountryModel(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }
}

class ContinentModel {
    public final int continentId;
    public final String continent;

    ContinentModel(int continentId, String continent) {
        this.continentId = continentId;
        this.continent = continent;
    }
}

class HotelsModel {
    public final int hotelId;
    public final String name;


    HotelsModel(int hotelId, String name) {
        this.hotelId = hotelId;
        this.name = name;
    }
}
