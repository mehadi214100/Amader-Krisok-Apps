package com.example.amaderkrisok;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;

public class ExpertHelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_help); // এই লেআউট ফাইল থাকবে

        // অ্যাকশন বার-এ টাইটেল ও ব্যাক বাটন দেখানো
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("বিশেষজ্ঞ সহায়তা");
        }
    }

    // অ্যাকশন বার থেকে ব্যাক বাটনে ক্লিক করলে Activity বন্ধ হবে
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
