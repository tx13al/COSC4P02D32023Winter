package com.example.museumapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class Login  implements View.OnClickListener {
    private Context context;
    View dimView;
    EditText  username, userpassword;
    Button login;
    ImageView loginCancel;

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
        dialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_popup));
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
                    intent.setClass(context.getApplicationContext(),Control.class);
                    context.startActivity(intent);
                    Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else if (outcome == 2) {
                    Toast.makeText(context, "Login Failed. Incorrect Password.", Toast.LENGTH_SHORT).show();
                } else if (outcome == 1) {
                    Toast.makeText(context, "Login Failed. User does not exist.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Login Failed. Please enter a username/password.", Toast.LENGTH_SHORT).show();
                }

                //TODO
            }

            public void SearchBar(Context context, String[] arr, AutoCompleteTextView View) {
                //search bar
                String[] stringArray = arr;

                ArrayAdapter<String> completion = new ArrayAdapter<>(
                        context, android.R.layout.simple_dropdown_item_1line, arr
                );

                AutoCompleteTextView actv = View;
                actv.setAdapter(completion);
                actv.setThreshold(1);
                actv.setInputType(InputType.TYPE_CLASS_TEXT);

                actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String selectedItem = (String) adapterView.getItemAtPosition(i);
                        Toast.makeText(context, "Item "+selectedItem+ " found. ", Toast.LENGTH_SHORT).show();
                    }
                });

                actv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                        if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                            String enteredText = textView.getText().toString();
                            int position = ((ArrayAdapter<String>) actv.getAdapter()).getPosition(enteredText);
                            if (position >= 0) {
                                // The entered text is in the adapter
                                Toast.makeText(context, "Item "+enteredText+ " found. ", Toast.LENGTH_SHORT).show();
                            } else {
                                // The entered text is not in the adapter
                                Toast.makeText(context, enteredText+" is NOT found", Toast.LENGTH_SHORT).show();
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }
        });
    }


}