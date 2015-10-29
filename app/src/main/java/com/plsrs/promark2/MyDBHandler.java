package com.plsrs.promark2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sunil on 7/27/2015.
 */
public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABSE_VERSION = 1;
    private static final String DATABASE_NAME = "ring.db";
    public int count=0;
    public String[] temp;
    public String[] packagearray;



    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABSE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE ring("+
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT," +
                "PACKAGE TEXT," +
                "BT TEXT," +
                "AT TEXT);";
        db.execSQL(query);

        //for inserting installed apps into db

        count = MainActivity.getCount();
        temp = new String[count];
        packagearray = new String[count];
        temp = MainActivity.getTemp();
        packagearray = MainActivity.getPackagearray();

        for(int i=0; i<count; i++){
            ContentValues values = new ContentValues();
            values.put("NAME", temp[i]);
            values.put("PACKAGE", packagearray[i]);
            values.put("BT", "Venkat you got notification from");
            values.put("AT", "");
            db.insert("ring", null, values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void input(String bt,String text, String at,String pack){

       /* ContentValues values = new ContentValues();
        values.put("NAME", text);
        values.put("PACKAGE", pack);
        values.put("AT", at);
        values.put("BT", bt);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("ring", null, values);
        db.close(); */

        String query = "UPDATE ring SET BT='"+bt+"' WHERE PACKAGE='"+pack+"';";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
        query = "UPDATE ring SET AT='"+at+"' WHERE PACKAGE='"+pack+"';";
        db.execSQL(query);

    }


    public String get_at(String pack){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT AT FROM ring where PACKAGE='"+pack+"';", null);
        c.moveToFirst();
        return c.getString(c.getColumnIndex("AT"));
    }

    public String get_bt(String pack){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT BT FROM ring where PACKAGE='"+pack+"';", null);
        c.moveToFirst();
        return c.getString(c.getColumnIndex("BT"));
    }

    public String get_app(String pack){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT NAME FROM ring where PACKAGE='"+pack+"';", null);
        c.moveToFirst();
        return c.getString(c.getColumnIndex("NAME"));
    }
}
