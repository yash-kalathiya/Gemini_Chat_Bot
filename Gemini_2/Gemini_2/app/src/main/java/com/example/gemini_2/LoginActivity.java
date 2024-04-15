package com.example.gemini_2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

        EditText etUsername, etPassword;
        Button btnLogin,btnRegister;
        DatabaseHelper databaseHelper;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_login);

                etUsername = findViewById(R.id.etUsername);
                etPassword = findViewById(R.id.etPassword);
                btnLogin = findViewById(R.id.btnLogin);
                btnRegister = findViewById(R.id.btnRegister);
                databaseHelper = new DatabaseHelper(this);

                btnLogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                String username = etUsername.getText().toString().trim();
                                String password = etPassword.getText().toString().trim();

                                // Check if the username and password exist in the database
                                if (databaseHelper.checkUser(username, password)) {
                                        // Set "isLoggedIn" flag to true
                                        SharedPreferences sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putBoolean("isLoggedIn", true);
                                        editor.apply();

                                        // Redirect to MainActivity
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish(); // Close LoginActivity to prevent going back
                                } else {
                                        // Login failed, display error message
                                        Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                                }
                        }
                });
                btnRegister.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                // Get username and password from EditText fields
                                String username = etUsername.getText().toString().trim();
                                String password = etPassword.getText().toString().trim();

                                // Check if username and password are not empty
                                if (!username.isEmpty() && !password.isEmpty()) {
                                        // Check if the user already exists in the database
                                        if (databaseHelper.checkUserExists(username)) {
                                                // User already exists, display error message
                                                Toast.makeText(LoginActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                                        } else {
                                                // Add the new user to the database
                                                long result = databaseHelper.addUser(username, password);
                                                if (result != -1) {
                                                        // Registration successful, display success message
                                                        Toast.makeText(LoginActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                                } else {
                                                        // Registration failed, display error message
                                                        Toast.makeText(LoginActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                                }
                                        }
                                } else {
                                        // Username or password is empty, display error message
                                        Toast.makeText(LoginActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                                }
                        }
                });
        }
}
