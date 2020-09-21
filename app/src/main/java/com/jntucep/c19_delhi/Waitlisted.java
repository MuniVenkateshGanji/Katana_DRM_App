package com.jntucep.c19_delhi;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.annotations.NonNull;

public class Waitlisted extends AppCompatActivity {

    DatabaseReference databaseEvents;
    FirebaseAuth mAuth;

    int i;

    Button button_a,button_w;
    ListView listViewUnApprovedEvents;

    public static HashMap<EventOneSchema , String> eventMap;
    public static HashMap<String, String> tempMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waitlisted);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("UnApproved Events");
        int nightModeFlags =
                toolbar.getContext().getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                toolbar.setBackgroundColor(getResources().getColor(R.color.black));
                break;

            case Configuration.UI_MODE_NIGHT_NO:
                toolbar.setBackgroundColor(getResources().getColor(R.color.white));
                break;

        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Button button_a = (Button) findViewById(R.id.button_approved);
        final Button button_w = (Button) findViewById(R.id.button_waitlisted);

        databaseEvents = FirebaseDatabase.getInstance().getReference().child("Events");
        mAuth = FirebaseAuth.getInstance();

        listViewUnApprovedEvents = (ListView) findViewById(R.id.listView_UnApproved);
        eventMap = new HashMap<>();
        tempMap = new HashMap<>();


        button_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Waitlisted.this,Approved.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                eventMap.clear();
                tempMap.clear();

                for( DataSnapshot hostsnapshot: dataSnapshot.getChildren() )
                {

                    for( DataSnapshot eventsnapshot: hostsnapshot.getChildren())
                    {

                        for( DataSnapshot childUn : eventsnapshot.child("unApprovedId").getChildren() )
                        {
                            if(childUn.getValue().toString().equals(mAuth.getUid()))
                            {
                                EventOneSchema eventOneSchema = eventsnapshot.getValue(EventOneSchema.class);
                                tempMap.put(eventsnapshot.getKey(), hostsnapshot.getKey());
                                eventMap.put( eventOneSchema, eventsnapshot.getKey() );
                            }
                        }

                    }

                }

                List<EventOneSchema> eventList = new ArrayList(eventMap.keySet());

                EventList3 adapter = new EventList3(Waitlisted.this , eventList);
                listViewUnApprovedEvents.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}
