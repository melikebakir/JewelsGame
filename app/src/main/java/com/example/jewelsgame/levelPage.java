package com.example.jewelsgame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class levelPage extends AppCompatActivity {
    ImageView logout,lvl1,lvl2,lvl3,kupa;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_page);
        lvl1= findViewById(R.id.lvl1);
        lvl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(levelPage.this,MainActivity.class);
                startActivity(intent);
                finish();
                //startGameActivity(1);
            }
        });

        lvl2= findViewById(R.id.lvl2);
        lvl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(levelPage.this,MainActivity.class);
                startActivity(intent);
                finish();
                //startGameActivity(2);
            }
        });
        lvl3= findViewById(R.id.lvl3);
        lvl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(levelPage.this,MainActivity.class);
                startActivity(intent);
                finish();


                // startGameActivity(3);
            }
        });
        kupa=findViewById(R.id.kupa);
        kupa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(levelPage.this,Scores.class);
                startActivity(intent);
                finish();

            }
        });


        logout= findViewById(R.id.logoutimg);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutDialog();

            }
        });
    }

    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(levelPage.this);
        builder.setTitle("Oturumu kapat")
                .setMessage("Oturumu kapatmak istediğinize emin misiniz?")
                .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        signOutAndRedirectToLogin();
                    }
                })
                .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void signOutAndRedirectToLogin() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.close();

        Intent intent = new Intent(levelPage.this, login.class);
        startActivity(intent);
        finish();
    }
}