package com.example.amaderkrisok;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
import java.util.Map;

public class DiseaseActivity extends AppCompatActivity {

    Spinner cropCategorySpinner, cropVarietySpinner;
    LinearLayout diseaseInfoLayout;
    TextView diseaseTitle, symptomsText, causesText, treatmentText, preventionText;

    Map<String, String[]> cropVarietyMap = new HashMap<>();
    Map<String, String[]> diseaseDataMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease);

        // UI Elements
        cropCategorySpinner = findViewById(R.id.cropCategorySpinner);
        cropVarietySpinner = findViewById(R.id.cropVarietySpinner);
        diseaseInfoLayout = findViewById(R.id.diseaseInfoLayout);

        diseaseTitle = findViewById(R.id.diseaseTitle);
        symptomsText = findViewById(R.id.symptomsText);
        causesText = findViewById(R.id.causesText);
        treatmentText = findViewById(R.id.treatmentText);
        preventionText = findViewById(R.id.preventionText);

        setupData();
        setupSpinners();
    }

    private void setupData() {
        // Crop-Varieties
        cropVarietyMap.put("ধান", new String[]{"বিআর১১", "বিআর২২"});
        cropVarietyMap.put("গম", new String[]{"সোনালিকা", "কাঞ্চন"});
        cropVarietyMap.put("ভুট্টা", new String[]{"বারি ভুট্টা-১", "সুপ্তা"});
        cropVarietyMap.put("আলু", new String[]{"ডায়মন্ড", "গ্রানোলা"});
        cropVarietyMap.put("সরিষা", new String[]{"বারি সরিষা-১৪", "তোরি-৭"});

        // Disease data
        diseaseDataMap.put("ধান-বিআর১১", new String[]{
                "পাতা ঝলসানো",
                "• পাতায় বাদামী দাগ\n• পাতা শুকিয়ে যাওয়া",
                "• ছত্রাকের আক্রমণ",
                "• ম্যানকোজেব স্প্রে করুন",
                "• জমি পরিষ্কার রাখুন"
        });

        diseaseDataMap.put("ধান-বিআর২২", new String[]{
                "ব্লাস্ট রোগ",
                "• দাগ দেখা যায়\n• গাছ মরে যায়",
                "• ছত্রাক সংক্রমণ",
                "• ট্রাইসাইক্লাজল ব্যবহার করুন",
                "• পানি নিষ্কাশন ঠিক রাখুন"
        });

        diseaseDataMap.put("গম-সোনালিকা", new String[]{
                "পাতা দগ্ধ রোগ",
                "• হলুদ দাগ\n• গাছ দুর্বল হয়ে পড়ে",
                "• ব্যাকটেরিয়া আক্রমণ",
                "• কপার অক্সিক্লোরাইড ব্যবহার করুন",
                "• ভালো পানি নিষ্কাশন নিশ্চিত করুন"
        });

        diseaseDataMap.put("গম-কাঞ্চন", new String[]{
                "গমের পত্রঝরা",
                "• নিচের পাতা হলুদ হয়",
                "• নাইট্রোজেন ঘাটতি",
                "• ইউরিয়া প্রয়োগ করুন",
                "• নির্ধারিত পরিমাণ সার ব্যবহার করুন"
        });

        diseaseDataMap.put("ভুট্টা-বারি ভুট্টা-১", new String[]{
                "লিফ ব্লাইট",
                "• পাতায় সাদা দাগ পড়ে",
                "• ছত্রাক সংক্রমণ",
                "• কপার ভিত্তিক ছত্রাকনাশক ব্যবহার",
                "• আগাছা নিয়ন্ত্রণ করুন"
        });

        diseaseDataMap.put("ভুট্টা-সুপ্তা", new String[]{
                "স্টেম বোরার",
                "• কান্ড ফুটো হয়",
                "• পোকা আক্রমণ",
                "• কুইনালফস স্প্রে করুন",
                "• নিয়মিত পর্যবেক্ষণ করুন"
        });

        diseaseDataMap.put("আলু-ডায়মন্ড", new String[]{
                "লেট ব্লাইট",
                "• পাতা কালো হয়ে যায়",
                "• ছত্রাক সংক্রমণ",
                "• রিডোমিল স্প্রে করুন",
                "• সঠিকভাবে জমি প্রস্তুত করুন"
        });

        diseaseDataMap.put("আলু-গ্রানোলা", new String[]{
                "আলু পাতার দাগ",
                "• পাতার প্রান্তে দাগ",
                "• ভাইরাস সংক্রমণ",
                "• রোগমুক্ত বীজ ব্যবহার করুন",
                "• জমি পরিষ্কার রাখুন"
        });

        diseaseDataMap.put("সরিষা-বারি সরিষা-১৪", new String[]{
                "পাতা কুঁচকে যাওয়া",
                "• পাতায় দাগ দেখা যায়",
                "• ভাইরাস আক্রমণ",
                "• রোগমুক্ত বীজ ব্যবহার করুন",
                "• পোকা দমন করুন"
        });

        diseaseDataMap.put("সরিষা-তোরি-৭", new String[]{
                "মূল পঁচা রোগ",
                "• গাছ ঢলে পড়ে",
                "• ছত্রাকের আক্রমণ",
                "• ট্রাইকোডার্মা ব্যবহার করুন",
                "• পানি জমতে দিবেন না"
        });
    }

    private void setupSpinners() {
        ArrayAdapter<String> cropAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cropVarietyMap.keySet().toArray(new String[0]));
        cropAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cropCategorySpinner.setAdapter(cropAdapter);

        cropCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCrop = cropCategorySpinner.getSelectedItem().toString();
                String[] varieties = cropVarietyMap.get(selectedCrop);

                if (varieties != null) {
                    cropVarietySpinner.setEnabled(true);
                    ArrayAdapter<String> varietyAdapter = new ArrayAdapter<>(DiseaseActivity.this, android.R.layout.simple_spinner_item, varieties);
                    varietyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cropVarietySpinner.setAdapter(varietyAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        cropVarietySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String crop = cropCategorySpinner.getSelectedItem().toString();
                String variety = cropVarietySpinner.getSelectedItem().toString();
                String key = crop + "-" + variety;

                if (diseaseDataMap.containsKey(key)) {
                    String[] diseaseInfo = diseaseDataMap.get(key);
                    diseaseInfoLayout.setVisibility(View.VISIBLE);

                    diseaseTitle.setText("রোগ: " + diseaseInfo[0]);
                    symptomsText.setText(diseaseInfo[1]);
                    causesText.setText(diseaseInfo[2]);
                    treatmentText.setText(diseaseInfo[3]);
                    preventionText.setText(diseaseInfo[4]);
                } else {
                    diseaseInfoLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
}
