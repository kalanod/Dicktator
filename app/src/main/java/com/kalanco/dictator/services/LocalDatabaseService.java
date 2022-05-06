package com.kalanco.dictator.services;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kalanco.dictator.models.ShopItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

public class LocalDatabaseService extends SQLiteOpenHelper {
    private static String DB_NAME = "DicktatorDB.db";
    private static String DB_PATH = "";
    private static final int DB_VERSION = 4;

    private SQLiteDatabase mDataBase;
    private final Context mContext;
    private boolean mNeedUpdate = false;

    public LocalDatabaseService(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        if (android.os.Build.VERSION.SDK_INT >= 17)
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        else
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.mContext = context;

        copyDataBase();
        mDataBase = getWritableDatabase();
        this.getReadableDatabase();
    }

    public void updateDataBase() throws IOException {
        if (mNeedUpdate) {
            File dbFile = new File(DB_PATH + DB_NAME);
            if (dbFile.exists())
                dbFile.delete();

            copyDataBase();

            mNeedUpdate = false;
        }
    }

    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    private void copyDataBase() {
        if (!checkDataBase()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    private void copyDBFile() throws IOException {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        OutputStream mOutput = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0)
            mOutput.write(mBuffer, 0, mLength);
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    public boolean openDataBase() throws SQLException {
        mDataBase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return mDataBase != null;
    }

    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            mNeedUpdate = true;
    }

    public List<ShopItem> getListItem() {
        String product = "";
        List<ShopItem> res = new LinkedList<>();
        Cursor cursor = mDataBase.rawQuery("SELECT * FROM shop", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            res.add(new ShopItem(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3)));
            cursor.moveToNext();
        }
        cursor.close();
        return res;
    }

    public boolean canBuy(int id) {
        Cursor cursor = mDataBase.rawQuery("SELECT * FROM user;", null);
        cursor.moveToFirst();
        int money = cursor.getInt(2);
        cursor = mDataBase.rawQuery("SELECT * FROM shop where _id = " + id + ";", null);
        cursor.moveToFirst();
        int price = cursor.getInt(3);
        return money >= price;
    }

    public void buy(int id) {
        mDataBase.execSQL("UPDATE user SET money = (SELECT money FROM user where _id = 1) - (SELECT price FROM shop where _id = " + id + ") WHERE _id = 1");
        mDataBase.execSQL("UPDATE shop SET count = (SELECT count FROM shop where _id = " + id +") + 1 WHERE _ID = " + id + ";");
    }

    public String getMoney() {
        Cursor cursor = mDataBase.rawQuery("SELECT * FROM user;", null);
        cursor.moveToFirst();
        int money = cursor.getInt(2);
        return Integer.toString(money);
    }
}