package com.jntucep.c19_delhi;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

public class survey extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        btn = findViewById(R.id.button_submit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Spinner spinner_question_2 = (Spinner) findViewById(R.id.spinner2);
            Spinner spinner_question_3 = (Spinner) findViewById(R.id.spinner3);
            Spinner spinner_question_4 = (Spinner) findViewById(R.id.spinner4);
            Spinner spinner_question_5 = (Spinner) findViewById(R.id.spinner5);
            Spinner spinner_question_6 = (Spinner) findViewById(R.id.spinner6);
            Spinner spinner_question_7 = (Spinner) findViewById(R.id.spinner7);
            Spinner spinner_question_8 = (Spinner) findViewById(R.id.spinner8);
            Spinner spinner_question_9 = (Spinner) findViewById(R.id.spinner9);

            String spinner_choice_2 = spinner_question_2.getSelectedItem().toString();
            String spinner_choice_3 = spinner_question_3.getSelectedItem().toString();
            String spinner_choice_4 = spinner_question_4.getSelectedItem().toString();
            String spinner_choice_5 = spinner_question_5.getSelectedItem().toString();
            String spinner_choice_6 = spinner_question_6.getSelectedItem().toString();
            String spinner_choice_7 = spinner_question_7.getSelectedItem().toString();
            String spinner_choice_8 = spinner_question_8.getSelectedItem().toString();
            String spinner_choice_9 = spinner_question_9.getSelectedItem().toString();

            float avg_rating = 0;
            String s[] = {spinner_choice_2, spinner_choice_3, spinner_choice_4,
                    spinner_choice_5, spinner_choice_6, spinner_choice_7, spinner_choice_8,
                    spinner_choice_9};
                for(int i = 0; i < 8; i++)
                {
                    if(s[i].equals("heavy")){avg_rating += 5;}
                    else if(s[i].equals("mild")){avg_rating += 1;}
                    else if(s[i].equals("Neutral")){avg_rating += 0;}
                }
                String avg_rate = Float.toString(avg_rating/9);
                Log.d("--------------->>", avg_rate);


                if(avg_rating>15){
                    Toast.makeText(survey.this," your health:"+avg_rate+"%",Toast.LENGTH_SHORT).show();
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.DAY_OF_WEEK,1);
                    calendar.set(Calendar.HOUR_OF_DAY,10);
                    Intent intent  = new Intent(getApplicationContext(),Notification_reciver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                    Toast.makeText(survey.this,"you are in surveillance! our health:"+avg_rate+"%",Toast.LENGTH_SHORT).show();
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
                    Intent rem = new Intent(survey.this,MainPage.class);
                    startActivity(rem);
                }
               else {
                    Toast.makeText(survey.this,"you are safe! welcome !",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(survey.this, topbar.class);
                    startActivity(intent);
                }

            }
        });



    }
}