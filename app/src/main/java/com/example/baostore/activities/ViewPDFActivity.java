package com.example.baostore.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.baostore.R;

public class ViewPDFActivity extends AppCompatActivity {
    WebView webViewPDF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdfactivity);
        webViewPDF = findViewById(R.id.webviewPDF);
        webViewPDF.getSettings().setSupportZoom(true);
        webViewPDF.getSettings().setJavaScriptEnabled(true);
        String url = "https://www.africau.edu/images/default/sample.pdf";
        webViewPDF.loadUrl("https://docs.google.com/gview?embedded=true&url="+url);
    }

}