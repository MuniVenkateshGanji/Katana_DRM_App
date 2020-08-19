package com.jntucep.c19_delhi;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import io.reactivex.annotations.NonNull;

public class MainActivityvolunteer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    String profilename1,email1,phno1;
    private ChildEventListener childEventListener;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    TextView pname,pemail,pphno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainvol);
        mAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@androidx.annotation.NonNull @NonNull DataSnapshot dataSnapshot) {
                profilename1 = Objects.requireNonNull(dataSnapshot.child("name").getValue()).toString();
                pname = findViewById(R.id.profilename2);
                pname.setText(profilename1);

                email1 = Objects.requireNonNull(dataSnapshot.child("mail").getValue()).toString();
                pemail = findViewById(R.id.profileemail2);
                pemail.setText(email1);

                de.hdodenhof.circleimageview.CircleImageView pro;
                pro =(de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.pro1);



            }

            @Override
            public void onCancelled(@androidx.annotation.NonNull @NonNull DatabaseError databaseError) {

            }
        });


        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MessageFragment()).commit();

            navigationView.setCheckedItem(R.id.nav_events);
            NavigationView navigationview = (NavigationView) findViewById(R.id.nav_view);
            View headerview = navigationview.getHeaderView(0);

            ImageView view=(ImageView)headerview.findViewById(R.id.pro1);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(MainActivityvolunteer.this,Profile.class);
                    startActivity(intent1);

                }
            });
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch(menuItem.getItemId()){

            case R.id.nav_events:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Events()).commit();
                Intent intent = new Intent(MainActivityvolunteer.this, MyEvents.class);
                startActivity(intent);

                break;
            case R.id.nav_volunteer:
                // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new VolunteeringFragment()).commit();
                Intent intent1 = new Intent(MainActivityvolunteer.this, VolunteerEvents.class);
                startActivity(intent1);
                break;
            case R.id.nav_wallet:
                // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Wallet()).commit();
                Intent intent3 = new Intent(MainActivityvolunteer.this, upload_labour.class);
                startActivity(intent3);
                break;
            case R.id.nav_host:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Hosting()).commit();
                Intent intent2 = new Intent(MainActivityvolunteer.this, HostAnEvent.class);
                startActivity(intent2);

                break;



            

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent startMain = new Intent(MainActivityvolunteer.this,topbar.class);
            startActivity(startMain);
            finish();
        }
    }
}