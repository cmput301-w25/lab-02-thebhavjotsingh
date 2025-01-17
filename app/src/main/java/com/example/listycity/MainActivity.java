package com.example.listycity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    String selectedCity = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);

        String [] cities = {"Edmonton","Vancouver","Delhi","Amritsar"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter((cityAdapter));

        Button addButton = findViewById(R.id.addButton);
        Button delButton = findViewById(R.id.delButton);

        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedCity = dataList.get(position); // Get the selected city
            Toast.makeText(MainActivity.this, selectedCity + " selected", Toast.LENGTH_SHORT).show();
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddCityDialog();
            }
        });

        delButton.setOnClickListener(v -> {
            if (selectedCity != null) {
                dataList.remove(selectedCity); // Remove the selected city
                cityAdapter.notifyDataSetChanged(); // Notify the adapter of the change
                Toast.makeText(MainActivity.this, selectedCity + " removed!", Toast.LENGTH_SHORT).show();
                selectedCity = null; // Reset the selected city
            } else {
                Toast.makeText(MainActivity.this, "No city selected to delete!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showAddCityDialog() {
        // Create an AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add City");

        // Set up an input field
        final EditText input = new EditText(this);
        input.setHint("Enter city name");
        builder.setView(input);

        // Set up the dialog buttons
        builder.setPositiveButton("Add", (dialog, which) -> {
            String newCity = input.getText().toString().trim();
            if (!newCity.isEmpty()) {
                dataList.add(newCity);
                cityAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, newCity + " added!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "City name cannot be empty!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        // Show the dialog
        builder.show();
    }
}