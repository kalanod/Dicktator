package com.kalanco.dictator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.kalanco.dictator.adapters.PointAdapter;
import com.kalanco.dictator.models.Event;
import com.kalanco.dictator.models.GameUser;
import com.kalanco.dictator.services.LocalDatabaseService;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ImageButton buttonSettings, btnBack;
    TextView money, title, desc, res, t4, score;
    Event[] events;
    Button shop, btn1, btn2, next;
    Random random = new Random();
    Event currentEvent;
    View card;
    GameUser user;
    PointAdapter pointAdapter, pointAdapter2;
    RecyclerView list, list2;
    ImageView img;
    ConstraintLayout resLayout;
    private SoundPool mSoundPool;
    private int mSoundId = 1;
    private int mStreamId;
    private LocalDatabaseService mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linker();
        bindDatabase();
        user = mDBHelper.getGameUser();


        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 100);


        money.setText(Integer.toString(user.money));
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
                        if (user.money >= currentEvent.price1) {
                            res.setText(currentEvent.res1);
                            user.money -= currentEvent.price1;
                            user.score += currentEvent.xp1;
                            user.loyal += currentEvent.loyal1;
                            if (user.loyal < 0) {
                                user.loyal = 0;
                            }
                            if (user.loyal > 5) {
                                user.loyal = 5;
                            }
                            user.police += currentEvent.police1;
                            if (user.police < 0) {
                                user.police = 0;
                            }
                            if (user.police > 5) {
                                user.police = 5;
                            }
                            showEvent();
                        } else {
                            Toast.makeText(MainActivity.this, "Недостаточно золота", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.btn2:
                        if (user.money >= currentEvent.price2) {
                            res.setText(currentEvent.res2);
                            user.money -= currentEvent.price2;
                            user.score += currentEvent.xp2;
                            user.loyal += currentEvent.loyal2;
                            if (user.loyal < 0) {
                                user.loyal = 0;
                            }
                            if (user.loyal > 5) {
                                user.loyal = 5;
                            }
                            user.police += currentEvent.police2;
                            if (user.police < 0) {
                                user.police = 0;
                            }
                            if (user.police > 5) {
                                user.police = 5;
                            }
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

        pointAdapter = new PointAdapter(mDBHelper.getLoyal());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        list.setAdapter(pointAdapter);
        list.setLayoutManager(layoutManager);

        pointAdapter2 = new PointAdapter(mDBHelper.getPolice());
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        list2.setAdapter(pointAdapter2);
        list2.setLayoutManager(layoutManager2);

        newEvent();


    }

    public void onStart() {
        mSoundPool.load(this, R.raw.background, 1);
        mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                mStreamId = mSoundPool.play(mSoundId, 1, 1, 0, 999, 1);
            }
        });
        super.onStart();
    }

    public void onStop() {
        mSoundPool.stop(mStreamId);

        super.onStop();
    }

    void showEvent() {
        mDBHelper.storeUser(user);
        score.setText(Integer.toString(user.score));
        money.setText(Integer.toString(user.money));
        card.setVisibility(View.INVISIBLE);
        next.setVisibility(View.VISIBLE);
        shop.setVisibility(View.VISIBLE);
        t4.setVisibility(View.INVISIBLE);
        resLayout.setVisibility(View.VISIBLE);

        if (user.best < user.score) {
            user.best = user.score;
        }
        pointAdapter.update(mDBHelper.getLoyal());
        pointAdapter2.update(mDBHelper.getPolice());

        Toast.makeText(this, Integer.toString(mDBHelper.getPolice()) + " | " + Integer.toString(user.police), Toast.LENGTH_SHORT).show();

    }

    void newEvent() {
        Toast.makeText(this, String.format(Locale.US, "%d", mDBHelper.getBest())
                , Toast.LENGTH_SHORT).show();
        mDBHelper.storeUser(user);
        score.setText(Integer.toString(user.score));
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
        user.money -= 100;
        money.setText(Integer.toString(user.money));
        img.setImageDrawable(getImage(a));
        resLayout.setVisibility(View.INVISIBLE);
        chekMoney();
        //Toast.makeText(this, Integer.toString(a), Toast.LENGTH_SHORT).show();

    }

    private Drawable getImage(int a) {
        switch (a) {
            case 1:
                return getDrawable(R.drawable.e1);
            case 2:
                return getDrawable(R.drawable.e2);
            case 3:
                return getDrawable(R.drawable.e3);
            case 4:
                return getDrawable(R.drawable.e4);
            case 5:
                return getDrawable(R.drawable.e5);
            case 6:
                return getDrawable(R.drawable.e6);
            case 7:
                return getDrawable(R.drawable.e7);

        }
        return getDrawable(R.drawable.e8);
    }

    private void chekMoney() {
        if (user.money <= 0) {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
            builder.setTitle("О нет! в казне закончились деньги");
            builder.setPositiveButton("заного!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    user.money = 1100;
                    user.score = 0;
                    user.loyal = 0;
                    user.police = 0;
                    newEvent();
                }
            });
            builder.setNegativeButton("выход", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(MainActivity.this, ActivityHome.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }
            });
            builder.show();
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ActivityHome.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
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
        score = findViewById(R.id.score);
        btnBack = findViewById(R.id.btn_back2);
        list = findViewById(R.id.list);
        img = findViewById(R.id.img);
        resLayout = findViewById(R.id.layout_result);
        list2 = findViewById(R.id.list2);
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

    public void onDestroy() {
        mDBHelper.close();
        super.onDestroy();
    }
}