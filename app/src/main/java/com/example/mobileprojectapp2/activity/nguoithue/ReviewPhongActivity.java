package com.example.mobileprojectapp2.activity.nguoithue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.example.mobileprojectapp2.R;

public class ReviewPhongActivity extends AppCompatActivity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_phong);
        anhXa();
        String video = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/KKC3nAMGaAo?si=hlQnjtxSVmWz9JRI\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        webView.loadData(video,"text/html","utf-8");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
    }
    private void anhXa(){
        webView = findViewById(R.id.videoReview);
    }
}