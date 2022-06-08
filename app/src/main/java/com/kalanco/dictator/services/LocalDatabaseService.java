package com.kalanco.dictator.services;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kalanco.dictator.models.GameUser;
import com.kalanco.dictator.models.ShopItem;
import com.kalanco.dictator.models.User;

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
    private static final int DB_VERSION = 8;

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

        if (!checkDataBase()) {
            copyDataBase();
        }
        mDataBase = getWritableDatabase();
        this.getReadableDatabase();
    }

    public void updateDataBase() throws IOException {
        if (mNeedUpdate) {
            File dbFile = new File(DB_PATH + DB_NAME);
            if (dbFile.exists())
                dbFile.delete();

            if (!checkDataBase()) {
                copyDataBase();
            }
            mNeedUpdate = false;
        }
    }

    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    public void copyDataBase() {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
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
        mDataBase.execSQL("UPDATE shop SET count = (SELECT count FROM shop where _id = " + id + ") + 1 WHERE _ID = " + id + ";");
    }

    public String getMoney() {
        Cursor cursor = mDataBase.rawQuery("SELECT * FROM user;", null);
        cursor.moveToFirst();
        int money = cursor.getInt(2);
        return Integer.toString(money);
    }

    public String getName() {
        Cursor cursor = mDataBase.rawQuery("SELECT * FROM user;", null);
        cursor.moveToFirst();
        String name = cursor.getString(1);
        return name;
    }

    public int getBest() {
        Cursor cursor = mDataBase.rawQuery("SELECT * FROM user;", null);
        cursor.moveToFirst();
        int best = cursor.getInt(4);
        return best;
    }

    public void storeMoney(int price) {
        mDataBase.execSQL("UPDATE user SET money = (SELECT money FROM user where _id = 1) - " + price + " WHERE _id = 1");
    }

    public boolean canDo(int price) {
        Cursor cursor = mDataBase.rawQuery("SELECT * FROM user;", null);
        cursor.moveToFirst();
        int money = cursor.getInt(2);
        return money >= price;
    }

    public int getScore() {
        Cursor cursor = mDataBase.rawQuery("SELECT * FROM user;", null);
        cursor.moveToFirst();
        int money = cursor.getInt(3);
        return money;
    }

    public int getTopScore() {
        Cursor cursor = mDataBase.rawQuery("SELECT * FROM user;", null);
        cursor.moveToFirst();
        int topScore = cursor.getInt(4);
        return topScore;
    }

    public int getLoyal() {
        Cursor cursor = mDataBase.rawQuery("SELECT * FROM user;", null);
        cursor.moveToFirst();
        int money = cursor.getInt(5);
        return money;
    }

    public int getPolice() {
        Cursor cursor = mDataBase.rawQuery("SELECT * FROM user;", null);
        cursor.moveToFirst();
        int money = cursor.getInt(6);
        return money;
    }

    public GameUser getGameUser() {
        Cursor cursor = mDataBase.rawQuery("SELECT * FROM user;", null);
        cursor.moveToFirst();
        GameUser user = new GameUser(cursor.getInt(2),
                cursor.getInt(3),
                cursor.getInt(4),
                cursor.getInt(5),
                cursor.getInt(6));
        return user;
    }

    public GameUser getUser() {
        Cursor cursor = mDataBase.rawQuery("SELECT * FROM user;", null);
        cursor.moveToFirst();
        GameUser user = new GameUser(cursor.getInt(2),
                cursor.getInt(3),
                cursor.getInt(4),
                cursor.getInt(5),
                cursor.getInt(6));
        return user;
    }

    public void storeUser(GameUser user) {
        mDataBase.execSQL("UPDATE user SET money = " + user.money + " WHERE _id = 1");
        mDataBase.execSQL("UPDATE user SET score = " + user.score + " WHERE _id = 1");
        if (user.score > user.best) {
            mDataBase.execSQL("UPDATE USER SET top_score = " + user.best + " WHERE _id = 1");

        }
        mDataBase.execSQL("UPDATE user SET loyal = " + user.loyal + " WHERE _id = 1");
        mDataBase.execSQL("UPDATE user SET police = " + user.police + " WHERE _id = 1");
    }

    public void storeUser(User user) {
        mDataBase.execSQL("UPDATE user SET name = " + '"' + user.name + '"' + " WHERE _id = 1");
        mDataBase.execSQL("UPDATE user SET user_id = " + '"' + user.id + '"' + " WHERE _id = 1");
        mDataBase.execSQL("UPDATE user SET money = 0 WHERE _id = 1");
        mDataBase.execSQL("UPDATE user SET score = 0 WHERE _id = 1");
        mDataBase.execSQL("UPDATE USER SET top_score = " + user.best + " WHERE _id = 1");
        mDataBase.execSQL("UPDATE user SET loyal = 0 WHERE _id = 1");
        mDataBase.execSQL("UPDATE user SET police = 0 WHERE _id = 1");
    }

    public void refresh() {
        mDataBase.execSQL("UPDATE user SET money = " + 1000 + " WHERE _id = 1");
        mDataBase.execSQL("UPDATE user SET score = " + 0 + " WHERE _id = 1");
        mDataBase.execSQL("UPDATE USER SET top_score = " + 0 + " WHERE _id = 1");
        mDataBase.execSQL("UPDATE user SET loyal = " + 0 + " WHERE _id = 1");
        mDataBase.execSQL("UPDATE user SET police = " + 0 + " WHERE _id = 1");
        mDataBase.execSQL("UPDATE shop SET isBought = false;");
    }

    public int getImg() {
        Cursor cursor = mDataBase.rawQuery("SELECT * FROM user;", null);
        cursor.moveToFirst();
        return cursor.getInt(8);
    }

    public void setAva(int ava) {
        mDataBase.execSQL("UPDATE user SET img = " + ava + " WHERE _id = 1");

    }

    public void setName(String toString) {
        mDataBase.execSQL("UPDATE user SET name = " + '"' + toString + '"' + " WHERE _id = 1");
    }

    public boolean isSoundOn() {
        Cursor cursor = mDataBase.rawQuery("SELECT * FROM user;", null);
        cursor.moveToFirst();
        return cursor.getInt(9) == 1;

    }

    public void changeSound() {
        if (isSoundOn()){
            mDataBase.execSQL("UPDATE user SET sound = 0 WHERE _id = 1");
            return;
        }
        mDataBase.execSQL("UPDATE user SET sound = 1 WHERE _id = 1");
    }
}