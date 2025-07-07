package com.example.amaderkrisok;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MarketPriceActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private LinearLayout priceListContainer;
    private MaterialCardView highestPriceCard, lowestPriceCard;
    private TextView dateTextView, lastUpdatedTextView;
    private View loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_price);

        // Initialize Firebase
        db = FirebaseFirestore.getInstance();

        // Initialize views
        priceListContainer = findViewById(R.id.priceListContainer);
        highestPriceCard = findViewById(R.id.highestPriceCard);
        lowestPriceCard = findViewById(R.id.lowestPriceCard);
        dateTextView = findViewById(R.id.dateTextView);
        lastUpdatedTextView = findViewById(R.id.lastUpdatedTextView);
        loadingIndicator = findViewById(R.id.loadingIndicator);

        // Set current date
        String currentDate = new SimpleDateFormat("dd MMMM yyyy", new Locale("bn", "BD")).format(new Date());
        dateTextView.setText("ঢাকা, " + currentDate);

        // Set last updated time
        String lastUpdated = new SimpleDateFormat("hh:mm a", new Locale("bn", "BD")).format(new Date());
        lastUpdatedTextView.setText("সর্বশেষ আপডেট: আজ " + lastUpdated);

        // Setup ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("বাজার দর");
        }

        // Fetch data
        fetchMarketPrices();
    }

    private void fetchMarketPrices() {
        loadingIndicator.setVisibility(View.VISIBLE);
        priceListContainer.removeAllViews();

        db.collection("marketPrices")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    loadingIndicator.setVisibility(View.GONE);

                    if (task.isSuccessful()) {
                        List<MarketPrice> priceList = new ArrayList<>();

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            MarketPrice price = document.toObject(MarketPrice.class);
                            priceList.add(price);
                        }

                        if (!priceList.isEmpty()) {
                            // Sort by price (convert to numbers for proper sorting)
                            Collections.sort(priceList, (p1, p2) -> {
                                try {
                                    double price1 = Double.parseDouble(p1.getPrice().replaceAll("[^\\d.]", ""));
                                    double price2 = Double.parseDouble(p2.getPrice().replaceAll("[^\\d.]", ""));
                                    return Double.compare(price2, price1); // Descending order
                                } catch (NumberFormatException e) {
                                    return 0;
                                }
                            });

                            // Set highest and lowest prices
                            setPriceCards(priceList.get(0), priceList.get(priceList.size() - 1));

                            // Display all prices
                            displayAllPrices(priceList);
                        }
                    }
                });
    }

    private void setPriceCards(MarketPrice highest, MarketPrice lowest) {
        // Highest price card
        ((TextView) highestPriceCard.findViewById(R.id.highestPriceProduct)).setText(highest.getProductName());
        ((TextView) highestPriceCard.findViewById(R.id.highestPriceValue)).setText(highest.getPrice());

        // Lowest price card
        ((TextView) lowestPriceCard.findViewById(R.id.lowestPriceProduct)).setText(lowest.getProductName());
        ((TextView) lowestPriceCard.findViewById(R.id.lowestPriceValue)).setText(lowest.getPrice());
    }

    private void displayAllPrices(List<MarketPrice> priceList) {
        // Sort alphabetically for display
        Collections.sort(priceList, (p1, p2) -> p1.getProductName().compareTo(p2.getProductName()));

        for (MarketPrice price : priceList) {
            View itemView = getLayoutInflater().inflate(R.layout.item_market_price, priceListContainer, false);

            TextView productName = itemView.findViewById(R.id.productName);
            TextView priceValue = itemView.findViewById(R.id.priceValue);
            TextView priceChange = itemView.findViewById(R.id.priceChange);

            productName.setText(price.getProductName());
            priceValue.setText(price.getPrice());

            // Set change text and color
            if (price.getChange().contains("↑")) {
                priceChange.setTextColor(ContextCompat.getColor(this, R.color.green));
            } else if (price.getChange().contains("↓")) {
                priceChange.setTextColor(ContextCompat.getColor(this, R.color.red));
            } else {
                priceChange.setTextColor(ContextCompat.getColor(this, R.color.gray));
            }
            priceChange.setText(price.getChange());

            priceListContainer.addView(itemView);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}