package com.some.btc;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * use fetch() method
 * if you want to know how to use, see Main()
 * @author xiaochi
 *
 */
public class BtcInfoFetcher {

    /**
     * @param args
     */
    public static void main(String[] args) throws JSONException {
        // test
    	// also an example
        BtcInfoFetcher btcInfoFetcher = new BtcInfoFetcher();
        JSONObject jsonObject = btcInfoFetcher.fetch();
        System.out.println(jsonObject.toString());
    }
    
    /**
     * 拉取信息 btcchina|MtGox
     * @return JSONObject
     */
    public JSONObject fetch(String platform) throws JSONException {
      String jsonString = fetchRaw(platform);
      JSONObject jsonObject = new JSONObject(jsonString);
       Log.e("mq", "fetch data:"+jsonObject.toString());
      return jsonObject.getJSONObject("ticker");
    }

    /**
     * fetch the json string
     * it may take 30 seconds or more
     * @return
     */
    public String fetchRaw(String platform) {
        double random = Math.random();
        
        String url = "http://info.btc123.com/lib/jsonProxyEx.php?type="+platform+"Ticker&suffix=" + random;
        System.out.println(url);
        
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        request.addHeader("Referer", "http://info.btc123.com/index_btcchina.php");
        request.addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
      
        HttpResponse response = null;
            try {
                response = client.execute(request);
                Log.e("mq", "!!!!!!!!!!!!!!");
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e) {
                Log.e("mq","????????????????");
            }
        Log.e("mq", "response:"+response);
        //System.out.println("Response Code: " +
        //response.getStatusLine().getStatusCode());
      
        BufferedReader rd = null;
            try {
                rd = new BufferedReader(  
                    new InputStreamReader(response.getEntity().getContent()));
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }  
        String line = "";
        StringBuffer stringBuffer = new StringBuffer();
        try {
                while((line = rd.readLine()) != null) {  
                    System.out.println(line);
                    stringBuffer.append(line);
                    }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    
        return stringBuffer.toString();
    }
}
