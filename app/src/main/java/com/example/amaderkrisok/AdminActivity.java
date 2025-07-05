package com.example.amaderkrisok;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Check if user is admin (add your admin verification logic)
        if (!isAdminUser()) {
            Toast.makeText(this, "অনুমতি নেই", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initialize all management buttons
        View cropManagementBtn = findViewById(R.id.cropManagementBtn);
        View diseaseManagementBtn = findViewById(R.id.diseaseManagementBtn);
        View expertManagementBtn = findViewById(R.id.expertManagementBtn);
        View marketManagementBtn = findViewById(R.id.marketManagementBtn);
        View tipsManagementBtn = findViewById(R.id.tipsManagementBtn);

        // Set click listeners for management buttons
        cropManagementBtn.setOnClickListener(v ->
                startActivity(new Intent(AdminActivity.this, CropManagementActivity.class)));

        diseaseManagementBtn.setOnClickListener(v ->
                startActivity(new Intent(AdminActivity.this, DiseaseManagementActivity.class)));

        marketManagementBtn.setOnClickListener(v ->
                startActivity(new Intent(AdminActivity.this, MarketPriceManagement.class)));

        tipsManagementBtn.setOnClickListener(v ->
                startActivity(new Intent(AdminActivity.this, TipsManagementActivity.class)));

        // Initialize BottomNavigationView
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);

        // Set listener for bottom navigation items
        bottomNav.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.menu_home) {
                // Refresh if needed
                return true;
            } else if (itemId == R.id.menu_profile) {
                startActivity(new Intent(AdminActivity.this, HomeActivity.class));
                return true;
            } else if (itemId == R.id.menu_logout) {
                logoutUser();
                return true;
            }
            return false;
        });

        // Set the home item as selected by default
        bottomNav.setSelectedItemId(R.id.menu_home);
    }

    private boolean isAdminUser() {
        // Implement your admin verification logic here
        // Example: Check Firebase Auth or Firestore for admin status
        return true; // Temporary - replace with actual check
    }

    private void logoutUser() {
        mAuth.signOut();
        Toast.makeText(this, "সফলভাবে লগআউট করা হয়েছে", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}