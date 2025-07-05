package com.example.amaderkrisok;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MarketPriceManagement extends AppCompatActivity {

    private EditText etProductName, etProductPrice, etPriceChange;
    private Button btnSave;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_price_management);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize views
        etProductName = findViewById(R.id.etProductName);
        etProductPrice = findViewById(R.id.etProductPrice);
        etPriceChange = findViewById(R.id.etPriceChange);
        btnSave = findViewById(R.id.btnSave);

        // Set click listener
        btnSave.setOnClickListener(v -> saveMarketPrice());
    }

    private void saveMarketPrice() {
        if (mAuth.getCurrentUser() == null) {
            Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show();
            return;
        }

        String productName = etProductName.getText().toString().trim();
        String productPrice = etProductPrice.getText().toString().trim();
        String priceChange = etPriceChange.getText().toString().trim();

        if (productName.isEmpty()) {
            etProductName.setError("Product name required");
            return;
        }

        if (productPrice.isEmpty()) {
            etProductPrice.setError("Price required");
            return;
        }

        if (priceChange.isEmpty()) {
            priceChange = "→ স্থির";
        }

        String currentDate = new SimpleDateFormat("dd MMMM yyyy", new Locale("bn", "BD")).format(new Date());

        Map<String, Object> priceData = new HashMap<>();
        priceData.put("productName", productName);
        priceData.put("price", productPrice);
        priceData.put("change", priceChange);
        priceData.put("date", currentDate);
        priceData.put("timestamp", System.currentTimeMillis());
        priceData.put("userId", mAuth.getCurrentUser().getUid());

        db.collection("marketPrices")
                .add(priceData)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(MarketPriceManagement.this,
                            "বাজার দর সফলভাবে সংরক্ষণ হয়েছে",
                            Toast.LENGTH_SHORT).show();
                    clearFields();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MarketPriceManagement.this,
                            "সংরক্ষণ ব্যর্থ: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                });
    }

    private void clearFields() {
        etProductName.setText("");
        etProductPrice.setText("");
        etPriceChange.setText("");
        etProductName.requestFocus();
    }
}