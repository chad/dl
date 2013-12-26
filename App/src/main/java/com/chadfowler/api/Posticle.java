package com.chadfowler.api;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chad on 26/12/13.
 */
public class Posticle {
    HttpPost httpPost;
    List<NameValuePair> params;

    public Posticle(String uri) {
        httpPost = new HttpPost("http://a.wunderlist.com/api/v1" + uri);
    }

    public void addParam(String key, String value) {
        if (params == null) {
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("client_id", "802ddb140a9ee060de8c")); // DL
        }
        params.add(new BasicNameValuePair(key, value));
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

        httpPost.setEntity(new UrlEncodedFormEntity(params));

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResponse = httpClient.execute(httpPost);

        return processHttpResponse(httpResponse);
    }
}
