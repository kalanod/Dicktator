package com.kalanco.dictator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.kalanco.dictator.services.LocalDatabaseService;
import com.kalanco.dictator.services.UserService;

import java.io.IOException;

public class ActivityReg extends AppCompatActivity {
    EditText fildEmail, fildPass, fildName;
    Button buttonLogin, buttonReg;
    private LocalDatabaseService mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        linker();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityReg.this, ActivityLogin.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);

            }
        });
        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserService.storeUser(
                        fildName.getText().toString(),
                        fildPass.getText().toString(),
                        fildEmail.getText().toString(), mDBHelper)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(ActivityReg.this, "успешная регистрация", Toast.LENGTH_SHORT).show();
                                LocalDatabaseService mDBHelper = new LocalDatabaseService(ActivityReg.this);
                                mDBHelper.copyDataBase();
                                mDBHelper.close();
                                Intent intent = new Intent(ActivityReg.this, ActivitySplash.class);
                                startActivity(intent);


                            }
                        }).addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        Toast.makeText(ActivityReg.this, "Операция прервана", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ActivityReg.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void onStart() {
        super.onStart();
        bindDatabase();
    }

    public void onStop() {
        mDBHelper.close();
        super.onStop();
    }

    void linker() {
        fildEmail = findViewById(R.id.fildEmail);
        fildPass = findViewById(R.id.fildPass);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonReg = findViewById(R.id.buttonReg);
        fildName = findViewById(R.id.fildName);
    }

    void bindDatabase() {
        mDBHelper = new LocalDatabaseService(this);
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }
    }
}