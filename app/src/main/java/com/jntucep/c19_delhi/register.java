package com.jntucep.c19_delhi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

public class register extends AppCompatActivity {

    private Spinner spinner;
    private EditText editText;
    Button Btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp);


        Btn = findViewById(R.id.button);

        spinner = findViewById(R.id.spinnerCountries);


        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));

        editText = findViewById(R.id.userPhone);


        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];

                String number = editText.getText().toString().trim();

                if (number.isEmpty() || number.length() < 10) {
                    editText.setError("Valid number is required");
                    editText.requestFocus();
                    return;
                }

                String phonenumber = "+" + code + number;

                Intent intent = new Intent(register.this, VerificationActivity_1.class);
                intent.putExtra("phonenumber", phonenumber);
                startActivity(intent);
                finish();
            }
        });
    }

        @Override
        protected void onStart () {
            super.onStart();

            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                Intent intent = new Intent(this, topbar.class);
                startActivity(intent);
                finish();
            }
        }
    }
