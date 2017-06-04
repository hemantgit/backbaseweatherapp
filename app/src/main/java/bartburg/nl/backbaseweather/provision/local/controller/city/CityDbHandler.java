package bartburg.nl.backbaseweather.provision.local.controller.city;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import bartburg.nl.backbaseweather.model.City;
import bartburg.nl.backbaseweather.model.Coordinates;

/**
 * Created by Bart on 6/3/2017.
 */

public class CityDbHandler extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";
    private static final String CITY_TABLE_NAME = "City";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_CITY_NAME = "name";
    private static final String COLUMN_LATITUDE = "lat";
    private static final String COLUMN_LONGITUDE = "lon";

    public CityDbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        String sqlCreateTable = "CREATE TABLE " + CITY_TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_CITY_NAME + " TEXT, "
                + COLUMN_LATITUDE + " REAL, "
                + COLUMN_LONGITUDE + " REAL "
                +  ");";
        db.execSQL(sqlCreateTable);

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO if this would be a real project: try to keep the data and provide legacy support if possible.
        if(oldVersion == 1){
            db.execSQL("DROP TABLE IF EXISTS " + CITY_TABLE_NAME);
        }
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    /**
     * Add city to database.
     * @param city City to add to the database
     * @return true if city is created and didn't already exist.
     */
    public boolean addCity(City city){
        if(city == null ||
            city.getCoordinates() == null||
            doesCityExist(city.getId())){
            // TODO call update
            return false;
        }
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, city.getId());
        values.put(COLUMN_CITY_NAME, city.getName());
        values.put(COLUMN_LATITUDE, city.getCoordinates().getLat());
        values.put(COLUMN_LONGITUDE, city.getCoordinates().getLon());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(CITY_TABLE_NAME, null, values);
        db.close();
        return true;
    }

    public void deleteCity(City city){
        deleteCity(city.getId());
    }

    public void deleteCity(Integer id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + CITY_TABLE_NAME + " WHERE " + COLUMN_ID + " = " + String.valueOf(id));
    }

    public boolean doesCityExist(Integer cityId) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + CITY_TABLE_NAME+ " WHERE " + COLUMN_ID + " = " + String.valueOf(cityId);
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public ArrayList<City> getAllCities(){
        ArrayList<City> cities = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + CITY_TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Integer id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String cityName = cursor.getString(cursor.getColumnIndex(COLUMN_CITY_NAME));
                double latitude = cursor.getDouble(cursor.getColumnIndex(COLUMN_LATITUDE));
                double longitude = cursor.getDouble(cursor.getColumnIndex(COLUMN_LONGITUDE));
                cities.add(new City(id, cityName, new Coordinates(latitude, longitude)));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return cities;
    }
}
