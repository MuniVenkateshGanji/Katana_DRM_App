package com.jntucep.c19_delhi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.TimeZone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class E_pass extends AppCompatActivity {
    private Button transport,essential;
    TextView status;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passsystem);
        transport = findViewById(R.id.Transport);
        essential = findViewById(R.id.Essential);
        status = findViewById(R.id.status);

        transport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(E_pass.this,Transport.class);
                startActivity(intent);
            }
        });
        essential.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(E_pass.this,essential.class);
                startActivity(intent);
            }
        });



    }
}
