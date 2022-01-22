package com.example.smartcitytraveller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    TextView textView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        textView3=findViewById(R.id.textView3);
        textView3.setText("This application helps you to plan a schedule for yourself if You are visiting a place which is new to you and you don't know anything about the place.");
    }
}