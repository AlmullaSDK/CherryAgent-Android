package com.serbus.amxsdk;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class HTTPRequest extends AsyncTask<String, Void, String>{
    public String requestURL="";
    public String postDataParams;
    public HashMap<String, String> headers;
    public HTTPCallback delegate = null;//Call back interface
    public int res_code=0;
    String charset = "UTF-8";
    public HTTPRequest(String requestURL, String postDataParams,HashMap<String, String> headers,HTTPCallback asyncResponse){
        this.delegate = asyncResponse;//Assigning call back interfacethrough constructor
        this.postDataParams=postDataParams;
        this.headers = headers;
        this.requestURL=requestURL;
    }
    @Override
    protected String doInBackground(String... params) {
        return performPostCall(requestURL,postDataParams);
    }
    @Override
    protected void onPostExecute(String result) {
        //super.onPostExecute(result);
        if(res_code==HttpURLConnection.HTTP_OK){
            delegate.processFinish(result);
        }else{
            delegate.processFailed(res_code, result);
        }
    }
    public String  performPostCall(String requestURL,
                                   String postDataParams) {
        Log.e("HTTP Request URL",requestURL);
        URL url;
        String response = "";
        try {
            url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(30000);
            conn.setConnectTimeout(60000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            headers.put("Content-Type", "application/json");
            for (String headerKey : headers.keySet()) {
                conn.setRequestProperty(headerKey, headers.get(headerKey));
            }
            OutputStream os = conn.getOutputStream();
//            BufferedWriter writer = new BufferedWriter(
//                    new OutputStreamWriter(os, "UTF-8"));
////            writer.write(getPostDataString(postDataParams));
//            byte[] input = postDataParams.getBytes("utf-8");
//            os.write(input, 0, input.length);
//            writer.flush();
//            writer.close();
//            os.close();
            byte[] input = postDataParams.getBytes(charset);
            os.write(input, 0, input.length);
            int responseCode=conn.getResponseCode();
            Log.e("HTTP Response Code", Integer.toString(responseCode));
            res_code=responseCode;
//            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
//            String f = in.readLine();
//            Log.e("RESPONSE",f);
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }else if (responseCode == 500) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                response="";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("RESPONSE FINAL", response);
        return response;
    }
}


