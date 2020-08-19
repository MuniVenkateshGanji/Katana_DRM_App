package com.jntucep.c19_delhi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CMRF extends AppCompatActivity {

    TextView textView2,textView4,upi;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_m_r_f);
        textView2 = findViewById(R.id.text2);
       
        textView4 = findViewById(R.id.text4);
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CMRF.this, PMNRF2.class);
                startActivity(intent);

            }
        });
        upi= findViewById(R.id.upi);
        upi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CMRF.this, upi.class);
                startActivity(intent);
            }
        });
    }
}
