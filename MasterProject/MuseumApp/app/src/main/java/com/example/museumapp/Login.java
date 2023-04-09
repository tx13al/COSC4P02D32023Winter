package com.example.museumapp;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Login implements View.OnClickListener {
    private Context context;
    public Login(Context context){
        this.context = context;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onClick(View view) {
        //dialog window settings
        Dialog dialog = new Dialog(context);
        View DialogView = LayoutInflater.from(context).inflate(R.layout.activity_login, null);
        dialog.setContentView(DialogView);
        dialog.setTitle("Login");
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button login = DialogView.findViewById(R.id.buttonLogin);
        EditText username = DialogView.findViewById(R.id.loginID);
        EditText userpassword = DialogView.findViewById(R.id.loginPass);
        ImageView loginCancel = DialogView.findViewById(R.id.loginCancel);
        loginCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int outcome = DatabaseHelper.tryLogin
                        (username.getText().toString(), userpassword.getText().toString());
                if(outcome == 3){
                    Intent intent=new Intent();
                    intent.setClass(context.getApplicationContext(), Control.class);
                    context.startActivity(intent);
                    Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    if (context instanceof MainActivity) {
                        ShowCaseSingleton.getInstance().setShowCases(((MainActivity)context).getShowCases());
                    }
                    ((Activity) context).finish();
                } else if (outcome == 2) {
                    Toast.makeText(context, "Login Failed. Incorrect Password.", Toast.LENGTH_SHORT).show();
                } else if (outcome == 1) {
                    Toast.makeText(context, "Login Failed. User does not exist.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Login Failed. Please enter a username/password.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}