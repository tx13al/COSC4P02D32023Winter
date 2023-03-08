package com.example.museumapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FloorOne extends AppCompatActivity implements View.OnClickListener {
    Button floorTwoButton, loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor_one);

        floorTwoButton = (Button) findViewById(R.id.floorTwoButton);
        loginButton = (Button) findViewById(R.id.loginButton);

        floorTwoButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.floorTwoButton:
                startActivity(new Intent(this, FloorTwo.class));
                finish();
                break;
            case R.id.loginButton:
                startActivity(new Intent(this, Login.class));
                break;
        }
    }
}
