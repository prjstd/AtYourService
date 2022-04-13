package com.example.atyourservice;

import static java.lang.Thread.sleep;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    ProgressBar pg;
    int prog = 0;
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        pg = findViewById(R.id.progressBar);
        pg.setMax(100);
        pg.getProgressDrawable().setColorFilter(
                Color.parseColor("#009900"), android.graphics.PorterDuff.Mode.SRC_IN);

        Animation();
        StartHomeScreen();
    }

    public void Animation(){
        title =  findViewById(R.id.txt2);
        title.setTranslationX(400);
        title.setAlpha(0);
        title.animate().translationX(0).alpha(1).
                setDuration(800).setStartDelay(500).start();
    }

    public void StartHomeScreen(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    for(int i=0; i<4; i++){
                        sleep(400);
                        prog+=25;
                        pg.setProgress(prog);
                    }

                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
    }