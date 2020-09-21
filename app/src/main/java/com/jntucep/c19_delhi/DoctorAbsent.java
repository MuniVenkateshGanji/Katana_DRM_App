package com.jntucep.c19_delhi;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jntucep.c19_delhi.app.AppConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class DoctorAbsent extends AppCompatActivity {

    private String URL = AppConfig.URL + "doctor/doctor_apponment.php";
    private String STATUS_URL = AppConfig.URL + "doctor/doctor_available_status.php";

    private TextView tvDays, tvDate, tvRange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_absent);

        tvDays = (TextView) findViewById(R.id.tvDays);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvRange = (TextView) findViewById(R.id.tvRange);

        final EditText edtAppointmentDate = (EditText) findViewById(R.id.edtAppointmentDate);
        Button btnSendRequest = (Button) findViewById(R.id.btnSendRequest);

        final String doc_id = String.valueOf(getIntent().getExtras().getInt("id"));

        // Load doctor status
        loadDataFromWeb(doc_id);

        btnSendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strDate = edtAppointmentDate.getText().toString();

                if (!strDate.isEmpty()) {

                    String user_id = ((shareddoc) DoctorAbsent.this.getApplicationContext()).readData("user_id");

                    makeAppointment(doc_id, user_id, strDate);


                } else
                    Toast.makeText(getApplicationContext(), "Input your appointment date", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Update Status
    private void makeAppointment(final String doc_id, final String user_id, final String date) {


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    boolean error = jsonObject.getBoolean("error");
                    String msg = jsonObject.getString("msg");

                    if (!error) {
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        finish();
                    }

                } catch (JSONException ignored) {
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Something went wrong! Please try again.", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> parameters = new HashMap<>();
                parameters.put("req_type", String.valueOf(1));
                parameters.put("doc_id", doc_id);
                parameters.put("user_id", user_id);
                parameters.put("date", date);

                return parameters;
            }
        };
        // Adding request to request queue
        volleystuff.getInstance(this).addToRequestQueue(request);
    }

    private void loadDataFromWeb(final String doc_id) {


        StringRequest request = new StringRequest(Request.Method.POST, STATUS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("Response", response);

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    // Handel days
                    if (jsonObject.getString("days").equals("0"))
                        tvDays.setText("Available all days");
                    else if (jsonObject.getString("days").equals("-1"))
                        tvDays.setText("No day is set");
                    else
                        tvDays.setText("Not available on " + jsonObject.getString("days"));

                    // Handel dates
                    if (jsonObject.getString("dates").equals("0"))
                        tvDate.setText("Available all date");
                    else if (jsonObject.getString("dates").equals("-1"))
                        tvDate.setText("No date is set");
                    else
                        tvDate.setText("Not available on " + jsonObject.getString("dates"));

                    //Handel date range
                    if (jsonObject.getString("range").equals("0"))
                        tvRange.setText("No date range is set");
                    else if (jsonObject.getString("range").equals("-1"))
                        tvRange.setText("No date range is set");
                    else
                        tvRange.setText("Not available from " + jsonObject.getString("range"));

                } catch (JSONException ignored) {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Something went wrong! Please try again.", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> parameters = new HashMap<>();
                parameters.put("doc_id", doc_id);

                return parameters;
            }
        };
        // Adding request to request queue
        volleystuff.getInstance(this).addToRequestQueue(request);
    }
}
