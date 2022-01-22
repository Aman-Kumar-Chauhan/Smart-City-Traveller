package com.example.smartcitytraveller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ScheduleCreationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_creation);
    }
    public void openMaps(View v)
    {
        EditText start=findViewById(R.id.start_time);
        EditText end=findViewById(R.id.end_time);
        String s="";
        String e="";
        s=start.getText().toString();
        e=end.getText().toString();
        if(!s.isEmpty() && !e.isEmpty()) {
            Intent mapIntent = new Intent(this, MapsActivity.class);
            mapIntent.putExtra("start_time",s);
            mapIntent.putExtra("end_time",e);
            startActivity(mapIntent);
        }
        else {
            Toast.makeText(this, "Please enter start and end time!!!", Toast.LENGTH_SHORT).show();
        }
    }
}