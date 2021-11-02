package com.example.booksstore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class WebViewActivity extends AppCompatActivity {

    public static final String URL_EXTRA = "URL_EXTRA";
    WebView webView;
    TextView error;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        String url = getIntent().getStringExtra(URL_EXTRA);
        webView = (WebView) findViewById(R.id.webViewID);
        error = (TextView) findViewById(R.id.error);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient());

        if (url != null){
            webView.loadUrl(url);
        } else {
            error.setVisibility(View.VISIBLE);
            webView.setVisibility(View.INVISIBLE);
        }
    }
}
