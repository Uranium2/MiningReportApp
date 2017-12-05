package com.example.tavernier.miningcharts;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;

public class MainActivity extends AppCompatActivity {
    private Button[] buttons, buttonsOption;
    private WebView[] webViews;
    private AsyncExample[] asyncExamples;
    private String[] paramLabel, paramColor, paramSource;
    private ColorPicker[] colorPickers;
    private int nbObjects = 2, defaultR = 0, defaultG = 0, defaultB = 0;
    private int[] defaultColors;
    private  int defaultA = 1;
    private TextView[] textViews;
    private Button buttonMenuOption, buttonBack;
    private String colorSeparator = "%2C";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paramColor = new String[nbObjects];
        String defaultColor = "rgba(" + defaultR + colorSeparator + defaultG + colorSeparator
                + defaultB + colorSeparator + defaultA + ")";
        paramColor[0] = defaultColor;
        paramColor[1] = defaultColor;
        loadActivity();

    }

    private void loadActivity() {
        setContentView(R.layout.activity_main);
        // Listes d'objets
        buttons = new Button[nbObjects];
        buttons[0] = findViewById(R.id.button0);
        buttons[1] = findViewById(R.id.button1);

        buttonMenuOption = findViewById(R.id.buttonOption);
        buttonMenuOption.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.graph_option);
                for (int i = 0; i < nbObjects; i++) {
                    buttonBack = findViewById(R.id.buttonBack);
                    buttonsOption = new Button[nbObjects];
                    buttonsOption[0] = findViewById(R.id.buttonOption0);
                    buttonsOption[1] = findViewById(R.id.buttonOption1);
                    final int j = i;
                    colorPickers[j] = new ColorPicker(MainActivity.this,
                            defaultR, defaultG, defaultB);
                    buttonsOption[i].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            colorPickers[j].show();
                            colorPickers[j].setCallback(new ColorPickerCallback() {
                                @Override
                                public void onColorChosen(@ColorInt int color) {
                                    String pickedColor = "rgba(" +
                                            Integer.toString(Color.red(color)) + colorSeparator +
                                            Integer.toString(Color.green(color)) + colorSeparator +
                                            Integer.toString(Color.blue(color)) + colorSeparator +
                                            defaultA + ")";
                                    defaultR = Color.red(color);
                                    defaultG = Color.green(color);
                                    defaultB = Color.blue(color);
                                    paramColor[j] = pickedColor;
                                    colorPickers[j].hide();
                                }
                            });
                        }
                    });
                    buttonBack.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            loadActivity();
                        }
                    });
                }
            }
        });

        webViews = new WebView[nbObjects];
        webViews[0] = findViewById(R.id.webView0);
        webViews[1] = findViewById(R.id.webView1);

        paramLabel = new String[nbObjects];
        paramLabel[0] = "GPUTemp";
        paramLabel[1] = "GPUUsage";

        paramSource = new String[nbObjects];
        paramSource[0] = "DataSource0.txt";
        paramSource[1] = "DataSource1.txt";

        textViews = new TextView[nbObjects];
        textViews[0] = findViewById(R.id.textView0);
        textViews[1] = findViewById(R.id.textView1);

        asyncExamples = new AsyncExample[nbObjects];

        colorPickers = new ColorPicker[nbObjects]; // plusieurs tableaux pour diff options?

        for (int i = 0; i < nbObjects; i++) {
            webViews[i].getSettings().setJavaScriptEnabled(true);
            webViews[i].setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    return super.shouldOverrideUrlLoading(view, request);
                }
            });
            //webViews[i].setVisibility(View.GONE);
            asyncExamples[i] = new AsyncExample(this, webViews[i], paramLabel[i],
                    paramColor[i], paramSource[i], textViews[i]);
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
