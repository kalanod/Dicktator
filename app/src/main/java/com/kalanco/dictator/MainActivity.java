package com.kalanco.dictator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kalanco.dictator.models.Event;
import com.kalanco.dictator.models.Events;
import com.kalanco.dictator.services.LocalDatabaseService;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ImageButton buttonSettings;
    TextView money, title, desc, res, t4;
    Event[] events;
    Button shop, btn1, btn2, next;
    Random random = new Random();
    Event currentEvent;
    View card;
    int myMoney;
    private LocalDatabaseService mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linker();
        bindDatabase();
        myMoney = Integer.parseInt(mDBHelper.getMoney());
        money.setText(Integer.toString(myMoney));
        try {
            AssetManager assetManager = getAssets();
            InputStream ims = assetManager.open("events.json");
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(ims);
            events = gson.fromJson(reader, Event[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ShopActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
            }
        });
        View.OnClickListener choiceLisener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn1:
                        if (myMoney >= currentEvent.price1) {
                            res.setText(currentEvent.res1);
                            myMoney -= currentEvent.price1;
                            mDBHelper.storeMoney(myMoney);
                            showEvent();
                        } else {
                            Toast.makeText(MainActivity.this, "Недостаточно золота", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case R.id.btn2:
                        if (myMoney >= currentEvent.price2) {
                            res.setText(currentEvent.res2);
                            myMoney -= currentEvent.price2;
                            mDBHelper.storeMoney(myMoney);
                            showEvent();
                        } else {
                            Toast.makeText(MainActivity.this, "Недостаточно золота", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }

            }
        };
        btn1.setOnClickListener(choiceLisener);
        btn2.setOnClickListener(choiceLisener);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newEvent();
            }
        });
        newEvent();
    }

    void showEvent() {
        money.setText(Integer.toString(myMoney));
        card.setVisibility(View.INVISIBLE);
        next.setVisibility(View.VISIBLE);
        shop.setVisibility(View.VISIBLE);
        t4.setVisibility(View.INVISIBLE);
    }

    void newEvent() {
        int a = random.nextInt(events.length);
        currentEvent = events[a];
        card.setVisibility(View.VISIBLE);
        title.setText(currentEvent.title);
        desc.setText(currentEvent.desc);
        btn1.setText(currentEvent.des1);
        btn2.setText(currentEvent.des2);
        res.setText("");
        shop.setVisibility(View.INVISIBLE);
        t4.setVisibility(View.VISIBLE);
        //Toast.makeText(this, Integer.toString(a), Toast.LENGTH_SHORT).show();

    }

    void linker() {
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        title = findViewById(R.id.text_title);
        res = findViewById(R.id.text_result);
        desc = findViewById(R.id.text_desc);
        money = findViewById(R.id.textMoney);
        shop = findViewById(R.id.btn_shop);
        card = findViewById(R.id.card);
        next = findViewById(R.id.btn_next);
        t4 = findViewById(R.id.textView4);
        //buttonBack = findViewById(R.id.buttonBack);
    }

    void bindDatabase() {
        mDBHelper = new LocalDatabaseService(this);
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }
    }
    public void onBackPressed() {
    }
}