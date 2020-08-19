package com.jntucep.c19_delhi;

import android.os.Bundle;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class profileSupport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_support);

        ArrayList<String> text=new ArrayList<>();
        ArrayList<Integer> image=new ArrayList<>();


        text.add("Feedback");
        image.add(R.drawable.feedback);


        text.add("Report");
        image.add(R.drawable.report);



        LinearLayoutManager manager=new LinearLayoutManager(profileSupport.this, LinearLayoutManager.HORIZONTAL,false);
        RecyclerView view=findViewById(R.id.accountlistview1);
        view.setLayoutManager(manager);

        Profilesupportadapter adapater=new Profilesupportadapter(text,image, profileSupport.this);
        view.setAdapter(adapater);



    }
}
