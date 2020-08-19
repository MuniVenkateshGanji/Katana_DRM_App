package com.jntucep.c19_delhi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

public class HostAnEvent extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference databaseEvents;



    EditText EventName,EventDesc,EventPay;
    TextView EventDate;
    Date eventdate;
    private Button nextbutton = null;
    private Button GoToCalender=null;
    private Button cancelhost=null;
    int EventTimeHours,EventTimeMinutes;
    EventOneSchema eventOneSchema,formax;
    String type;
    long maxid = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostanevent);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Host An Event");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent incoming = getIntent();
        final String date = incoming.getStringExtra("date");
        nextbutton = (Button) findViewById(R.id.NextStep);
        EventName=(EditText)findViewById(R.id.HostAnEventName);
        EventDate=(TextView) findViewById(R.id.HostAnEventDate);
        EventDesc=(EditText)findViewById(R.id.HostAnEventDescription);
        TimePicker HostAnEventTime=(TimePicker)findViewById(R.id.HostAnEventTime) ;
        EventPay=(EditText)findViewById(R.id.HostAnEventMoney);
        GoToCalender=(Button)findViewById(R.id.GoToCalender);
        cancelhost=(Button)findViewById(R.id.CancelHost) ;

        Spinner spinner = findViewById(R.id.types);

        ArrayList<String> typelist = new ArrayList<>();
        typelist.add("Health");
        typelist.add("Essential");
        typelist.add("Shelter");
        typelist.add("Food");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // Notify the selected item text

                if(selectedItemText.compareTo("Health")==0){
                    type="Health";
                }
                if(selectedItemText.compareTo("Essential")==0){
                    type="Essential";
                }
                if(selectedItemText.compareTo("Shelter")==0){
                    type="Shelter";
                }
                if(selectedItemText.compareTo("Food")==0){
                    type="Food";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, typelist);

         //Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         //attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        EventDate.setText(date);
        HostAnEventTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Toast.makeText(getApplicationContext(), hourOfDay + "  " + minute, Toast.LENGTH_SHORT).show();
                EventTimeHours=hourOfDay;
                EventTimeMinutes=minute;

            }
        });
        mAuth=FirebaseAuth.getInstance();

        GoToCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentj = new Intent(HostAnEvent.this,CalenderActivity.class);
                startActivity(intentj);
            }
        });
        cancelhost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HostAnEvent.this, MainActivityvolunteer.class);
                startActivity(intent);
            }
        });
        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (date == null) {
                    EventDate.requestFocus();
                    EventDate.setError("");
                    Toast.makeText(getApplicationContext(),"Select Event Date!",Toast.LENGTH_LONG).show();;
                } else {
                    final String eventdateS = EventDate.getText().toString().trim();
                    final String eventname = EventName.getText().toString().trim();
                    final String eventdesc = EventDesc.getText().toString().trim();
                    final String eventpay = EventPay.getText().toString().trim();
                    final String eventlocation = "wenkey";
                    Date presdate = Calendar.getInstance().getTime();
                    if (eventdateS != null) {
                        try {
                            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                            eventdate = df.parse(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    if (eventdateS != null) {
                        if (presdate.after(eventdate)) {
                            EventDate.requestFocus();
                            EventDate.setError("EVENT DATE CANNOT BE A PAST DATE!");
                        }
                    }
                    if (eventname.length() == 0) {
                        EventName.requestFocus();
                        EventName.setError("NAME CANNOT BE EMPTY!");
                    } else if (eventdateS.length() == 0) {
                        EventDate.requestFocus();
                        EventDate.setError("EVENT DATE CANNOT BE EMPTY");
                    }
                     else if (!eventname.matches("[a-zA-z ]*+")) {
                        EventName.requestFocus();
                        EventName.setError("ONLY ALPHABETICAL CHARACTERS ALLOWED!");
                    } else if (date.length() == 0) {
                        EventDate.requestFocus();
                        EventDate.setError("SELECT A DATE!");
                    } else if (eventpay.length() == 0) {
                        EventPay.requestFocus();
                        EventPay.setError("PAYMENT CANNOT BE EMPTY!");

                    } else if (eventdesc.length() == 0) {
                        EventDesc.requestFocus();
                        EventDesc.setError("DESCRIPTION CANNOT BE EMPTY!");

                    } else {
                        Toast.makeText(getApplicationContext(), "Event information saved!", Toast.LENGTH_LONG).show();

                        final EventOneSchema eventOneSchema = new EventOneSchema(eventname, date, eventlocation, eventdesc, Integer.parseInt(eventpay),type);
                        eventOneSchema.closeEntries = false;
                        eventOneSchema.avgRating=(float)0.0;


                        databaseEvents = FirebaseDatabase.getInstance().getReference().child("Events/" + mAuth.getUid());
                        String eventId = databaseEvents.push().getKey();

                        databaseEvents.child(eventId).setValue(eventOneSchema);
                        databaseEvents.child(eventId).child("avgRating").setValue((float) 0.0);
                        Intent i = new Intent(view.getContext(), MapsActivityvol.class);
                        i.putExtra("EventId", eventId);
                        startActivity(i);
                    }
                }
            }
        });
    }
}