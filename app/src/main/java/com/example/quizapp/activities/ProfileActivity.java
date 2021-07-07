package com.example.quizapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.quizapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    TextView email;
    Button logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        email = findViewById(R.id.txtEmail);
        logOut = findViewById(R.id.btnLogout);
        mAuth = FirebaseAuth.getInstance();
        email.setText(mAuth.getCurrentUser().getEmail());
        logOut.setOnClickListener(v -> {
            mAuth.signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}