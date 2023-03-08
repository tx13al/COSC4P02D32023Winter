package com.example.museumapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FloorTwo extends AppCompatActivity implements View.OnClickListener {
    Button floorOneButton, loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor_two);

        floorOneButton = (Button) findViewById(R.id.floorOneButton);
        loginButton = (Button) findViewById(R.id.loginButton);

        floorOneButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.floorOneButton:
                startActivity(new Intent(this, FloorOne.class));
                finish();
                break;
            case R.id.loginButton:
                startActivity(new Intent(this, Login.class));
                break;
        }
    }

    public void floorChange(View view) {
        startActivity(new Intent(this, FloorOne.class));
    }
}