package com.example.smartcitytraveller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    EditText email,password;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email=findViewById(R.id.editTextTextEmailAddress);
        password=findViewById(R.id.editTextTextPassword);
        fAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar);
    }
    public void openAbout(View v)
    {
        Intent aboutIntent=new Intent(this, AboutActivity.class);
        startActivity(aboutIntent);
    }
    public void openSignUp(View v)
    {
        Intent signIntent=new Intent(this, SignUpActivity.class);
        startActivity(signIntent);
    }
    public void openScheduleCreation(View v)
    {
        String eml=email.getText().toString().trim();
        String pwd=password.getText().toString().trim();
        if(TextUtils.isEmpty(eml))
        {
            email.setError("Email is required!!");
            return;
        }
        if(TextUtils.isEmpty(pwd))
        {
            password.setError("Password is required!!");
            return;
        }
        if(pwd.length()<6)
        {
            password.setError("Password should be greater or equal to 6 characters!!");
        }
        progressBar.setVisibility(View.VISIBLE);
        fAuth.signInWithEmailAndPassword(eml,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(MainActivity.this, "Logged In Successfully!!!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),ScheduleCreationActivity.class));
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Error Occurred!! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                //    progressBar.setVisibility(View.GONE);
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}