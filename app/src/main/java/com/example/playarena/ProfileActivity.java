package com.example.playarena;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    EditText etProfileEmail, etProfileName, etProfilePhone;
    Button btnUpdateProfile, btnLogout;
    UserDatabase userDb;
    String loggedEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        etProfileEmail = findViewById(R.id.etProfileEmail);
        etProfileName = findViewById(R.id.etProfileName);
        etProfilePhone = findViewById(R.id.etProfilePhone);
        btnUpdateProfile = findViewById(R.id.btnUpdateProfile);
        btnLogout = findViewById(R.id.btnLogout);

        userDb = new UserDatabase(this);

        loggedEmail = getIntent().getStringExtra("email");
        loadUserData();

        btnUpdateProfile.setOnClickListener(v -> updateProfile());
        btnLogout.setOnClickListener(v -> logoutUser());
    }

    private void loadUserData() {
        User user = userDb.getUserByEmail(loggedEmail);

        if (user != null) {
            etProfileEmail.setText(user.getEmail());
            etProfileName.setText(user.getName());
            etProfilePhone.setText(user.getPhone());
        }
    }

    private void updateProfile() {
        String newName = etProfileName.getText().toString().trim();
        String newPhone = etProfilePhone.getText().toString().trim();

        boolean updated = userDb.updateUser(loggedEmail, newName, newPhone);

        if (updated) {
            Toast.makeText(this, "Profile updated!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to update profile!", Toast.LENGTH_SHORT).show();
        }
    }

    private void logoutUser() {
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
        finish();
    }
}
