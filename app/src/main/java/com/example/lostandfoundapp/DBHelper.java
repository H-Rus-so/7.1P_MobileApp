package com.example.lostandfoundapp;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

// This class helps me manage my local database for saving, reading, and deleting adverts
public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME    = "lostfound.db";  // the name of my database
    private static final int    DB_VERSION = 1;               // version number

    // Table and column names
    private static final String TABLE       = "adverts";
    private static final String COL_ID      = "_id";
    private static final String COL_TYPE    = "type";
    private static final String COL_NAME    = "name";
    private static final String COL_PHONE   = "phone";
    private static final String COL_DESC    = "description";
    private static final String COL_DATE    = "date";
    private static final String COL_LOCATION= "location";

    // Constructor just calls the super class with my DB name and version
    public DBHelper(Context ctx) {
        super(ctx, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // This method is called the first time the database is made
        // creating a table with all the advert fields
        String sql = "CREATE TABLE " + TABLE + " ("
                + COL_ID      + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TYPE    + " TEXT, "
                + COL_NAME    + " TEXT, "
                + COL_PHONE   + " TEXT, "
                + COL_DESC    + " TEXT, "
                + COL_DATE    + " TEXT, "
                + COL_LOCATION+ " TEXT"
                + ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        // If I ever upgrade the database, just drop the old table and create it again
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    /** This method adds a new advert to the database and returns the new row ID */
    public long addAdvert(Advert ad) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        // Put each value from the advert into the ContentValues object
        cv.put(COL_TYPE, ad.getType());
        cv.put(COL_NAME, ad.getName());
        cv.put(COL_PHONE, ad.getPhone());
        cv.put(COL_DESC, ad.getDescription());
        cv.put(COL_DATE, ad.getDate());
        cv.put(COL_LOCATION, ad.getLocation());
        // Insert into the database
        long id = db.insert(TABLE, null, cv);
        db.close();
        return id;
    }

    /** This method gets all the adverts in the database and returns them as a list */
    public List<Advert> getAllAdverts() {
        List<Advert> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE,
                null, null, null, null, null, COL_ID + " DESC"); // get all rows, sorted newest first
        while (c.moveToNext()) {
            // For each row, create a new Advert and add it to the list
            Advert a = new Advert(
                    c.getInt(c.getColumnIndexOrThrow(COL_ID)),
                    c.getString(c.getColumnIndexOrThrow(COL_TYPE)),
                    c.getString(c.getColumnIndexOrThrow(COL_NAME)),
                    c.getString(c.getColumnIndexOrThrow(COL_PHONE)),
                    c.getString(c.getColumnIndexOrThrow(COL_DESC)),
                    c.getString(c.getColumnIndexOrThrow(COL_DATE)),
                    c.getString(c.getColumnIndexOrThrow(COL_LOCATION))
            );
            list.add(a);
        }
        c.close();
        db.close();
        return list;
    }

    /** This method gets a single advert by its ID */
    public Advert getAdvertById(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE, null,
                COL_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null);
        Advert a = null;
        if (c.moveToFirst()) {
            // If we found a row with that ID, build the Advert object
            a = new Advert(
                    c.getInt(c.getColumnIndexOrThrow(COL_ID)),
                    c.getString(c.getColumnIndexOrThrow(COL_TYPE)),
                    c.getString(c.getColumnIndexOrThrow(COL_NAME)),
                    c.getString(c.getColumnIndexOrThrow(COL_PHONE)),
                    c.getString(c.getColumnIndexOrThrow(COL_DESC)),
                    c.getString(c.getColumnIndexOrThrow(COL_DATE)),
                    c.getString(c.getColumnIndexOrThrow(COL_LOCATION))
            );
        }
        c.close();
        db.close();
        return a;
    }

    /** This method deletes an advert using its ID */
    public void deleteAdvert(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE, COL_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
