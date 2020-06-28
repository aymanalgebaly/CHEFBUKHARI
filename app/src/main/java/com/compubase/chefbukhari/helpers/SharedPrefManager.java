package com.compubase.chefbukhari.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.gesture.Prediction;
import android.location.Location;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private Context context;


    private SharedPrefManager(Context mCtx) {
        this.context = mCtx;
    }

    public static synchronized SharedPrefManager getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(mCtx);
        }
        return mInstance;
    }

    public void saveUserLat(float lat) {
        SharedPreferences.Editor editor = context.getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        editor.putFloat("lat", lat);
        editor.apply();
    }

    public float getLat() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return sharedPreferences.getFloat("lat", 0);
    }


    public void saveUserLon(float lon) {
        SharedPreferences.Editor editor = context.getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        editor.putFloat("lon", lon);
        editor.apply();
    }

    public float getLon() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return sharedPreferences.getFloat("lon", 0);
    }


    public void saveCurrentUserLocations(List<Location> locationList) {

        Gson gson = new Gson();
        String json = gson.toJson(locationList);

        SharedPreferences.Editor editor = context.getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        editor.putString("currentUserLocations", json);
        editor.apply();
    }


    public List<Location> getCurrentUserLocations() {

        Gson gson = new Gson();
        SharedPreferences sharedPref = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        String json = sharedPref.getString("currentUserLocations", "");

        Type type = new TypeToken<List<Location>>() {
        }.getType();

        List<Location> arrPackageData = gson.fromJson(json, type);

        return arrPackageData;
    }


    public void saveDestinationUserLocations(List<Location> locationList) {

        Gson gson = new Gson();
        String json = gson.toJson(locationList);

        SharedPreferences.Editor editor = context.getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        editor.putString("destinationUserLocations", json);
        editor.apply();
    }


    public List<Location> getDestinationUserLocations() {

        Gson gson = new Gson();
        SharedPreferences sharedPref = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        String json = sharedPref.getString("destinationUserLocations", "");

        Type type = new TypeToken<List<Location>>() {
        }.getType();

        List<Location> locations = gson.fromJson(json, type);

        return locations;
    }





    public void searchHistory(List<Prediction> searchHistoryList) {

        Gson gson = new Gson();
        String json = gson.toJson(searchHistoryList);

        SharedPreferences.Editor editor = context.getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        editor.putString("searchHistory", json);
        editor.apply();
    }


    public List<Prediction> getSearchHistory() {

        Gson gson = new Gson();
        SharedPreferences sharedPref = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        String json = sharedPref.getString("searchHistory", "");

        Type type = new TypeToken<List<Prediction>>() {
        }.getType();

        List<Prediction> predictionList = gson.fromJson(json, type);

        return predictionList;
    }





    public void saveUserLatDestination(float lat) {
        SharedPreferences.Editor editor = context.getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        editor.putFloat("lat_dest", lat);
        editor.apply();
    }

    public float getLatDestination() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return sharedPreferences.getFloat("lat_dest", 0);
    }


    public void saveUserLonDestination(float lon) {
        SharedPreferences.Editor editor = context.getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        editor.putFloat("lon_dest", lon);
        editor.apply();
    }

    public float getLonDestination() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return sharedPreferences.getFloat("lon_dest", 0);
    }


    public void saveUserName(String user_name) {
        SharedPreferences.Editor editor = context.getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        editor.putString("user_name", user_name);
        editor.apply();
    }

    public String getUserName() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_name", null);
    }
    public void saveDistanceBetweenOriginAndDestination(String date) {
        SharedPreferences.Editor editor = context.getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        editor.putString("dis", date);
        editor.apply();
    }

    public String getDistanceBetweenOriginAndDestination() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return sharedPreferences.getString("dis", null);
    }

    public void saveDistanceBetweenOriginAndDestinationInteger(int date) {
        SharedPreferences.Editor editor = context.getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        editor.putInt("dis_int", date);
        editor.apply();
    }

    public int getTotalPrice() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("total_price", 0);
    }

    public void saveTotalPrice(int total_price) {
        SharedPreferences.Editor editor = context.getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        editor.putInt("total_price", total_price);
        editor.apply();
    }

    public int getDistanceBetweenOriginAndDestinationInteger() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("dis_int", 0);
    }

    public void saveDate(String date) {
        SharedPreferences.Editor editor = context.getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        editor.putString("date", date);
        editor.apply();
    }

    public String getDateCalendar() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return sharedPreferences.getString("date", null);
    }


    public void saveEndDate(String date) {
        SharedPreferences.Editor editor = context.getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        editor.putString("endDate", date);
        editor.apply();
    }

    public String getEndDateCalendar() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return sharedPreferences.getString("endDate", null);
    }


    public void saveTime(String time) {
        SharedPreferences.Editor editor = context.getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        editor.putString("time", time);
        editor.apply();
    }

    public String getTimeClock() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return sharedPreferences.getString("time", null);
    }
    public void saveArrivalTime(String arrivalTime) {
        SharedPreferences.Editor editor = context.getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        editor.putString("arrivalTime", arrivalTime);
        editor.apply();
    }

    public String getArrivalTime() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return sharedPreferences.getString("arrivalTime", null);
    }
    public void savePolyLine(String polyLine) {
        SharedPreferences.Editor editor = context.getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        editor.putString("polyLine", polyLine);
        editor.apply();
    }
    public String getPolyLine(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return sharedPreferences.getString("polyLine", "");
    }


    public void saveNumberOfBreaks(int numbeOfBreaks) {
        SharedPreferences.Editor editor = context.getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        editor.putInt("numbeOfBreaks", numbeOfBreaks);
        editor.apply();
    }

    public int getNumberOfBreaks() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("numbeOfBreaks", 0);
    }

    public void saveposition(int position) {
        SharedPreferences.Editor editor = context.getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        editor.putInt("position", position);
        editor.apply();
    }

    public int getposition() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("position", 0);
    }



    public void saveNumberOfPassengers(int numberOfPassengers) {
        SharedPreferences.Editor editor = context.getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        editor.putInt("numberOfPassengers", numberOfPassengers);
        editor.apply();
    }

    public int getNumberOfPassengers() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("numberOfPassengers", 0);
    }
    public void saveLuggageType(String luggageType) {
        SharedPreferences.Editor editor = context.getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        editor.putString("luggageType", luggageType);
        editor.apply();
    }

    public String getLuggageType() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return sharedPreferences.getString("luggageType","");
    }
    public void saveSameGender(String sameGender) {
        SharedPreferences.Editor editor = context.getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        editor.putString("sameGender", sameGender);
        editor.apply();
    }

    public String getSameGender() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return sharedPreferences.getString("sameGender","");
    }
    public void saveAgeOfPassengers(boolean ageOFPassengers) {
        SharedPreferences.Editor editor = context.getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        editor.putBoolean("ageOFPassengers", ageOFPassengers);
        editor.apply();
    }

    public boolean getAgeOfPassengers() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("ageOFPassengers",false);
    }
    public void saveTimeOfBreaks(String timeOFBreaks) {
        SharedPreferences.Editor editor = context.getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        editor.putString("timeOFBreaks", timeOFBreaks);
        editor.apply();
    }

    public String getTimeOfBreaks() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return sharedPreferences.getString("timeOFBreaks", null);
    }
    public void saveFlex(int flex) {
        SharedPreferences.Editor editor = context.getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        editor.putInt("flex", flex);
        editor.apply();
    }

    public int getFlex() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("flex",0);
    }

    public void saveUserImg(String user_img) {
        SharedPreferences.Editor editor = context.getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        editor.putString("user_img", user_img);
        editor.apply();
    }

    public String getUserImg() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_img", null);
    }

    public void saveUserId(int user_id) {
        SharedPreferences.Editor editor = context.getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        editor.putInt("user_id", user_id);
        editor.apply();
    }

    public int getUserId() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("user_id", 0);
    }

    public void saveLogin(int number) {
        SharedPreferences.Editor editor = context.getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        editor.putInt("save_login", number);
        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        int number = sharedPreferences.getInt("save_login", 0);  /// if value = 1 , keep him login
        return number != 0;
    }

}
