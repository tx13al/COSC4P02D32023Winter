package com.example.museumapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SettingsPage extends AppCompatActivity {

    private float currentFontScale = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        setTitle("");

        Toolbar toolbar = findViewById(R.id.more_tool_bar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Button btnSmallFont = findViewById(R.id.btnSmallFont);
        Button btnMediumFont = findViewById(R.id.btnMediumFont);
        Button btnLargeFont = findViewById(R.id.btnLargeFont);
        //Button btnChangeButton = findViewById(R.id.button_change_theme);

        btnSmallFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adjustFontScale(0.8f);
            }
        });
        btnMediumFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adjustFontScale(1.0f);
            }
        });
        btnLargeFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adjustFontScale(1.2f);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void adjustFontScale(float scale) {
        Configuration configuration = getResources().getConfiguration();
        configuration.fontScale = scale;
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        getBaseContext().getResources().updateConfiguration(configuration, metrics);
        currentFontScale = scale;
        recreate();
    }
}
