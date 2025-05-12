package com.example.lostandfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ItemListActivity extends AppCompatActivity {
    private ListView listView;
    private DBHelper db;
    private List<Advert> adverts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        // Set up the ListView and the database helper
        listView = findViewById(R.id.listViewItems);
        db       = new DBHelper(this);

        // When someone taps on a list item, go to the detail screen for that advert
        listView.setOnItemClickListener((AdapterView<?> parent, android.view.View view, int pos, long id) -> {
            // Get the advert they clicked on
            Advert chosen = adverts.get(pos);

            // Create an Intent to open the details screen, and pass the advert's ID
            Intent i = new Intent(this, ItemDetailsActivity.class);
            i.putExtra("advert_id", chosen.getId());
            startActivity(i);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the list every time this screen is shown
        adverts = db.getAllAdverts(); // Get all saved adverts from the database

        // Create a simple built-in list adapter using the toString() from Advert
        ArrayAdapter<Advert> adapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1,
                        adverts);
        listView.setAdapter(adapter); // Show the adverts in the list view
    }
}
