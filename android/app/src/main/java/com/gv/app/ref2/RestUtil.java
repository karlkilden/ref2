package com.gv.app.ref2;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class RestUtil {

    private DefaultHttpClient client;

    public RestUtil() {
        client = new DefaultHttpClient();
        String url = "http://10.0.1.9:8080/ref2-web/api/poll/list/meta2";
        try {
            String response = doGet(url);
            Log.i("Rest response", "" + response);
        } catch (Exception e) {
            Log.e("Rest error", e.getMessage());
        }
    }

    public String doGet(String URI) throws Exception {
        return convertStreamToString(doGetStream(URI));
    }

    public InputStream doGetStream(String URI) throws Exception {
        HttpGet httpget = new HttpGet(URI);
        httpget.getParams().setBooleanParameter("http.protocol.expect-continue", false);
//        addHeaders(httpget);
        HttpResponse response = client.execute(httpget);
        HttpEntity entity = response.getEntity();
        InputStream instream = entity.getContent();
        return instream;
    }

    public void addHeaders(HttpRequestBase requestBase) throws UnsupportedEncodingException {
        requestBase.addHeader("username", "kk");
        requestBase.addHeader("Accept", "*/*");
    }

    private String convertStreamToString(InputStream is) {
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            // TODO: Error handling?
        } finally {
            closeStream(is);
        }
        return sb.toString();
    }

    private void closeStream(InputStream is) {
        try {
            is.close();
        } catch (IOException e) {
            // TODO: Error handling?
        }
    }
}