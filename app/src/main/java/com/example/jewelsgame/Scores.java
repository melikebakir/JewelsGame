package com.example.jewelsgame;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Scores extends AppCompatActivity {
    ImageView slout,back;
    private SimpleCursorAdapter adapter;

    private DatabaseHelper dbHelper;
    private ListView listView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Scores.this,levelPage.class);
                startActivity(intent);
                finish();
            }
        });



        slout=findViewById(R.id.slout);
        slout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutDialog();

            }
        });


        listView = findViewById(R.id.scores_list_view);
        dbHelper = new DatabaseHelper(this);

        loadScores();
    }


    private void loadScores() {
        Cursor cursor = dbHelper.getScores();
        String[] from = new String[]{"personName", "score"};
        int[] to = new int[]{R.id.person_name, R.id.score};

        if (adapter == null) {
            adapter = new SimpleCursorAdapter(
                    this,
                    R.layout.score_item,
                    cursor,
                    from,
                    to,
                    0
            );
            listView.setAdapter(adapter);
        } else {
            adapter.changeCursor(cursor);
        }
    }

    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Scores.this);
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
        //DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.close();

        Intent intent = new Intent(Scores.this, login.class);
        startActivity(intent);
        finish();
    }
}