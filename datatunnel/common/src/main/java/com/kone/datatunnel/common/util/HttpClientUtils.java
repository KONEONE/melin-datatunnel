package com.kone.datatunnel.common.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.kone.datatunnel.api.DataTunnelException;

public class HttpClientUtils {
    public static void postRequest(String url, String key, String value){
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(key, value));
        postRequest(url, params);
    }

    public static void postRequest(String url, String key1, String value1, String key2, String value2){
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(key1, value1));
        params.add(new BasicNameValuePair(key2, value2));
        postRequest(url, params);
    }

    public static void postRequest(String url, List<NameValuePair> params){
        CloseableHttpClient client = null;
        try{
            client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);

            httpPost.setEntity(new UrlEncodedFormEntity(params));
            client.execute(httpPost);
        }catch(IOException e){
            throw new DataTunnelException(e.getMessage(), e);
        } finally{
            org.apache.http.client.utils.HttpClientUtils.closeQuietly(client);
        }
    }
}
