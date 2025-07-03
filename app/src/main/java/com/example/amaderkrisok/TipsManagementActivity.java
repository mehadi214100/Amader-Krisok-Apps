package com.example.amaderkrisok;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class TipsManagementActivity extends AppCompatActivity {

    private EditText etTipsContent;
    private Button btnSaveTips;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_management);

        // Initialize Firebase services
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        etTipsContent = findViewById(R.id.et_tips_content);
        btnSaveTips = findViewById(R.id.btn_save_tips);

        // Check if user is admin
        checkAdminStatus();

        // Load current tips
        loadCurrentTips();

        // Set click listener for save button
        btnSaveTips.setOnClickListener(v -> saveTips());
    }

    private void checkAdminStatus() {
        if (mAuth.getCurrentUser() == null) {
            Toast.makeText(this, "প্রথমে লগইন করুন", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String userId = mAuth.getCurrentUser().getUid();
        db.collection("admins").document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful() || !task.getResult().exists()) {
                        Toast.makeText(this, "শুধুমাত্র অ্যাডমিন এই পেজ ব্যবহার করতে পারে", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    private void loadCurrentTips() {
        db.collection("tips").document("daily_tips")
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String currentTips = documentSnapshot.getString("content");
                        if (currentTips != null) {
                            etTipsContent.setText(currentTips);
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "বর্তমান টিপস লোড করতে সমস্যা হয়েছে: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void saveTips() {
        String newTips = etTipsContent.getText().toString().trim();

        if (newTips.isEmpty()) {
            Toast.makeText(this, "টিপস লিখুন", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> tipsData = new HashMap<>();
        tipsData.put("content", newTips);

        db.collection("tips").document("daily_tips")
                .set(tipsData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "টিপস সফলভাবে সংরক্ষণ করা হয়েছে", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "সংরক্ষণ করতে সমস্যা হয়েছে: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}