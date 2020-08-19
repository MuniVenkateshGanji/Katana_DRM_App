package com.jntucep.c19_delhi;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Transport extends AppCompatActivity{
    private String insertDoctorsUrl = "https://unblenched-shoes.000webhostapp.com/transportpass.php";
    TextView one,status;
    private EditText name, email, phone, Regd, Licence, cause, address;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transportpass);
        one = findViewById(R.id.one);
        status= findViewById(R.id.status);
        status.setVisibility(View.GONE);
        one.setVisibility(View.GONE);
        findViewById(R.id.sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = ((EditText) findViewById(R.id.name)).getText().toString();
                final String email = ((EditText) findViewById(R.id.email)).getText().toString();

                final String phone = ((EditText) findViewById(R.id.phone)).getText().toString();
                final String degree = ((EditText) findViewById(R.id.degree)).getText().toString();
                final String latitude = ((EditText) findViewById(R.id.latitude)).getText().toString();
                final String longitude = ((EditText) findViewById(R.id.longitude)).getText().toString();
                final String address = ((EditText) findViewById(R.id.address)).getText().toString();
                status.setVisibility(View.VISIBLE);
                one.setVisibility(View.VISIBLE);
                if (!name.equals("") && !email.equals("") && !phone.equals("")  && !degree.equals("") && !latitude.equals("") && !longitude.equals("") && !address.equals("")) {


                    Calendar c = Calendar.getInstance();
                    int day;
                    for(day=c.get(Calendar.DAY_OF_WEEK);day<7;day++){
                        if(day==c.SUNDAY) {
                            status.setText("ISSUED");
                            status.setTextColor(getResources().getColor(R.color.green));
                        }
                    }





                    registerDoctors(name, email, phone, degree, latitude, longitude, address);

                    // Clear all values


                    Toast.makeText(Transport.this,"You will an Mail in 3 days",Toast.LENGTH_SHORT).show();


                } else
                    Toast.makeText(Transport.this, "Please input every field", Toast.LENGTH_SHORT).show();
            }
        });
    }





    /**
     * Function to store user in MySQL database will post params(name,
     * phone, latitude, longitude) to register url
     */
    private void registerDoctors(final String name, final String email, final String phone, final String degree, final String latitude, final String longitude, final String address) {

        StringRequest request = new StringRequest(Request.Method.POST, insertDoctorsUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    final boolean type = jsonObject.getBoolean("type");
                    final String msg = jsonObject.getString("msg");

                    AlertDialog.Builder build = new AlertDialog.Builder(Transport.this);
                    build.setMessage(msg);
                    build.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            if (type) {
                                finish();
                            }
                        }
                    });

                    AlertDialog alert = build.create();
                    alert.show();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Json Error", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Something went wrong! Please try again.", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {



                // Posting params to register url
                Map<String, String> parameters = new HashMap<>();
                parameters.put("name", name);
                parameters.put("email", email);
                parameters.put("phone", phone);
                parameters.put("degree", degree);
                parameters.put("latitude", latitude);
                parameters.put("longitude", longitude);
                parameters.put("address", address);


                return parameters;
            }
        };
        // Adding request to request queue
        volleystuff.getInstance(this).addToRequestQueue(request);

    }

    }
