package com.example.jewelsgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class login extends AppCompatActivity {
    TextView signup;
    EditText logmail, logpassword;
    Button giris;
    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        databaseHelper = new DatabaseHelper(this);
        giris = findViewById(R.id.giris);
        logmail = findViewById(R.id.logmail);
        logpassword = findViewById(R.id.logpassword);
        signup = findViewById(R.id.signup);


        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = logmail.getText().toString();
                String password = logpassword.getText().toString();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(login.this, "Tüm alanları doldurun.", Toast.LENGTH_SHORT).show();
                }else {
                    int userId = databaseHelper.getUserId(email, password); // Kullanıcı kimliğini al
                    if (userId != -1) {

                    Intent intent = new Intent(login.this, levelPage.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(login.this, "Hatalı email veya şifre!", Toast.LENGTH_SHORT).show();
                }
              }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, signUp.class);
                startActivity(intent);
                finish();
            }
        });


    }
}