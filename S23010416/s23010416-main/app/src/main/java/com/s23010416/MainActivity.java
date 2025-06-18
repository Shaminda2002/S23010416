package com.s23010416;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText userNameEditText, passwordEditText;
    private Button loginButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameEditText = findViewById(R.id.userNameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.btn1);

        dbHelper = new DatabaseHelper(this);

        loginButton.setOnClickListener(v -> {
            String username = userNameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            boolean inserted = dbHelper.insertUser(username, password);

            if (inserted) {
                Toast.makeText(MainActivity.this, "Data saved successfully", Toast.LENGTH_SHORT).show();

                // Navigate to MapActivity
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);

            } else {
                Toast.makeText(MainActivity.this, "Error saving data", Toast.LENGTH_SHORT).show();
            }
        });

    }
}