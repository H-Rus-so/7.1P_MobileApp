package com.example.lostandfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import android.widget.RadioButton;
import android.widget.Toast;

public class CreateAdvertActivity extends AppCompatActivity {

    // All the UI components I need from the screen
    private RadioGroup rgType;
    private RadioButton rbLost, rbFound;
    private EditText etName, etPhone, etDescription, etDate, etLocation;
    private MaterialButton btnSave;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert);

        // Connect all the views from the layout to the variables here
        rgType        = findViewById(R.id.rgType);
        rbLost        = findViewById(R.id.rbLost);
        rbFound       = findViewById(R.id.rbFound);
        etName        = findViewById(R.id.etName);
        etPhone       = findViewById(R.id.etPhone);
        etDescription = findViewById(R.id.etDescription);
        etDate        = findViewById(R.id.etDate);
        etLocation    = findViewById(R.id.etLocation);
        btnSave       = findViewById(R.id.btnSaveAdvert);

        // Set up the database helper so I can save the advert
        db = new DBHelper(this);

        // When the "Save" button is clicked
        btnSave.setOnClickListener(v -> {
            // Work out if it’s a "Lost" or "Found" advert
            String type        = (rgType.getCheckedRadioButtonId() == R.id.rbLost) ? "Lost" : "Found";
            // Get the text the user entered
            String name        = etName.getText().toString().trim();
            String phone       = etPhone.getText().toString().trim();
            String desc        = etDescription.getText().toString().trim();
            String date        = etDate.getText().toString().trim();
            String location    = etLocation.getText().toString().trim();

            // Make sure nothing is left blank
            if (name.isEmpty() || phone.isEmpty() || desc.isEmpty()
                    || date.isEmpty() || location.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create a new Advert object with the info entered
            Advert ad = new Advert(0, type, name, phone, desc, date, location);

            // Try to save the advert in the database
            long id = db.addAdvert(ad);
            if (id > 0) {
                Toast.makeText(this, "Advert saved", Toast.LENGTH_SHORT).show();
                // If save worked, go to the list of items
                startActivity(new Intent(this, ItemListActivity.class));
                finish(); // Close this screen so user can’t return to it
            } else {
                // If save failed, show an error message
                Toast.makeText(this, "Save failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
