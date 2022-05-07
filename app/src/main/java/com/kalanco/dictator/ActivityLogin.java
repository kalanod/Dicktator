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
import com.kalanco.dictator.services.UserService;

public class ActivityLogin extends AppCompatActivity {
    EditText fildEmail, fildPass;
    Button buttonLogin, buttonReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        linker();

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityLogin.this, ActivityReg.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserService.loginUser(fildEmail.getText().toString(), fildPass.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(ActivityLogin.this, "успешный вход", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ActivityLogin.this, ActivitySplash.class);
                                startActivity(intent);
                            }
                        }).addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        Toast.makeText(ActivityLogin.this, "Операция прервана", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ActivityLogin.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    void linker() {
        fildEmail = findViewById(R.id.fildEmail);
        fildPass = findViewById(R.id.fildPass);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonReg = findViewById(R.id.buttonReg);
    }
}