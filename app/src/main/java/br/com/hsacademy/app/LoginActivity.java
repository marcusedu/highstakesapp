package br.com.hsacademy.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LoginActivity extends AppCompatActivity {
    private WebView wView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        wView = (WebView) findViewById(R.id.webview);
        wView.setWebChromeClient(new WebChromeClient());
        wView.setWebViewClient(new WebViewClient());
        wView.clearCache(true);
        wView.clearHistory();
        wView.getSettings().setJavaScriptEnabled(true);
        wView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wView.loadData("<html><body style=\"margin:0;\"><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/ycPmjdwxIOQ\" frameborder=\"0\" allowfullscreen></iframe></body></html>"
                , "text/html", null);
    }
}
