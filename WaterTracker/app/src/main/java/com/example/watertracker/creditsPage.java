package com.example.watertracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class creditsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
