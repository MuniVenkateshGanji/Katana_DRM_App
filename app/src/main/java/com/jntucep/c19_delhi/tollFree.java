package com.jntucep.c19_delhi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class tollFree extends AppCompatActivity {

    ImageView img1,img2,img3,img4,img5;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tollfree);

        img1 = findViewById(R.id.i1);
        img2 = findViewById(R.id.i2);
        img3 = findViewById(R.id.i3);
        img4 = findViewById(R.id.i4);
        img5 = findViewById(R.id.i5);


        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);

                intent.setData(Uri.parse("tel: 011-22307145"));
                if(ActivityCompat.checkSelfPermission(tollFree.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(tollFree.this,"Please grant the permission",Toast.LENGTH_SHORT).show();
                    requestPermissions();
                }
                else {
                    startActivity(intent);
                }
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);

                intent.setData(Uri.parse("tel: +91-11-23978046"));
                if(ActivityCompat.checkSelfPermission(tollFree.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(tollFree.this,"Please grant the permission",Toast.LENGTH_SHORT).show();
                    requestPermissions();
                }
                else {
                    startActivity(intent);
                }
            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);

                intent.setData(Uri.parse("tel: 101"));
                if(ActivityCompat.checkSelfPermission(tollFree.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(tollFree.this,"Please grant the permission",Toast.LENGTH_SHORT).show();
                    requestPermissions();
                }
                else {
                    startActivity(intent);
                }
            }
        });

        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);

                intent.setData(Uri.parse("tel: 108"));
                if(ActivityCompat.checkSelfPermission(tollFree.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(tollFree.this,"Please grant the permission",Toast.LENGTH_SHORT).show();
                    requestPermissions();
                }
                else {
                    startActivity(intent);
                }
            }
        });
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);

                intent.setData(Uri.parse("tel: 100"));
                if(ActivityCompat.checkSelfPermission(tollFree.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(tollFree.this,"Please grant the permission",Toast.LENGTH_SHORT).show();
                    requestPermissions();
                }
                else {
                    startActivity(intent);
                }
            }
        });
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},1);

    }



}

