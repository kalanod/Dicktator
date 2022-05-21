package com.kalanco.dictator.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BDService extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "todo";
    public static final String NAME = "NAME";
    public static final String PHONE = "PHONE";
    private static final int DATABASE_VERSION = 5;

    public BDService(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        {
            db.execSQL("CREATE TABLE todo (_id INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, PHONE TEXT);");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
