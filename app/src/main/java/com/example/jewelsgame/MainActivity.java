package com.example.jewelsgame;
import static com.example.jewelsgame.Constants.*;

import static com.example.jewelsgame.Constants.cellWidth;
import static com.example.jewelsgame.Constants.drawX;
import static com.example.jewelsgame.Constants.drawY;
import static com.example.jewelsgame.Constants.screenHeight;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;



import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        cellWidth = screenWidth / 9;
        drawX = (float) (screenWidth - cellWidth * 9) / 2;
        drawY = cellWidth * 3;
        setContentView(R.layout.activity_main);
        FrameLayout root_layout = findViewById(R.id.root_layout);
        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);
        GameView gameView = new GameView(this);
        root_layout.addView(gameView);
        gameView.setLayoutParams(params);
    }

}

