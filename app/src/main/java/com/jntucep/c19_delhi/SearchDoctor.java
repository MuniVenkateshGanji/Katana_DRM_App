package com.jntucep.c19_delhi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.jntucep.c19_delhi.spinnerData.DistrictData;
import com.jntucep.c19_delhi.spinnerData.DivisionData;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class SearchDoctor extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // uiControls
    Button btnSearch;

    private ArrayList<String> specialist = new ArrayList<>();

    private DivisionData divisionData;
    private DistrictData districtData;
    private Spinner spinnerDivision, spinnerDistrict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_doctor);

        spinnerDivision = (Spinner) findViewById(R.id.spinnerDivision);
        spinnerDistrict = (Spinner) findViewById(R.id.spinnerDistrict);
        btnSearch = (Button) findViewById(R.id.btnSearch);


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
                new DivisionData(13, "Vizianagaram")


        });

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDivision.setAdapter(spinnerArrayAdapter);

        spinnerDivision.setOnItemSelectedListener(this);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                        Intent i = new Intent(getApplicationContext(), ResultActivity.class);
                        i.putExtra("division_id", String.valueOf(divisionData.id));
                        i.putExtra("district_id", String.valueOf(districtData.id));
                        i.putExtra("specialists", specialists);
                        startActivity(i);

                        // Clear all values from array list
                        specialist.clear();

                    } else
                        Toast.makeText(SearchDoctor.this, "Nothing is selected", Toast.LENGTH_SHORT).show();

                } else
                    Toast.makeText(SearchDoctor.this, "Select any division and district", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /*
    Spinner Listener
    */
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
