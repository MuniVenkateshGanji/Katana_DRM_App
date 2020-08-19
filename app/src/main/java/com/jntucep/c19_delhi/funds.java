package com.jntucep.c19_delhi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class funds extends AppCompatActivity {
    Spinner sp_Funds;

    ArrayList<String> arrayList_Funds;
    ArrayAdapter<String> arrayAdapter_Funds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funds);

        sp_Funds = findViewById(R.id.sp_Funds);
        arrayList_Funds = new ArrayList<>();
        arrayList_Funds.add("SELECT");
        arrayList_Funds.add("PM CARES");
        arrayList_Funds.add("PM NATIONAL RELIEF FUND");
        arrayList_Funds.add("CM RELIEF FUND");
        arrayList_Funds.add("Orphans");
        arrayAdapter_Funds=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,arrayList_Funds);

        sp_Funds.setAdapter(arrayAdapter_Funds);

        sp_Funds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==1) {



                    Intent intent = new Intent(funds.this,
                            PMCARES.class);
                    startActivity(intent);

                }
                if(position==2) {



                    Intent intent = new Intent(funds.this,
                            PMNRF.class);
                    startActivity(intent);

                }
                if(position==3) {



                    Intent intent = new Intent(funds.this,
                            CMRF.class);
                    startActivity(intent);

                }
                if(position==4) {



                    Intent intent = new Intent(funds.this,
                            orphans.class);
                    startActivity(intent);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




    }
}
