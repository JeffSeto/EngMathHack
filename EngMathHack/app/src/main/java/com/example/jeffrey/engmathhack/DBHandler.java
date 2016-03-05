package com.example.jeffrey.engmathhack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by melissali on 16-03-04.
 */
public class DBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "database.db";
    private static final String TABLE_NAME = "transactions";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_NOTES = "notes";

    private static boolean tableCreated = false;
    private static final String TAG = DBHandler.class.getSimpleName();

    // Constructor
    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        //Log.d(TAG, "Created database handler");
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_AMOUNT + " DOUBLE, " +
                COLUMN_NOTES + " TEXT);";

        db.execSQL(query);
        tableCreated = true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
        onCreate(db);
    }

    public void createTransaction (Context myContext, User user){

        if (lookup(user.getName())){
            changeAmount(user.getName(), user.getAmount());
            return;
        }

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_AMOUNT, user.getAmount());
        values.put(COLUMN_NOTES, user.getNote());

        SQLiteDatabase db = getWritableDatabase();

        //Log.d(TAG, "Transaction created");
        //Log.d(TAG, "Table created = " + tableCreated);

        long chk = db.insert(TABLE_NAME, null, values);

        if(chk!=0){
            Toast.makeText(myContext, "Record added successfully",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(myContext, "Record added failed...! ",Toast.LENGTH_LONG).show();
        }

        db.close();
    }

    public boolean lookup (String name){

        String query = "SELECT " + COLUMN_NAME + " FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " = \"" + name + "\"";
        //Log.d(TAG, "lookup running query: " + query);

        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        try{
            String nameQuery = c.getString(c.getColumnIndex(COLUMN_NAME));
            //Log.d(TAG, "nameQuery = " + nameQuery);
            db.close();
            return true;
        }catch(Exception e){
            //Log.d(TAG, "Query failed");
            db.close();
            return false;
        }
    }

    public void changeAmount (String name, double deltaAmount){
        String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_AMOUNT + " = " + COLUMN_AMOUNT + " + " + deltaAmount +
                " WHERE " + COLUMN_NAME + " = \"" + name + "\";";

        String amountQuery = "SELECT " + COLUMN_AMOUNT + " FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " = \"" + name + "\"";

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
        Cursor c = db.rawQuery(amountQuery, null);
        //Log.d(TAG, "Number of columns: " + c.getColumnCount());
        c.moveToFirst();
        double amount = c.getFloat(c.getColumnIndex(COLUMN_AMOUNT));
        if (amount == 0) {
            settleTransaction(name);
        }

        db.close();
    }

    public void settleTransaction (String name) {
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " = \"" + name + "\";";
        SQLiteDatabase db = getWritableDatabase();
        //Log.d(TAG, query);
        db.execSQL(query);
        db.close();
    }

    public String databaseToString () {
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + ";";

        Cursor c = db.rawQuery(query, null);
        //Log.d(TAG, "Number of rows: " + c.getCount());
        c.moveToFirst();

        while (!c.isAfterLast()){
            if (c.getString(c.getColumnIndex(COLUMN_NAME)) != null){
                dbString += "Name: " + c.getString(c.getColumnIndex(COLUMN_NAME)) + " | ";
                dbString += "Amount: " + c.getString(c.getColumnIndex(COLUMN_AMOUNT)) + " | ";
                dbString += "Notes: " + c.getString(c.getColumnIndex(COLUMN_NOTES));
                dbString += "\n";
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }

}
