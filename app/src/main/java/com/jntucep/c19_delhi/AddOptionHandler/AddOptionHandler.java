package com.jntucep.c19_delhi.AddOptionHandler;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jntucep.c19_delhi.R;
import com.jntucep.c19_delhi.app.AppConfig;
import com.jntucep.c19_delhi.spinnerData.DistrictData;
import com.jntucep.c19_delhi.spinnerData.DivisionData;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AddOptionHandler extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String URL = AppConfig.URL + "doctor/insert_option_handler.php";

    private DivisionData divisionData;
    private DistrictData districtData;

    private EditText name, phone, address;
    private Spinner spinnerDivision, spinnerDistrict;

    private String req_type = null, tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set Custom title
        setContentView(R.layout.activity_add_option_handler);

        req_type = getIntent().getExtras().getString("req_type");
        tableName = getIntent().getExtras().getString("Table Name");

        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        address = (EditText) findViewById(R.id.address);
        spinnerDivision = (Spinner) findViewById(R.id.spinnerDivision);
        spinnerDistrict = (Spinner) findViewById(R.id.spinnerDistrict);
        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);

        ArrayAdapter<DivisionData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, new DivisionData[]{
                new DivisionData(0, "Select..."),
                new DivisionData(1, "New Delhi"),
                new DivisionData(2, "North Delhi"),
                new DivisionData(3, "North West Delhi"),
                new DivisionData(4, "West Delhi	"),
                new DivisionData(5, "South West Delhi"),
                new DivisionData(6, "South Delhi"),
                new DivisionData(7, "South East Delhi"),
                new DivisionData(8, "Central Delhi"),
                new DivisionData(9, "North East Delhi"),
                new DivisionData(10, "Shahdara"),
                new DivisionData(11, "East Delhi"),

        });

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDivision.setAdapter(spinnerArrayAdapter);

        spinnerDivision.setOnItemSelectedListener(this);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                divisionData = (DivisionData) spinnerDivision.getSelectedItem();
                districtData = (DistrictData) spinnerDistrict.getSelectedItem();

                String strName = name.getText().toString();
                String strPhone = phone.getText().toString();
                String strAddress = address.getText().toString();

                if (!strName.isEmpty() && !strPhone.isEmpty() && !strAddress.isEmpty()) {

                    if (!divisionData.name.equals("Select...") && !districtData.name.equals("Select...")) {

                        submitData(strName, strPhone, strAddress, districtData.id, divisionData.id);

                    } else
                        Toast.makeText(AddOptionHandler.this, "Select any division and district", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(AddOptionHandler.this, "Please input every field", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        // Get the currently selected State object from the spinner
        divisionData = (DivisionData) spinnerDivision.getSelectedItem();

        switch (divisionData.name) {
            case "Select...": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select...")
                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }
            case "New Delhi": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(1, "Chanakyapuri"),
                        new DistrictData(2, "Delhi Cantonment"),
                        new DistrictData(3, "Vasant Vihar"),
                        new DistrictData(4, "Other"),




                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }
            case "North Delhi": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(5, "Model Town"),
                        new DistrictData(6, "Narela"),
                        new DistrictData(7, "Alipur"),
                        new DistrictData(8, "Other"),



                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }
            case "North West Delhi": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(9, "Rohini"),
                        new DistrictData(10, "Kanjhawala"),
                        new DistrictData(11, "Saraswati Vihar"),
                        new DistrictData(12, "Other"),



                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }
            case "West Delhi": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(13, "Patel Nagar"),
                        new DistrictData(14, "Punjabi Bagh"),
                        new DistrictData(15, "Rajouri Garden"),
                        new DistrictData(16, "Other"),



                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }
            case "South West Delhi": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(17, "Dwarka"),
                        new DistrictData(18, "Najafgarh"),
                        new DistrictData(19, "Kapashera"),
                        new DistrictData(20, "Other"),




                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }
            case "South Delhi": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(21, "Saket"),
                        new DistrictData(22, "Hauz Khas"),
                        new DistrictData(23, "Mehrauli"),
                        new DistrictData(24, "Other"),


                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }
            case "South East Delhi": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(25, "Defence Colony"),
                        new DistrictData(26, "Kalkaji"),
                        new DistrictData(27, "Sarita Vihar"),
                        new DistrictData(28, "Other"),




                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }
            case "Central Delhi": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(29, "Kotwali"),
                        new DistrictData(30, "Civil Lines"),
                        new DistrictData(31, "Karol Bagh"),
                        new DistrictData(32, "Other"),

                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }
            case "North East Delhi": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(33, "Seelampur"),
                        new DistrictData(34, "Yamuna Vihar"),
                        new DistrictData(35, "Karawal Nagar"),
                        new DistrictData(36, "Other"),


                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }


            case "Shahdara": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(37, "Shahdara"),
                        new DistrictData(38, "Seemapuri"),
                        new DistrictData(39, "Vivek Vihar"),
                        new DistrictData(40, "Other"),


                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }
            case "East Delhi": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(41, "Gandhi Nagar"),
                        new DistrictData(42, "Preet Vihar"),
                        new DistrictData(43, "Mayur Vihar"),
                        new DistrictData(44, "Other"),



                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void submitData(final String strName, final String strPhone, final String strAddress, final int district_id, final int division_id) {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getBoolean("type")) {
                        final String msg = jsonObject.getString("msg");

                        AlertDialog.Builder build = new AlertDialog.Builder(AddOptionHandler.this);
                        build.setMessage(msg);
                        build.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                finish();
                            }
                        });

                        AlertDialog alert = build.create();
                        alert.show();

                    } else {
                        final String msg = jsonObject.getString("msg");

                        AlertDialog.Builder build = new AlertDialog.Builder(AddOptionHandler.this);
                        build.setMessage(msg);
                        build.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
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
                parameters.put("req_type", req_type);
                parameters.put("name", strName);
                parameters.put("phone", strPhone);
                parameters.put("address", strAddress);
                parameters.put("district_id", String.valueOf(district_id));
                parameters.put("division_id", String.valueOf(division_id));

                return parameters;
            }
        };
        // Adding request to request queue
        requestQueue.add(request);
    }
}
