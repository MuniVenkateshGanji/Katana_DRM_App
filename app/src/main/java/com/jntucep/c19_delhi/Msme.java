package com.jntucep.c19_delhi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

public class Msme extends AppCompatActivity {
    Button link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.msme);
        link = findViewById(R.id.link);
        link.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
