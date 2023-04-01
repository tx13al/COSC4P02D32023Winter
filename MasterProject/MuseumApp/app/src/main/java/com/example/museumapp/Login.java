package com.example.museumapp;

import android.content.Context;
import android.content.Intent;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
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

    public Login(Context context, View dimView) {
        this.context = context;
        this.dimView = dimView;
    }

    @Override
    public void onClick(View view) {
        //add a dimmed view to darken the background while the popup window showing
        dimView.setVisibility(View.VISIBLE);
        //popup window settings
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((MainActivity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        int x = width / 15;
        int y = height / 6;
        PopupWindow popupWindow = new PopupWindow(context);
        View popView = LayoutInflater.from(context).inflate(R.layout.activity_login, null);
        popupWindow.setContentView(popView);
        popupWindow.setWidth(width * 7 / 8);
        popupWindow.setHeight(height * 2 / 3);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_popup));
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, x, y);


        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dimView.setVisibility(View.GONE);
            }//dimView gone when close the popup window
        });
        ImageView popup_back_button = popView.findViewById(R.id.loginCancel);
        popup_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }//click on close button, popup window closes
        });

        //login function after clicking the login button
        // Button on the top right id=loginbutton, button for confirm login id=buttonLogin
        login = popView.findViewById(R.id.buttonLogin);
        username= popView.findViewById(R.id.loginID);
        userpassword=popView.findViewById(R.id.loginPass);
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
                    popupWindow.dismiss();
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