package com.example.jeffrey.engmathhack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//import android.widget.Toast;

/**
 * Created by melissali on 16-03-04.
 */
public class DBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "database.db";
    private static final String TABLE_NAME = "table";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_NOTES = "notes";

    // Constructor
    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE" + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_AMOUNT + " DOUBLE, " +
                COLUMN_NOTES + " TEXT)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void createTransaction (User user){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_AMOUNT, user.getAmount());
        values.put(COLUMN_NOTES, user.getNote());
        SQLiteDatabase db = getWritableDatabase();

        long chk = db.insert(TABLE_NAME, null, values);

        // myContext???????
//        if(chk!=0){
//            Toast.makeText(myContext, "Record added successfully",Toast.LENGTH_LONG).show();
//        }else{
//            Toast.makeText(myContext, "Record added failed...! ",Toast.LENGTH_LONG).show();
//        }

        db.close();
    }

    public void changeAmount (User user, double deltaAmount){
        String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_AMOUNT + " = " + COLUMN_AMOUNT + " + " + deltaAmount +
                " WHERE " + COLUMN_NAME + " = " + user.getName();
        user.changeAmount(deltaAmount);
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
    }

    public void settleTransaction (User user) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " = " + user.getName());

        // delete user
    }

    public String databaseToString () {
        StringBuffer dbString = new StringBuffer();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while (!c.isAfterLast()){
            if (c.getString(c.getColumnIndex(COLUMN_NAME)) != null){
                dbString.append("Name: " + c.getString(c.getColumnIndex(COLUMN_NAME)) + " | ");
                dbString.append("Amount: " + c.getString(c.getColumnIndex(COLUMN_AMOUNT)) + " | ");
                dbString.append("Notes: " + c.getString(c.getColumnIndex(COLUMN_NOTES)));
                dbString.append("\n");
            }
        }

        return dbString.toString();
    }

}
