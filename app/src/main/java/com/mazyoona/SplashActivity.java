package com.mazyoona;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splashscreen);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */

                SharedPreferences prefs = getSharedPreferences(Activity_Login.MY_PREFS_NAME, MODE_PRIVATE);
                String name = prefs.getString("username", "null");//"No name defined" is the default value.
                System.out.println("name in pref"+name);
                if (name.equals("") || name.equals("null")){
                    Intent mainIntent = new Intent(SplashActivity.this,Entry.class);
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();
                }else {
                    Intent mainIntent = new Intent(SplashActivity.this,HomePage.class);
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}