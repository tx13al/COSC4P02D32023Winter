package com.example.museumapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Login extends AppCompatActivity implements View.OnClickListener {
    Button buttonLogin;
    ImageView loginCancel;
    EditText loginUser, loginPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginUser = (EditText) findViewById(R.id.loginUser);
        loginPass = (EditText) findViewById(R.id.loginPass);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        loginCancel = (ImageView) findViewById(R.id.loginCancel);

        buttonLogin.setOnClickListener(this);
        loginCancel.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.buttonLogin:
                // TODO Login Check
                finish();
                break;
            case R.id.loginCancel:
                finish();
                break;
            default:
                break;
        }
    }
}