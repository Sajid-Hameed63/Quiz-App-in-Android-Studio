package com.example.quizapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.quizapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginIntro extends AppCompatActivity {
    Button getStarted;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_intro);
        mAuth = FirebaseAuth.getInstance();

        // Check whether user is logged in or not!
        if(mAuth.getCurrentUser()!=null){
            Toast.makeText(this, "You are already logged in!", Toast.LENGTH_LONG).show();
            Intent intent=new Intent(LoginIntro.this,MainActivity.class);
            startActivity(intent);
            finish(); // to finish the activity
        }
        getStarted = findViewById(R.id.btnGetStarted);
        getStarted.setOnClickListener(v -> {
            Intent intent=new Intent(LoginIntro.this, LoginActivity.class);
            startActivity(intent);
            finish(); // to finish the activity
        });
    }
}