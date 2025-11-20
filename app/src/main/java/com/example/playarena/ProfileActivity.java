package com.example.playarena;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    EditText etProfileEmail, etProfileName, etProfilePhone;
    Button btnLogout;
    UserDatabase userDb;
    String loggedEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        etProfileEmail = findViewById(R.id.etProfileEmail);
        etProfileName = findViewById(R.id.etProfileName);
        etProfilePhone = findViewById(R.id.etProfilePhone);
        btnLogout = findViewById(R.id.btnLogout);

        userDb = new UserDatabase(this);

        SharedPreferences preferences = getSharedPreferences("user_session", MODE_PRIVATE);
        loggedEmail = preferences.getString("email", null);

        loadUserData();

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

    public void goToMain(View view) {
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(intent);
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
