package com.basicapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Result");

        //intent to get data
        Intent intent = getIntent();
        String name = intent.getStringExtra("Name");
        String email = intent.getStringExtra("Email");
        String phone = intent.getStringExtra("Phone");

        TextView mResultTv = findViewById(R.id.resultTv);

        mResultTv.setText("Name: "+name+"\nEmail: "+email+ "\nPhone:"+phone);
    }
}