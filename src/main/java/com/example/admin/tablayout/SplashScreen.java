package com.example.admin.tablayout;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class SplashScreen extends AppCompatActivity {
    private ProgressBar progressBar;

    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        progressBar=(ProgressBar)findViewById(R.id.pBar);
        activity = this;


        Thread th = new Thread() {
            public void run() {
                doWork();
                startApp();
                finish();
            }


        };

        th.start();
    }

    public void startApp()
    {
        Intent in = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(in);
        finish();

    }

public void doWork(){

    for (int progress=0; progress<100; progress+=10) {
        try {
            Thread.sleep(400);
            progressBar.setProgress(progress);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}}
