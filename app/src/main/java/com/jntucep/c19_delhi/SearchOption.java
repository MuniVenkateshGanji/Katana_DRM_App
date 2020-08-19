package com.jntucep.c19_delhi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jntucep.c19_delhi.SearchOptionHandler.SearchOptionHandler;

import androidx.appcompat.app.AppCompatActivity;

public class SearchOption extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_option);

        Button doctorSearch = (Button) findViewById(R.id.btnDoctorSearch);
        Button hospitalSearch = (Button) findViewById(R.id.btnHospitalSearch);
        Button ambulanceSearch = (Button) findViewById(R.id.btnAmbulanceSearch);
        Button diagnosticSearch = (Button) findViewById(R.id.btnDiagnosticSearch);

        doctorSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchOption.this, SearchDoctor.class));
            }
        });

        hospitalSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchOptionHandler.class);
                intent.putExtra("URL", "hospital.php");
                startActivity(intent);
            }
        });

        ambulanceSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchOptionHandler.class);
                intent.putExtra("URL", "ambulance.php");
                startActivity(intent);
            }
        });

        diagnosticSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchOptionHandler.class);
                intent.putExtra("URL", "diagnostic_center.php");
                startActivity(intent);
            }
        });

    }
}
