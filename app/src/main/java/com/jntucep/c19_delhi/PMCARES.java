package com.jntucep.c19_delhi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PMCARES extends AppCompatActivity {

    TextView textView1,txt;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_m_c_a_r_e_s);

        textView1 = findViewById(R.id.text1);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PMCARES.this, PMCARES2.class);
                startActivity(intent);

            }
        });

        txt =findViewById(R.id.pmcare);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PMCARES.this, upi1.class);
                startActivity(intent);
            }
        });

    }
}
