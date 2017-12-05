package com.example.tavernier.miningcharts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import java.util.PriorityQueue;

public class MainActivity extends AppCompatActivity {
    private Button[] buttons = null;
    private WebView[] webViews = null;
    private AsyncExample[] asyncExamples= null;
    private String[] params = null;
    private int nbObjects = 2;
    private TextView[] textViews = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Listes d'objets
        buttons = new Button[nbObjects];
        buttons[0] = findViewById(R.id.button0);
        buttons[1] = findViewById(R.id.button1);

        webViews = new WebView[nbObjects];
        webViews[0] = findViewById(R.id.webView0);
        webViews[1] = findViewById(R.id.webView1);

        params = new String[nbObjects];
        params[0] = "GPUTemp";
        params[1] = "CPUTemp";

        textViews = new TextView[nbObjects];
        textViews[0] = findViewById(R.id.textView0);
        textViews[1] = findViewById(R.id.textView1);

        asyncExamples = new AsyncExample[nbObjects];

        for (int i = 0; i < nbObjects; i++) {
            webViews[i].getSettings().setJavaScriptEnabled(true);
            webViews[i].setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    return super.shouldOverrideUrlLoading(view, request);
                }
            });
            //webViews[i].setVisibility(View.GONE);
            asyncExamples[i] = new AsyncExample(this, webViews[i], params[i], textViews[i]);
            asyncExamples[i].execute();
            final int j = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (webViews[j].getVisibility() == View.VISIBLE) {
                        webViews[j].setVisibility(View.GONE);
                    } else {
                        webViews[j].setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }
}
