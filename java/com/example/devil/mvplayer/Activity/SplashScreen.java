package com.example.devil.mvplayer.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.devil.mvplayer.Activity.home.Home;
import com.example.devil.mvplayer.Model.LdPreferences;
import com.example.devil.mvplayer.R;

public class SplashScreen extends AppCompatActivity {

Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        activity=this;
        LdPreferences.putString(activity,"repeat_status","false");


        Thread th = new Thread()
        {
            public void run()
            {

                try {
                    Thread.sleep(4000);
                    if(LdPreferences.readString(activity,"login_status").equalsIgnoreCase("true")) {
                        Intent in = new Intent(SplashScreen.this, Home.class);
                        startActivity(in);
                        finish();
                    }
                    else
                    {
                        Intent it= new Intent(SplashScreen.this,Login.class);
                        startActivity(it);
                        finish();
                    }
                } catch (InterruptedException ae) {
                    ae.printStackTrace();
                }
            }

        };

        th.start();
    }
}
