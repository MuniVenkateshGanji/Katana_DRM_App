package com.jntucep.c19_delhi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class doctor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        TextView btnDoctor =  findViewById(R.id.btnDoctor);
        TextView btnPatient =  findViewById(R.id.btnPatient);
        TextView btnSearch =  findViewById(R.id.btnSearch);
        TextView btnAdmin =  findViewById(R.id.btnAdmin);



        btnDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(doctor.this, DoctorLogin.class));
            }
        });

        btnPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean Registered;
                final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(doctor.this);
                Registered = sharedPref.getBoolean("Registered", false);

                if (!Registered) {
                    startActivity(new Intent(doctor.this, PatientLogin.class));

                } else {
                    startActivity(new Intent(doctor.this, PatientLogin.class));

                }
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(doctor.this, SearchOption.class));
            }
        });

        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(doctor.this, AdminLogin.class));
            }
        });


    }


    }

