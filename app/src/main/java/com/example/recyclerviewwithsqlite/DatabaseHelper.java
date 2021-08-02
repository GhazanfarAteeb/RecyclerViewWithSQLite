package com.example.recyclerviewwithsqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB ="employees.db";
    public static final String TABLE_NAME="employee";
    public static final String COL_1="ID";
    public static final String COL_2="Name";
    public static final String COL_3="Salary";
        public DatabaseHelper(Context context) {
            super(context, DB, null,1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_NAME +
                    " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Name TEXT, Salary DOUBLE)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
            onCreate(db);
        }
}
