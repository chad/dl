package com.chadfowler.api;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by chad on 26/12/13.
 */
public class Posticle {
    HttpPost httpPost;
    JSONObject params;
    String oauthToken;

    public Posticle(String uri) {
        httpPost = new HttpPost("http://a.wunderlist.com/api/v1" + uri);
    }

    public Posticle(String uri, String oauthToken) {
        this(uri);
        this.oauthToken = oauthToken;
    }

    public void addParam(String key, String value) {
        if (params == null) {
            params = new JSONObject();
        }
        try {
            params.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private String processHttpResponse(HttpResponse httpResponse) throws IOException {
        HttpEntity httpEntity = httpResponse.getEntity();
        InputStream is = httpEntity.getContent();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);

            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "n");
            }
            is.close();
            return sb.toString();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }

    }


    public String makeHttpRequest() throws IOException {

        httpPost.setEntity(new StringEntity(params.toString()));
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("X-Client-ID", "f42a83cb136750a7d8b8"); //DL
        if (oauthToken != null) {
            httpPost.setHeader("X-Access-Token", oauthToken);
        }

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResponse = httpClient.execute(httpPost);

        return processHttpResponse(httpResponse);
    }
}
