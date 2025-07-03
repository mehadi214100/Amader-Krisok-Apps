package com.example.amaderkrisok;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ListenerRegistration tipListener;
    private TextView tvDailyTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize views
        tvDailyTip = findViewById(R.id.tv_daily_tip);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        CardView cropCard = findViewById(R.id.cropCard);
        CardView diseaseCard = findViewById(R.id.diseaseCard);
        CardView expertCard = findViewById(R.id.expertCard);
        CardView marketCard = findViewById(R.id.marketCard);

        // Initialize Firebase services
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Setup action bar
        setupActionBar();

        // Setup bottom navigation
        setupBottomNavigation(bottomNav);

        // Setup card click listeners
        setupCardClickListeners(cropCard, diseaseCard, expertCard, marketCard);

        // Load and listen for daily tips updates
        setupDailyTipsListener();
    }

    private void setupActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("হোম পেজ");
        }
    }

    private void setupBottomNavigation(BottomNavigationView bottomNav) {
        bottomNav.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_logout) {
                logoutUser();
                return true;
            }
            return false;
        });
    }

    private void setupCardClickListeners(CardView cropCard, CardView diseaseCard,
                                         CardView expertCard, CardView marketCard) {
        cropCard.setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, CropInfoActivity.class)));

        diseaseCard.setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, DiseaseActivity.class)));

        expertCard.setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, ExpertHelpActivity.class)));

        marketCard.setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, MarketPriceActivity.class)));
    }

    private void setupDailyTipsListener() {
        DocumentReference docRef = db.collection("tips").document("daily_tips");

        tipListener = docRef.addSnapshotListener((documentSnapshot, e) -> {
            if (e != null) {
                handleFirestoreError(e);
                return;
            }

            if (documentSnapshot != null && documentSnapshot.exists()) {
                updateDailyTipUI(documentSnapshot);
            } else {
                handleMissingDocument();
            }
        });
    }

    private void updateDailyTipUI(@NonNull com.google.firebase.firestore.DocumentSnapshot documentSnapshot) {
        String tipContent = documentSnapshot.getString("content");
        if (tipContent != null && !tipContent.isEmpty()) {
            tvDailyTip.setText(tipContent);
        } else {
            tvDailyTip.setText("আজকের জন্য কোন টিপস নেই");
        }
    }

    private void handleFirestoreError(@NonNull FirebaseFirestoreException e) {
        String errorMessage;

        switch (e.getCode()) {
            case PERMISSION_DENIED:
                errorMessage = "অনুমতি নেই - অ্যাডমিনের সাথে যোগাযোগ করুন";
                break;
            case UNAVAILABLE:
                errorMessage = "ইন্টারনেট সংযোগ নেই";
                break;
            case NOT_FOUND:
                errorMessage = "ডাটা পাওয়া যায়নি";
                break;
            default:
                errorMessage = "ডাটা লোড করতে সমস্যা হয়েছে";
        }

        tvDailyTip.setText(errorMessage);
        Log.e(TAG, "Firestore error: " + e.getMessage());
    }

    private void handleMissingDocument() {
        tvDailyTip.setText("ডকুমেন্ট পাওয়া যায়নি");
        Log.d(TAG, "Daily tips document not found");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_logout) {
            logoutUser();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logoutUser() {
        mAuth.signOut();
        Toast.makeText(this, "সফলভাবে লগআউট করা হয়েছে", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tipListener != null) {
            tipListener.remove();
        }
    }
}