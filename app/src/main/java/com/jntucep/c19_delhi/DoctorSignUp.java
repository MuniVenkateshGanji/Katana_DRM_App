package com.jntucep.c19_delhi;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jntucep.c19_delhi.app.AppConfig;
import com.jntucep.c19_delhi.spinnerData.DistrictData;
import com.jntucep.c19_delhi.spinnerData.DivisionData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DoctorSignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ArrayList<String> specialist = new ArrayList<>();


    private String insertDoctorsUrl = AppConfig.URL + "doctor/insert.php";

    private DivisionData divisionData;
    private DistrictData districtData;

    private Spinner spinnerDivision, spinnerDistrict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_sign_up);

        spinnerDivision = (Spinner) findViewById(R.id.spinnerDivision);
        spinnerDistrict = (Spinner) findViewById(R.id.spinnerDistrict);

        ArrayAdapter<DivisionData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, new DivisionData[]{
                new DivisionData(0, "Select..."),
                new DivisionData(1, "Anantapur"),
                new DivisionData(2, "Chittoor"),
                new DivisionData(3, "East Godavari"),
                new DivisionData(4, "West Godavari"),
                new DivisionData(5, "Guntur"),
                new DivisionData(6, "YSR Kadapa"),
                new DivisionData(7, "Krishna"),
                new DivisionData(8, "Kurnool"),
                new DivisionData(9, "Nellore"),
                new DivisionData(10, "Prakasam"),
                new DivisionData(11, "Srikakulam"),
                new DivisionData(12, "Visakhapatnam"),
                new DivisionData(13, "Vizianagaram"),

        });

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDivision.setAdapter(spinnerArrayAdapter);

        spinnerDivision.setOnItemSelectedListener(this);

        findViewById(R.id.sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = ((EditText) findViewById(R.id.name)).getText().toString();
                final String email = ((EditText) findViewById(R.id.email)).getText().toString();
                final String password = ((EditText) findViewById(R.id.password)).getText().toString();
                final String phone = ((EditText) findViewById(R.id.phone)).getText().toString();
                final String degree = ((EditText) findViewById(R.id.degree)).getText().toString();
                final String latitude = ((EditText) findViewById(R.id.latitude)).getText().toString();
                final String longitude = ((EditText) findViewById(R.id.longitude)).getText().toString();
                final String address = ((EditText) findViewById(R.id.address)).getText().toString();


                if (!name.equals("") && !email.equals("") && !password.equals("") && !phone.equals("")  && !degree.equals("") && !latitude.equals("") && !longitude.equals("") && !address.equals("")) {
                    divisionData = (DivisionData) spinnerDivision.getSelectedItem();
                    districtData = (DistrictData) spinnerDistrict.getSelectedItem();
                    checkBoxListener();

                    if (!divisionData.name.equals("Select...") && !districtData.name.equals("Select...")) {

                        if (specialist.size() > 0) {

                            String specialists = "";

                            for (int i = 0; i < specialist.size(); i++) {
                                specialists = specialists + specialist.get(i);
                                if (i != specialist.size() - 1) {
                                    specialists = specialists + ",";
                                }
                            }

                            registerDoctors(name, email, password, phone, degree, latitude, longitude, address, specialists);

                            // Clear all values
                            specialist.clear();

                        } else
                            Toast.makeText(DoctorSignUp.this, "Nothing is selected", Toast.LENGTH_SHORT).show();

                    } else
                        Toast.makeText(DoctorSignUp.this, "Select any division and district", Toast.LENGTH_SHORT).show();

                } else
                    Toast.makeText(DoctorSignUp.this, "Please input every field", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * Function to store user in MySQL database will post params(name,
     * phone, latitude, longitude) to register url
     */
    private void registerDoctors(final String name, final String email, final String password, final String phone, final String degree, final String latitude, final String longitude, final String address, final String specialists) {

        StringRequest request = new StringRequest(Request.Method.POST, insertDoctorsUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    final boolean type = jsonObject.getBoolean("type");
                    final String msg = jsonObject.getString("msg");

                    AlertDialog.Builder build = new AlertDialog.Builder(DoctorSignUp.this);
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

                divisionData = (DivisionData) spinnerDivision.getSelectedItem();
                districtData = (DistrictData) spinnerDistrict.getSelectedItem();

                // Posting params to register url
                Map<String, String> parameters = new HashMap<>();
                parameters.put("name", name);
                parameters.put("email", email);
                parameters.put("pass", password);
                parameters.put("phone", phone);
                parameters.put("degree", degree);
                parameters.put("latitude", latitude);
                parameters.put("longitude", longitude);
                parameters.put("address", address);
                parameters.put("division_id", String.valueOf(divisionData.id));
                parameters.put("district_id", String.valueOf(districtData.id));
                parameters.put("spec_id", specialists);

                return parameters;
            }
        };
        // Adding request to request queue
        volleystuff.getInstance(this).addToRequestQueue(request);

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
            case "Anantapur": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(1, "Anantapur "),
                        new DistrictData(2, "Dharmavaram"),
                        new DistrictData(3, "Kadiri"),
                        new DistrictData(4, "Kalyandurg"),
                        new DistrictData(5, "Penukonda"),
                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }
            case "Chittoor": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(6, "Chittoor"),
                        new DistrictData(7, "Madanapalle"),
                        new DistrictData(8, "Triupati"),

                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }
            case "East Godavari": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(9, "Amalapuram"),
                        new DistrictData(10, "Etapaka"),
                        new DistrictData(11, "Kakinada"),
                        new DistrictData(12, "Peddapuram"),
                        new DistrictData(13, "Rajamundry"),
                        new DistrictData(14, "Ramachandrapuram"),
                        new DistrictData(15, "Rampachodavaram"),

                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }
            case "West Godavari": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(16, "ELURU"),
                        new DistrictData(17, "Jangareddygudem"),
                        new DistrictData(18, "Kovvur"),
                        new DistrictData(19, "Narasapuram"),

                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }
            case "Guntur": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(20, "Guntur"),
                        new DistrictData(21, "Tenali"),
                        new DistrictData(22, "Narasaraopet"),
                        new DistrictData(23, "Gurazala"),
                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }
            case "YSR Kadapa": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(24, "Kadapa"),
                        new DistrictData(25, "Rajampeta"),
                        new DistrictData(26, "Jamalamadugu"),
                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }
            case "Krishna": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(27, "Machilipatnam"),
                        new DistrictData(28, "Gudivada"),
                        new DistrictData(29, "Vijayawada"),
                        new DistrictData(30, "Nuzvid"),

                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }
            case "Kurnool": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(31, "Kurnool"),
                        new DistrictData(32, "Nandyal"),
                        new DistrictData(33, "Adoni"),
                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }
            case "Nellore": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(34, "Atmakur"),
                        new DistrictData(35, "Naidupeta"),
                        new DistrictData(36, "Nellore"),
                        new DistrictData(37, "Gudur"),
                        new DistrictData(38, "Kavali"),

                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }


            case "Prakasam": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(39, "Kandukur "),
                        new DistrictData(40, "Markapur "),
                        new DistrictData(41, "Ongole "),
                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }
            case "Srikakulam": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(42, "palakonda "),
                        new DistrictData(43, "Srikakulam  "),
                        new DistrictData(44, "Tekkali "),
                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }

            case "Visakhapatnam": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(45, "Anakapalle  "),
                        new DistrictData(46, "Narsipatnam "),
                        new DistrictData(47, "Paderu  "),
                        new DistrictData(48, "Visakhapatnam  "),

                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }

            case "Vizianagaram": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(63, "Parvathipuram   "),
                        new DistrictData(64, "Vizianagaram    "),


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


    /*
    Get all value from check box
    */
    private void checkBoxListener() {

        if (((CheckBox) findViewById(R.id.checkbox)).isChecked()) {
            specialist.add("1");
        }
        if (((CheckBox) findViewById(R.id.checkbox1)).isChecked()) {
            specialist.add("2");
        }
        if (((CheckBox) findViewById(R.id.checkbox2)).isChecked()) {
            specialist.add("3");
        }
        if (((CheckBox) findViewById(R.id.checkbox3)).isChecked()) {
            specialist.add("4");
        }
        if (((CheckBox) findViewById(R.id.checkbox4)).isChecked()) {
            specialist.add("5");
        }
        if (((CheckBox) findViewById(R.id.checkbox5)).isChecked()) {
            specialist.add("6");
        }
        if (((CheckBox) findViewById(R.id.checkbox6)).isChecked()) {
            specialist.add("7");
        }
        if (((CheckBox) findViewById(R.id.checkbox7)).isChecked()) {
            specialist.add("8");
        }
        if (((CheckBox) findViewById(R.id.checkbox8)).isChecked()) {
            specialist.add("9");
        }
        if (((CheckBox) findViewById(R.id.checkbox9)).isChecked()) {
            specialist.add("10");
        }
        if (((CheckBox) findViewById(R.id.checkbox10)).isChecked()) {
            specialist.add("11");
        }
        if (((CheckBox) findViewById(R.id.checkbox11)).isChecked()) {
            specialist.add("12");
        }
        if (((CheckBox) findViewById(R.id.checkbox12)).isChecked()) {
            specialist.add("13");
        }
        if (((CheckBox) findViewById(R.id.checkbox13)).isChecked()) {
            specialist.add("14");
        }
        if (((CheckBox) findViewById(R.id.checkbox14)).isChecked()) {
            specialist.add("15");
        }
        if (((CheckBox) findViewById(R.id.checkbox15)).isChecked()) {
            specialist.add("16");
        }
        if (((CheckBox) findViewById(R.id.checkbox16)).isChecked()) {
            specialist.add("17");
        }
        if (((CheckBox) findViewById(R.id.checkbox17)).isChecked()) {
            specialist.add("18");
        }
        if (((CheckBox) findViewById(R.id.checkbox18)).isChecked()) {
            specialist.add("19");
        }
        if (((CheckBox) findViewById(R.id.checkbox19)).isChecked()) {
            specialist.add("20");
        }
    }
}