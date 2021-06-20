package com.authenticateusingspring.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.authenticateusingspring.R;

import javax.annotation.Resource;

public class SuccessfulLogin extends AppCompatActivity {

    TextView tvUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_login);
        String username = getIntent().getStringExtra("username");
        tvUsername = findViewById(R.id.tvUserName);
        tvUsername.setText(getString(R.string.hello) + "  " + username);
    }
}