package com.example.douglas.aplicativo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class IdeauWebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ideau_web_view);

        // Recuperar a tag webView
        final WebView webView =
                (WebView) findViewById(R.id.webView);

        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean
            shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("http://www.offpremium.com.br")) {
                    webView.loadUrl(url);
                    return false;
                } else {
                    return true;
                }
            }
        });

        webView.loadUrl("http://www.offpremium.com.br/br/mobile/home");
    }
}
