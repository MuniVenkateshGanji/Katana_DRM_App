package com.jntucep.c19_delhi.PatientProfile;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jntucep.c19_delhi.R;
import com.jntucep.c19_delhi.shareddoc;
import com.jntucep.c19_delhi.app.AppConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class PatientProfile extends AppCompatActivity {

    private String URL = AppConfig.URL + "doctor/doctor_apponment.php";
    private TextView no;
    private ProgressDialog dialog;

    private List<DataAdapter> adapterArrayList = new ArrayList<>();
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);
        ListView listView = findViewById(R.id.listView);
        no = findViewById(R.id.no);
        adapter = new CustomAdapter(PatientProfile.this, adapterArrayList);
        listView.setAdapter(adapter);

        // Load Json Data from Web
        loadDataFromWeb();
    }

    private void loadDataFromWeb() {

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getBoolean("type")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("users");

                        //parsing json
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                no.setVisibility(View.INVISIBLE);
                                JSONObject obj = jsonArray.getJSONObject(i);
                                DataAdapter adapter = new DataAdapter();
                                adapter.setId(obj.getInt("id"));
                                adapter.setName(obj.getString("name"));
                                adapter.setDate(obj.getString("date"));
                                adapter.setStatus(obj.getString("status"));

                                //add to array
                                adapterArrayList.add(adapter);

                            }
                            catch (JSONException ignored) {
                                no.setVisibility(View.VISIBLE);
                            }
                        }
                        // Update list view
                        adapter.notifyDataSetChanged();

                    } else {
                        final boolean type = jsonObject.getBoolean("type");
                        final String msg = jsonObject.getString("msg");

                        AlertDialog.Builder build = new AlertDialog.Builder(PatientProfile.this);
                        build.setMessage(msg);
                        build.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                if (!type) {
                                    finish();
                                }
                            }
                        });

                        AlertDialog alert = build.create();
                        alert.show();
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
                parameters.put("req_type", String.valueOf(4));
                parameters.put("user_id", ((shareddoc) PatientProfile.this.getApplication()).readData("user_id"));

                return parameters;
            }
        };
        // Adding request to request queue
        requestQueue.add(request);
    }

    public void hideDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
