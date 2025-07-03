package com.example.amaderkrisok;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.*;

public class CropInfoActivity extends AppCompatActivity {

    private Spinner cropSpinner, varietySpinner;
    private TextView infoTitle, infoDetails, cultivationTips, diseaseInfo, fertilizerInfo;
    private LinearLayout infoLayout;
    private ProgressBar loadingProgress;

    private Map<String, String[]> cropVarieties = new HashMap<>();
    private Map<String, CropInfo> varietyDataMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_info);

        initializeViews();
        setupData();
        setupSpinners();
    }

    private void initializeViews() {
        cropSpinner = findViewById(R.id.cropSpinner);
        varietySpinner = findViewById(R.id.varietySpinner);
        infoTitle = findViewById(R.id.infoTitle);
        infoDetails = findViewById(R.id.infoDetails);
        cultivationTips = findViewById(R.id.cultivationTips);
        diseaseInfo = findViewById(R.id.diseaseInfo);
        fertilizerInfo = findViewById(R.id.fertilizerInfo);
        infoLayout = findViewById(R.id.infoLayout);
        loadingProgress = findViewById(R.id.loadingProgress);
    }

    private void setupData() {
        // ফসল এবং তাদের জাত
        cropVarieties.put("ধান", new String[]{"ব্রি ধান-২৮", "ব্রি ধান-২৯", "ব্রি ধান-৫০"});
        cropVarieties.put("গম", new String[]{"সোনালী", "প্রোটিনা", "বরকত"});
        cropVarieties.put("ভুট্টা", new String[]{"পায়োনিয়ার", "কণিকা", "বারি ভুট্টা-৭"});
        cropVarieties.put("আলু", new String[]{"ডায়ামন্ড", "কার্ডিনাল", "বারি আলু-৭"});

        // ধানের জাতের তথ্য
        varietyDataMap.put("ধান-ব্রি ধান-২৮", new CropInfo(
                "ব্রি ধান-২৮ (উচ্চ ফলনশীল আমন ধান)",
                "উৎপাদন: ৬-৭ টন/হেক্টর\n" +
                        "পরিপক্কতা: ১৪০-১৪৫ দিন\n" +
                        "বপন সময়: জুলাই-আগস্ট\n" +
                        "গাছের উচ্চতা: ১০৫-১১০ সেমি",
                "• জমি ভালভাবে চাষ ও মই দিয়ে প্রস্তুত করুন\n" +
                        "• সারি থেকে সারির দূরত্ব ২০ সেমি রাখুন\n" +
                        "• বীজ হার: ৩০-৩৫ কেজি/হেক্টর",
                "• পাতা পোড়া রোগ: টিল্ট ২৫০ ইসি ব্যবহার করুন\n" +
                        "• বাকানি রোগ: কার্বেন্ডাজিম গ্রুপের ছত্রাকনাশক\n" +
                        "• পামরি পোকা: কার্টাপ হাইড্রোক্লোরাইড",
                "• ইউরিয়া: ২০০-২২০ কেজি/হেক্টর\n" +
                        "• টিএসপি: ১০০-১২০ কেজি\n" +
                        "• এমওপি: ৮০-১০০ কেজি\n" +
                        "• জিপসাম: ৬০-৮০ কেজি"
        ));

        varietyDataMap.put("ধান-ব্রি ধান-২৯", new CropInfo(
                "ব্রি ধান-২৯ (নতুন উচ্চ ফলনশীল জাত)",
                "উৎপাদন: ৭-৮ টন/হেক্টর\n" +
                        "পরিপক্কতা: ১৪৫-১৫০ দিন\n" +
                        "সেচ: ৩-৪ বার প্রয়োজন\n" +
                        "প্রতিরোধ ক্ষমতা: খরা সহনশীল",
                "• আগাছা নিয়ন্ত্রণ অত্যন্ত গুরুত্বপূর্ণ\n" +
                        "• জৈব সার ব্যবহারে ফলন বৃদ্ধি পায়\n" +
                        "• সমন্বিত বালাই ব্যবস্থাপনা প্রয়োগ করুন",
                "• ব্লাস্ট রোগ: ট্রাইসাইক্লাজোল ব্যবহার করুন\n" +
                        "• গলিফচিং: ক্লোরপাইরিফস দিয়ে নিয়ন্ত্রণ\n" +
                        "• পাতা মোড়ানো পোকা: ফিপ্রোনিল স্প্রে করুন",
                "• ইউরিয়া: ২৫০ কেজি (৩ কিস্তিতে)\n" +
                        "• টিএসপি: ১৫০ কেজি\n" +
                        "• জিংক সালফেট: ১০ কেজি\n" +
                        "• বোরন: ৫ কেজি"
        ));

        varietyDataMap.put("ধান-ব্রি ধান-৫০", new CropInfo(
                "ব্রি ধান-৫০ (বোরো ধানের জাত)",
                "উৎপাদন: ৭.৫-৮.৫ টন/হেক্টর\n" +
                        "পরিপক্কতা: ১৫৫-১৬০ দিন\n" +
                        "বপন সময়: নভেম্বর-ডিসেম্বর\n" +
                        "বিশেষ বৈশিষ্ট্য: লবণাক্ততা সহনশীল",
                "• জমিতে পর্যাপ্ত সেচ নিশ্চিত করুন\n" +
                        "• সময়মতো সার প্রয়োগ করুন\n" +
                        "• ফসল সংগ্রহে বিলম্ব করবেন না",
                "• টুংরো রোগ: ইমিডাক্লোপ্রিড দিয়ে নিয়ন্ত্রণ\n" +
                        "• পাতা মোড়ানো পোকা: কার্টাপ ব্যবহার করুন\n" +
                        "• গান্ধী পোকা: ম্যালাথিয়ন স্প্রে করুন",
                "• ইউরিয়া: ২৮০ কেজি (৩ কিস্তিতে)\n" +
                        "• টিএসপি: ১৮০ কেজি\n" +
                        "• এমওপি: ১২০ কেজি\n" +
                        "• জিংক: ১২ কেজি"
        ));

        // গমের জাতের তথ্য
        varietyDataMap.put("গম-সোনালী", new CropInfo(
                "সোনালী (উচ্চ প্রোটিন সমৃদ্ধ)",
                "উৎপাদন: ৫-৫.৫ টন/হেক্টর\n" +
                        "পরিপক্কতা: ১০০-১১০ দিন\n" +
                        "প্রোটিন: ১২-১৩%\n" +
                        "বপন সময়: নভেম্বর-ডিসেম্বর",
                "• জমি ভালভাবে চাষ করে নিন\n" +
                        "• সারি থেকে সারির দূরত্ব ২০ সেমি\n" +
                        "• বীজ হার: ১২০-১৩০ কেজি/হেক্টর",
                "• পাতার দাগ রোগ: টেবুকোনাজোল স্প্রে করুন\n" +
                        "• কান্ড পচা রোগ: কার্বেন্ডাজিম দিয়ে চিকিৎসা\n" +
                        "• মাজরা পোকা: সাইপারমেথ্রিন দমন",
                "• ইউরিয়া: ১৫০ কেজি (২ কিস্তিতে)\n" +
                        "• টিএসপি: ১০০ কেজি\n" +
                        "• এমওপি: ৮০ কেজি\n" +
                        "• গোবর সার: ৫ টন"
        ));

        varietyDataMap.put("গম-প্রোটিনা", new CropInfo(
                "প্রোটিনা (অত্যধিক প্রোটিনযুক্ত)",
                "উৎপাদন: ৪.৫-৫ টন/হেক্টর\n" +
                        "প্রোটিন: ১৪-১৫%\n" +
                        "পরিপক্কতা: ১০৫-১১৫ দিন\n" +
                        "বিশেষতা: রুটি তৈরির জন্য উত্তম",
                "• সময়মতো বপন করুন\n" +
                        "• সেচের সময় নিয়ম মেনে চলুন\n" +
                        "• ফসল সংগ্রহে বিলম্ব করবেন না",
                "• গমের দানা পোকা: ফেনিট্রোথিয়ন স্প্রে\n" +
                        "• জাবপোকা: ইমিডাক্লোপ্রিড দমন\n" +
                        "• পাতার দাগ: ম্যানকোজেব স্প্রে",
                "• ইউরিয়া: ১৬০ কেজি (২ কিস্তিতে)\n" +
                        "• টিএসপি: ১১০ কেজি\n" +
                        "• জিপসাম: ৫০ কেজি\n" +
                        "• বোরাক্স: ৫ কেজি"
        ));

        varietyDataMap.put("গম-বরকত", new CropInfo(
                "বরকত (খরা সহনশীল জাত)",
                "উৎপাদন: ৪-৪.৫ টন/হেক্টর\n" +
                        "পরিপক্কতা: ৯৫-১০৫ দিন\n" +
                        "সেচ: ২-৩ বার\n" +
                        "বিশেষতা: কম পানিতে চাষযোগ্য",
                "• অগভীর চাষ করুন\n" +
                        "• মালচিং ব্যবহার করুন\n" +
                        "• আগাছা নিয়ন্ত্রণ করুন",
                "• কান্ড পোকা: কার্বারিল স্প্রে\n" +
                        "• পাতার দাগ: হেক্সাকোনাজোল\n" +
                        "• গমের মাজরা: ফিপ্রোনিল",
                "• ইউরিয়া: ১৩০ কেজি (২ কিস্তিতে)\n" +
                        "• টিএসপি: ৯০ কেজি\n" +
                        "• জিংক সালফেট: ৮ কেজি\n" +
                        "• গোবর সার: ৪ টন"
        ));

        // ভুট্টার জাতের তথ্য
        varietyDataMap.put("ভুট্টা-পায়োনিয়ার", new CropInfo(
                "পায়োনিয়ার (হাইব্রিড ভুট্টা)",
                "উৎপাদন: ৮-১০ টন/হেক্টর\n" +
                        "পরিপক্কতা: ৯০-১০০ দিন\n" +
                        "শস্যের রঙ: উজ্জ্বল হলুদ\n" +
                        "বপন সময়: অক্টোবর-নভেম্বর",
                "• সারি থেকে সারির দূরত্ব ৭৫ সেমি\n" +
                        "• গাছ থেকে গাছের দূরত্ব ২৫ সেমি\n" +
                        "• বীজ হার: ২০-২৫ কেজি/হেক্টর",
                "• ডাউনি মিলডিউ: মেটালাক্সিল + ম্যানকোজেব\n" +
                        "• স্টেম বোরার: কার্টাপ বা ফিপ্রোনিল\n" +
                        "• পাখি থেকে রক্ষা: নেট ব্যবহার করুন",
                "• ইউরিয়া: ২৫০ কেজি (৩ কিস্তিতে)\n" +
                        "• টিএসপি: ১৫০ কেজি\n" +
                        "• জিপসাম: ১০০ কেজি\n" +
                        "• জিংক: ১০ কেজি"
        ));

        varietyDataMap.put("ভুট্টা-কণিকা", new CropInfo(
                "কণিকা (উচ্চ ফলনশীল)",
                "উৎপাদন: ৭-৮ টন/হেক্টর\n" +
                        "পরিপক্কতা: ৮৫-৯৫ দিন\n" +
                        "বপন সময়: নভেম্বর-ডিসেম্বর\n" +
                        "বিশেষতা: রোগ প্রতিরোধী",
                "• গভীর চাষ করুন\n" +
                        "• সময়মতো সেচ দিন\n" +
                        "• সমন্বিত সার ব্যবস্থাপনা করুন",
                "• পাতার দাগ: কপার অক্সিক্লোরাইড\n" +
                        "• ভুট্টার পোকা: সাইপারমেথ্রিন\n" +
                        "• কান্ড পচা: কার্বেন্ডাজিম",
                "• ইউরিয়া: ২৩০ কেজি (৩ কিস্তিতে)\n" +
                        "• টিএসপি: ১৩০ কেজি\n" +
                        "• জিংক সালফেট: ৮ কেজি\n" +
                        "• বোরাক্স: ৪ কেজি"
        ));

        varietyDataMap.put("ভুট্টা-বারি ভুট্টা-৭", new CropInfo(
                "বারি ভুট্টা-৭ (স্থানীয় উন্নত জাত)",
                "উৎপাদন: ৬-৭ টন/হেক্টর\n" +
                        "পরিপক্কতা: ৯৫-১০৫ দিন\n" +
                        "সেচ: ৩-৪ বার\n" +
                        "বিশেষতা: লবণাক্ততা সহনশীল",
                "• জৈব সার ব্যবহার করুন\n" +
                        "• ফসল পর্যায় অনুসরণ করুন\n" +
                        "• সময়মতো সংগ্রহ করুন",
                "• পাতা পোড়া রোগ: ম্যানকোজেব\n" +
                        "• কান্ড পোকা: কার্টাপ\n" +
                        "• জাবপোকা: ডাইমেথোয়েট",
                "• ইউরিয়া: ২০০ কেজি (৩ কিস্তিতে)\n" +
                        "• টিএসপি: ১২০ কেজি\n" +
                        "• এমওপি: ৭০ কেজি\n" +
                        "• গোবর সার: ৬ টন"
        ));

        // আলুর জাতের তথ্য
        varietyDataMap.put("আলু-ডায়ামন্ড", new CropInfo(
                "ডায়ামন্ড (উচ্চ ফলনশীল)",
                "উৎপাদন: ৩০-৩৫ টন/হেক্টর\n" +
                        "পরিপক্কতা: ৮৫-৯০ দিন\n" +
                        "বীজ হার: ১.৫-২ টন/হেক্টর\n" +
                        "বপন সময়: অক্টোবর-নভেম্বর",
                "• মাটি ঝুরঝুরে করে চাষ করুন\n" +
                        "• সারি থেকে সারির দূরত্ব ৬০ সেমি\n" +
                        "• গাছ থেকে গাছের দূরত্ব ২৫ সেমি",
                "• লেট ব্লাইট: মেটালাক্সিল + ম্যানকোজেব\n" +
                        "• আলুর পোকা: সাইপারমেথ্রিন\n" +
                        "• কান্ড পচা: কপার অক্সিক্লোরাইড",
                "• ইউরিয়া: ২৫০ কেজি (৩ কিস্তিতে)\n" +
                        "• টিএসপি: ২০০ কেজি\n" +
                        "• এমওপি: ১৮০ কেজি\n" +
                        "• গোবর সার: ১০ টন"
        ));

        varietyDataMap.put("আলু-কার্ডিনাল", new CropInfo(
                "কার্ডিনাল (লাল চামড়ার)",
                "উৎপাদন: ২৫-৩০ টন/হেক্টর\n" +
                        "পরিপক্কতা: ৯০-৯৫ দিন\n" +
                        "বিশেষতা: রপ্তানির জন্য উত্তম\n" +
                        "সেচ: ৪-৫ বার",
                "• উচ্চ মানের বীজ ব্যবহার করুন\n" +
                        "• সঠিক গভীরতায় বপন করুন\n" +
                        "• সময়মতো সেচ দিন",
                "• আর্লি ব্লাইট: ক্লোরোথ্যালোনিল\n" +
                        "• স্ক্যাব রোগ: বোরাক্স স্প্রে\n" +
                        "• আলুর মথ: ইমিডাক্লোপ্রিড",
                "• ইউরিয়া: ২৩০ কেজি (৩ কিস্তিতে)\n" +
                        "• টিএসপি: ১৮০ কেজি\n" +
                        "• জিপসাম: ১০০ কেজি\n" +
                        "• বোরাক্স: ৬ কেজি"
        ));

        varietyDataMap.put("আলু-বারি আলু-৭", new CropInfo(
                "বারি আলু-৭ (স্থানীয় উন্নত জাত)",
                "উৎপাদন: ২৮-৩২ টন/হেক্টর\n" +
                        "পরিপক্কতা: ৮০-৮৫ দিন\n" +
                        "বপন সময়: নভেম্বরের প্রথম সপ্তাহ\n" +
                        "বিশেষতা: রোগ প্রতিরোধী",
                "• জৈব সার ব্যবহার করুন\n" +
                        "• ফসল সংগ্রহে বিলম্ব করবেন না\n" +
                        "• ফসল পর্যায় অনুসরণ করুন",
                "• ভাইরাস রোগ: রোগমুক্ত বীজ ব্যবহার\n" +
                        "• কান্ড পচা: কপার যৌগ স্প্রে\n" +
                        "• পাতার পোকা: স্পিনোসাড",
                "• ইউরিয়া: ২২০ কেজি (৩ কিস্তিতে)\n" +
                        "• টিএসপি: ১৭০ কেজি\n" +
                        "• জিংক সালফেট: ১০ কেজি\n" +
                        "• গোবর সার: ৮ টন"
        ));
    }

    private void setupSpinners() {
        ArrayAdapter<String> cropAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new ArrayList<>(cropVarieties.keySet()));
        cropAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cropSpinner.setAdapter(cropAdapter);

        cropSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCrop = cropSpinner.getSelectedItem().toString();
                String[] varieties = cropVarieties.get(selectedCrop);

                ArrayAdapter<String> varietyAdapter = new ArrayAdapter<>(CropInfoActivity.this,
                        android.R.layout.simple_spinner_item, varieties);
                varietyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                varietySpinner.setAdapter(varietyAdapter);
                varietySpinner.setEnabled(true);

                infoLayout.setVisibility(View.GONE);
                loadingProgress.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        varietySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String crop = cropSpinner.getSelectedItem().toString();
                String variety = varietySpinner.getSelectedItem().toString();
                String key = crop + "-" + variety;

                if (varietyDataMap.containsKey(key)) {
                    loadingProgress.setVisibility(View.GONE);
                    infoLayout.setVisibility(View.VISIBLE);

                    CropInfo info = varietyDataMap.get(key);
                    infoTitle.setText(info.getTitle());
                    infoDetails.setText(info.getDescription());
                    cultivationTips.setText(info.getCultivationTips());
                    diseaseInfo.setText(info.getDiseaseManagement());
                    fertilizerInfo.setText(info.getFertilizerRecommendation());
                } else {
                    infoLayout.setVisibility(View.GONE);
                    Toast.makeText(CropInfoActivity.this, "এই জাত সম্পর্কে তথ্য পাওয়া যায়নি", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private static class CropInfo {
        private String title;
        private String description;
        private String cultivationTips;
        private String diseaseManagement;
        private String fertilizerRecommendation;

        public CropInfo(String title, String description, String cultivationTips,
                        String diseaseManagement, String fertilizerRecommendation) {
            this.title = title;
            this.description = description;
            this.cultivationTips = cultivationTips;
            this.diseaseManagement = diseaseManagement;
            this.fertilizerRecommendation = fertilizerRecommendation;
        }

        public String getTitle() { return title; }
        public String getDescription() { return description; }
        public String getCultivationTips() { return cultivationTips; }
        public String getDiseaseManagement() { return diseaseManagement; }
        public String getFertilizerRecommendation() { return fertilizerRecommendation; }
    }
}