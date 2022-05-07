package com.kalanco.dictator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.kalanco.dictator.services.BDService;
import com.kalanco.dictator.services.DatabaseService;
import com.kalanco.dictator.services.LocalDatabaseService;
import com.kalanco.dictator.services.UserService;

import java.io.IOException;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    ImageButton buttonSettings;
    TextView money;
    Button shop;
    //BDService dbHelper;
    //SQLiteDatabase db;
    private LocalDatabaseService mDBHelper;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linker();
        bindDatabase();
        money.setText(mDBHelper.getMoney());
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ActivityHome.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
            }
        });
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ShopActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
            }
        });
//        getDatabase();
//        Log.i("tag", Environment.getDataDirectory().toString());
//        ContentValues ins= new ContentValues();
//        ins.put("NAME", "andrey");
//        ins.put("PHONE", "kalan");
//        db.insert("todo", null, ins);
//        db.execSQL("INSERT into todo values (null,\"Kalan\", \"Andrey\")");
//        Cursor cursor = db.rawQuery("select * from todo where _id = ?", new String[] { "1" });
//        cursor.moveToPosition(0);
//        Toast.makeText(this, cursor.getString(1), Toast.LENGTH_SHORT).show();

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String product = "";

                Cursor cursor = mDb.rawQuery("SELECT * FROM user", null);
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    product += cursor.getString(1) + " | ";
                    cursor.moveToNext();
                }
                cursor.close();
                Toast.makeText(MainActivity.this, product, Toast.LENGTH_SHORT).show();
                String query = "INSERT INTO user (name, money) VALUES ('Tom', 11)";
                mDb.execSQL(query);
            }
        });
    }

    void linker() {
        buttonSettings = findViewById(R.id.buttonSettings);
        money = findViewById(R.id.textMoney);
        shop = findViewById(R.id.btn_shop);
        //buttonBack = findViewById(R.id.buttonBack);
    }

    //    void getDatabase() {
//        dbHelper = new BDService(getApplicationContext(), "DATABASE2", null, 1);
//        try {
//            db = dbHelper.getWritableDatabase();
//        } catch (SQLiteException ex) {
//            db = dbHelper.getReadableDatabase();
//        }
//    }
    void bindDatabase() {
        mDBHelper = new LocalDatabaseService(this);
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }
    }
}