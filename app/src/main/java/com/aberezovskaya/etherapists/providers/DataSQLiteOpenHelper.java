package com.aberezovskaya.etherapists.providers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *  SQLiteOpenHelper class to manage
 *  database creation
 *  and opening.
 *
 *  For the test project, the database is not
 *  very necessary, cause we can use
 *  a small amount of static test data
 *
 *  But for the real project such kind of database,
 *  containing exercises for different physical problems,
 *  and, possibly, some another info in future,
 *  would be useful
 */
public class DataSQLiteOpenHelper extends SQLiteOpenHelper {

    public DataSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
