package com.example.lostandfoundapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private MaterialButton btnCreateAdvert, btnShowItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link the buttons from the layout to the code
        btnCreateAdvert = findViewById(R.id.btnCreateAdvert);
        btnShowItems    = findViewById(R.id.btnShowItems);

        // If the user taps "Create a New Advert", open the screen to enter advert details
        btnCreateAdvert.setOnClickListener(v -> {
            startActivity(new Intent(this, CreateAdvertActivity.class));
        });

        // If the user taps "Show All Lost & Found Items", go to the list of adverts
        btnShowItems.setOnClickListener(v -> {
            startActivity(new Intent(this, ItemListActivity.class));
        });
    }
}
