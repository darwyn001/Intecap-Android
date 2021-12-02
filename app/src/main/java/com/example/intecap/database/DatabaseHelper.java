package com.example.intecap.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.intecap.models.Provider;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "productos.db";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE = "CREATE TABLE providers(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, address TEXT, product TEXT, picture TEXT)";
    public static DatabaseHelper instance;
    public SQLiteDatabase db;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public static DatabaseHelper getInstance(Context context) {
        if (instance != null)
            return instance;

        return instance = new DatabaseHelper(context);
    }

    public void insert(Provider provider) {
        ContentValues cv = new ContentValues();
        cv.put("name", provider.getName());
        cv.put("address", provider.getAddress());
        cv.put("product", provider.getProduct());
        cv.put("picture", provider.getPicturePath());
       db.insert("providers", null, cv);
    }

    public void delete(int id) {
        db.delete("providers", "_id=?", new String[]{String.valueOf(id)});
    }

    @SuppressLint("Range")
    public ArrayList<Provider> selectAll() {
        ArrayList<Provider> dataList = new ArrayList<>();
        Cursor c = db.rawQuery("select * from providers", null);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                dataList.add(new Provider(
                        Integer.parseInt(c.getString(c.getColumnIndex("_id"))),
                        c.getString(c.getColumnIndex("name")),
                        c.getString(c.getColumnIndex("address")),
                        c.getString(c.getColumnIndex("product")),
                        c.getString(c.getColumnIndex("picture"))
                ));
            } while (c.moveToNext());
            c.close();
        }
        return dataList;
    }
}
