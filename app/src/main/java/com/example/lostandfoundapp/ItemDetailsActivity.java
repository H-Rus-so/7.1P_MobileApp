package com.example.lostandfoundapp;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ItemDetailsActivity extends AppCompatActivity {
    private TextView tvItemTitle;
    private TextView tvItemTimestamp;
    private TextView tvItemLocation;
    private MaterialButton btnRemoveItem;

    private DBHelper db;      // This will help me interact with the database
    private int advertId;     // This is the ID of the advert passed from the list screen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        // Step 1: Find all the views by ID
        tvItemTitle     = findViewById(R.id.tvItemTitle);
        tvItemTimestamp = findViewById(R.id.tvItemTimestamp);
        tvItemLocation  = findViewById(R.id.tvItemLocation);
        btnRemoveItem   = findViewById(R.id.btnRemoveItem);

        // Step 2: Set up the database helper so I can load or delete adverts
        db = new DBHelper(this);

        // Step 3: Get the advert ID that was passed from the list screen
        advertId = getIntent().getIntExtra("advert_id", -1);
        if (advertId != -1) {
            loadAdvert(advertId); // Load that advert if we got a valid ID
        }

        // Step 4: If the user taps the "Remove" button, delete the advert and go back
        btnRemoveItem.setOnClickListener(v -> {
            db.deleteAdvert(advertId);
            Toast.makeText(this, "Advert removed", Toast.LENGTH_SHORT).show();
            finish(); // Close this screen and return to the list
        });
    }

    /**
     * This method loads a single advert by ID and updates the UI:
     * - Shows the title (e.g. "Lost: Wallet")
     * - Tries to convert the date into something like "2 days ago"
     * - Adds "At" before the location
     */
    private void loadAdvert(int id) {
        Advert adv = db.getAdvertById(id);
        if (adv == null) return; // Just return if the advert wasn’t found

        // Show title like "Found: Keys" or "Lost: Phone"
        tvItemTitle.setText(adv.getType() + ": " + adv.getName());

        // Try to convert the stored date into something like "2 days ago"
        String rawDate = adv.getDate();
        try {
            // Parse the date using the format we saved it in
            SimpleDateFormat parser = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date parsed = parser.parse(rawDate);
            if (parsed != null) {
                long then = parsed.getTime();
                long now  = System.currentTimeMillis();

                // This builds a nice readable time difference string
                CharSequence relative = DateUtils.getRelativeTimeSpanString(
                        then,
                        now,
                        DateUtils.DAY_IN_MILLIS,
                        DateUtils.FORMAT_SHOW_DATE
                );
                tvItemTimestamp.setText(relative); // e.g. "3 days ago"
            } else {
                // Just show the raw date if parsing failed
                tvItemTimestamp.setText(rawDate);
            }
        } catch (Exception e) {
            // If anything goes wrong, just show the raw date string
            tvItemTimestamp.setText(rawDate);
        }

        // Show the location with "At" before it
        tvItemLocation.setText("At " + adv.getLocation());
    }
}
