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
                new DivisionData(0, "Select..."),
                new DivisionData(1, "Ambala"),
                new DivisionData(2, "Bhiwani"),
                new DivisionData(3, "Charkhi Dadri"),
                new DivisionData(4, "Faridabad"),
                new DivisionData(5, "Fatehabad"),
                new DivisionData(6, "Gurugram"),
                new DivisionData(7, "Hisar"),
                new DivisionData(8, "Yamunanagar"),
                new DivisionData(9, "Jhajjar"),
                new DivisionData(10, "Jind"),
                new DivisionData(11, "Kaithal"),
                new DivisionData(12, "Karnal"),
                new DivisionData(13, "Kurukshetra"),
                new DivisionData(14, "Mahendragarh"),
                new DivisionData(15, "Nuh"),
                new DivisionData(16, "Palwal"),
                new DivisionData(17, "Panchkula"),
                new DivisionData(18, "Panipat"),
                new DivisionData(19, "Rewari"),
                new DivisionData(20, "Rohtak"),
                new DivisionData(21, "Sirsa"),
                new DivisionData(22, "Sonipat")

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
            case "Ambala": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(1, "Ambala "),
                        new DistrictData(2, "Ambala Cantonment"),
                        new DistrictData(3, "Shahzadpur"),
                        new DistrictData(4, "Naraingarh"),
                        new DistrictData(5, "Kurali"),
                        new DistrictData(6, "Harbon"),
                        new DistrictData(7, "Barara"),
                        new DistrictData(8, "Saha"),
                        new DistrictData(9, "Badhauli "),
                        new DistrictData(10, "Mullana"),
                        new DistrictData(11, "Kardhan"),
                        new DistrictData(12, "Sountli "),
                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }
            case "Bhiwani": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(13, "Bhiwani"),

                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }
            case "Charkhi Dadri": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(14, "Gudana"),
                        new DistrictData(15, "Hindol"),
                        new DistrictData(16, "sanwar"),
                        new DistrictData(17, "Rambass"),
                        new DistrictData(18, "Ranila"),

                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }
            case "Faridabad": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(19, "Faridabad"),

                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }
            case "Fatehabad": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(20, "Fatehabad"),
                        new DistrictData(21, "Ratia"),
                        new DistrictData(22, "Tohana"),
                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }
            case "Gurugram": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(23, "kasan"),
                        new DistrictData(24, "Naurangpur"),
                        new DistrictData(25, "Hasanpur"),
                        new DistrictData(26, "Behrampur"),
                        new DistrictData(27, "Kanahi"),
                        new DistrictData(28, "Dhankot"),
                        new DistrictData(29, "Palam vihar"),
                        new DistrictData(30, "Ghata"),
                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }
            case "Hisar": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(31, "Hisar II."),
                        new DistrictData(32, "Narnaund"),
                        new DistrictData(33, "Uklana"),
                        new DistrictData(34, "Hansi I"),
                        new DistrictData(35, "Hansi II"),
                        new DistrictData(36, "Barwala"),
                        new DistrictData(37, "Agroha"),
                        new DistrictData(38, "Adampur"),
                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }
            case "Yamunanagar": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(39, "Yamunanagar"),
                        new DistrictData(40, "Jagadhri"),
                        new DistrictData(41, "Chhachhrauli"),
                        new DistrictData(42, "Sadhaura"),
                        new DistrictData(43, "Adi Badri"),
                        new DistrictData(44, "Bilaspur"),
                        new DistrictData(45, "Radaur"),
                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }
            case "Jhajjar": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(46, "Bahadurgarh"),
                        new DistrictData(47, "Beri"),
                        new DistrictData(48, "Jhajjar"),
                        new DistrictData(49, "Matanhail"),
                        new DistrictData(50, "Salhawas"),

                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }


            case "Jind": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(51, "Jind "),
                        new DistrictData(52, "Safidon "),
                        new DistrictData(53, "Julana "),
                        new DistrictData(54, "Pilukhera "),
                        new DistrictData(55, "Alewa"),
                        new DistrictData(56, "Uchana"),
                        new DistrictData(57, "Narwana "),
                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }
            case "Kaithal": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(58, "Guhla  "),
                        new DistrictData(59, "Kalayat  "),
                        new DistrictData(60, "Kaithal "),
                        new DistrictData(61, "Pundri "),
                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }

            case "Karnal": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(62, "karnal  "),

                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }

            case "Kurukshetra": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(63, "Thanesar   "),
                        new DistrictData(64, "Ladwa    "),
                        new DistrictData(65, "Shahabad    "),
                        new DistrictData(66, "Pehowa    "),


                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }
            case "Mahendragarh": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(67, "Paiga   "),
                        new DistrictData(68, "Sainipura    "),
                        new DistrictData(69, "Bas mohalla    "),

                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }

            case "Nuh": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(70, "Palri   "),
                        new DistrictData(71, "Nalhar    "),
                        new DistrictData(72, "Jakohpur    "),

                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }
            case "Palwal": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(73, "Jaindipura  "),

                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }
            case "Panchkula": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(74, "Panchkula  "),
                        new DistrictData(75, "Kalka "),
                        new DistrictData(76, "Raipur Rani  "),
                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }

            case "Panipat": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(77, "Panipat  "),

                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }

            case "Rewari": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(78, "Rewari  "),

                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }

            case "Rohtak": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(79, "Rohtak  "),
                        new DistrictData(80, "kheri sadh  "),


                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }

            case "Sirsa": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(81, "Dabwali  "),
                        new DistrictData(82, "Sirsa  "),
                        new DistrictData(83, "Rania  "),
                        new DistrictData(84, "Ellenabad  "),
                        new DistrictData(85, "Kalanwali  "),
                });
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerArrayAdapter);

                break;
            }

            case "Sonipat": {
                ArrayAdapter<DistrictData> spinnerArrayAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, new DistrictData[]{
                        new DistrictData(0, "Select..."),
                        new DistrictData(86, "Bahalgrah  "),
                        new DistrictData(87, "Sonipat  "),

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
