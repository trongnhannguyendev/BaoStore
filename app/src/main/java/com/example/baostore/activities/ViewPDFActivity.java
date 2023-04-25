package com.example.baostore.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.baostore.R;

public class ViewPDFActivity extends AppCompatActivity {
    WebView webViewPDF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_view_pdfactivity);

            webViewPDF = findViewById(R.id.webviewPDF);
            webViewPDF.getSettings().setSupportZoom(true);
            webViewPDF.getSettings().setJavaScriptEnabled(true);
            String url = "https://www.africau.edu/images/default/sample.pdf";
            webViewPDF.loadUrl("https://docs.google.com/gview?embedded=true&url=" + url);
        } catch (Exception e){
            Toast.makeText(this, getString(R.string.text_something_wrong), Toast.LENGTH_SHORT).show();
            Log.d(getString(R.string.debug_LoginActivity), "Error: "+e);
            finish();
        }
    }

}