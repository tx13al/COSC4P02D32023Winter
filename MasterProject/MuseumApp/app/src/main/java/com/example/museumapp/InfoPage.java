package com.example.museumapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class InfoPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        setTitle("Visitor Info");
        TextView address_text = findViewById(R.id.location_address);
        address_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri addressUri = Uri.parse("geo:0,0?q=" + Uri.encode("43 Castlereagh St, Niagara-on-the-Lake, ON L0S 1J0"));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, addressUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
        Toolbar toolbar = findViewById(R.id.tool_info);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
