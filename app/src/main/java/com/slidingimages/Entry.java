package com.slidingimages;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Entry extends Activity {

    protected FrameLayout wel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entrylayout);
        String fontPath = "fonts/arial.ttf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        wel = (FrameLayout)findViewById(R.id.welcome);

        Button logger = (Button)findViewById(R.id.button3);
        Button registering = (Button)findViewById(R.id.button4);
        TextView skip=(TextView) findViewById(R.id.skip_login);
        logger.setTypeface(tf);
        registering.setTypeface(tf);
        skip.setTypeface(tf);


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
}
