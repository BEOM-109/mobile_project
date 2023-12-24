package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.final_project.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding homeBindinging;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBindinging = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(homeBindinging.getRoot());
    }
}