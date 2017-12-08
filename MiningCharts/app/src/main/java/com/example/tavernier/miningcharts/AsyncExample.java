package com.example.tavernier.miningcharts;

/**
 * Created by Tavernier on 12/1/2017.
 */


import android.os.AsyncTask;
import android.webkit.WebView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

class AsyncExample extends AsyncTask<Void, Void, Void> {
    private HttpURLConnection con;
    private MainActivity mContext;
    private String urlParameters, paramLabel, paramColor, paramSource;
    private WebView mWebView;
    private TextView mtextView;

    public AsyncExample(MainActivity context, WebView webView, String paramLabel,
                        String paramColor, String paramSource, TextView textView) {
        mContext = context;
        mWebView = webView;
        urlParameters = null;
        mtextView = textView;
        this.paramColor = paramColor;
        this.paramLabel = paramLabel;
        this.paramSource = paramSource;
    }

    protected void onPreExecute() {
    }

    @Override
    protected Void doInBackground(Void... params) {
        urlParameters = "user=Antoine&label=" + paramLabel +
                "&file=" + paramSource +
                "&backgroundColor=" + paramColor +
                "&borderColor=" + paramColor +
                "&pointBorderColor=" + paramColor +
                "&pointHoverBackgroundColor=" + paramColor +
                "&pointHoverBorderColor=" + paramColor +
                "&min=0" +
                "&max=100" +
                "&stepSize=50";

        byte[] postData = urlParameters.getBytes(Charset.forName("UTF-8"));
        int postDataLength = postData.length;
        String request = "http://90.90.43.188:6262/api/values";
        URL url;
        try {
            url = new URL(request);
            con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setInstanceFollowRedirects(false);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("charset", "utf-8");
            con.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            OutputStream os = con.getOutputStream();
            os.write(urlParameters.getBytes("UTF-8"));
            os.close();
            con.connect();
            con.getResponseCode();
            mContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String ip = readFullyAsString(con.getInputStream(), "UTF-8");
                        ip = ip.replace("\"", "");
                        mWebView.loadUrl(ip);
                        mtextView.setText(ip);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            con.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String readFullyAsString(InputStream inputStream, String encoding) throws IOException {
        return readFully(inputStream).toString(encoding);
    }

    private ByteArrayOutputStream readFully(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, length);
        }
        return byteArrayOutputStream;
    }
}