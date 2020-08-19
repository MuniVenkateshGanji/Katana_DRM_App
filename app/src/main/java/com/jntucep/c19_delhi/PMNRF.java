package com.jntucep.c19_delhi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class PMNRF extends AppCompatActivity {

    TextView textView3,textView4;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_m_n_r_f);



        textView3 = findViewById(R.id.text3);
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(PMNRF.this, PMNRF3.class);
                startActivity(intent);

            }
        });
        textView4 = findViewById(R.id.pmcare1);
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PMNRF.this, upi2.class);
                startActivity(intent);
            }
        });

    }

}
