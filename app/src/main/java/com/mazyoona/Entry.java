package com.mazyoona;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Entry extends Activity {

    protected FrameLayout wel;
    Button logger,registering;
    TextView skip;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entrylayout);
        String fontPath = "fonts/arial.ttf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        wel = (FrameLayout)findViewById(R.id.welcome);

         logger = (Button)findViewById(R.id.button3);
         registering = (Button)findViewById(R.id.button4);
         skip=(TextView) findViewById(R.id.skip_login);
        logger.setTypeface(tf);
        registering.setTypeface(tf);
        skip.setTypeface(tf);
        StartAnimations();

        logger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent l = new Intent(getApplicationContext(), Activity_Login.class);
                startActivity(l);
            }
        });

        registering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent r = new Intent(getApplicationContext(), Activity_Register.class);
                startActivity(r);
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity_Login.userId="0";
                Activity_Login.username="temp";
                Intent intent1 = new Intent(Entry.this, HomePage.class);
                startActivity(intent1);
            }
        });
    }
    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();

        FrameLayout linlay=(FrameLayout) findViewById(R.id.welcome);
        linlay.clearAnimation();
        linlay.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();

//        ImageView splashimg = (ImageView) findViewById(R.id.splashview);

        logger.clearAnimation();
        logger.startAnimation(anim);
        registering.clearAnimation();
        registering.startAnimation(anim);
        skip.startAnimation(anim);

//        timer = new Timer();
//        timer.schedule(new TimerTask(){
//            @Override
//            public void run() {
//                Intent home_page = new Intent(getApplicationContext(),WelcomeActivity.class);
//                startActivity(home_page);
//                finish();
//            }}, SPLASH_TIME_OUT);
//        this.scheduled=true;
    }
}
