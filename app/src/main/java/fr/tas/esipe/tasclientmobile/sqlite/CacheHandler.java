package fr.tas.esipe.tasclientmobile.sqlite;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;


import java.util.logging.Logger;

import fr.tas.esipe.tasclientmobile.model.Parking;

public class CacheHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "csc_cache";
    public static final String PARKING_TABLE = "parking";

    public CacheHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        if (!isTableExist(db, PARKING_TABLE)){
            createParkingTable(db);
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public void createParkingTable(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + PARKING_TABLE +
                "(id_parking INTEGER UNIQUE PRIMARY KEY," +
                " capacity int," +
                " type_parking varchar(255)," +
                " address varchar(255)," +
                " latitude float," +
                " longitude float)");
    }

    public boolean isTableExist(SQLiteDatabase db, String tableName){
        Cursor cursor = db.rawQuery("SELECT DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);
        boolean result =  cursor.getCount() != 0 ? true : false;
        cursor.close();
        return result;
    }

    public void addParkingHandler(Parking parking) {

    }
}
