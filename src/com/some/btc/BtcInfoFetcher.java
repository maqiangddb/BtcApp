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
        BtcInfo btcInfo = btcInfoFetcher.fetch("btcchina");
//        BtcInfo btcInfo = btcInfoFetcher.fetch("MtGox");
        System.out.println(btcInfo.toString());
    }
    
    /**
     * 拉取信息 btcchina|MtGox
     * @return JSONObject
     */
    public BtcInfo fetch(String platform) throws JSONException {
    	BtcInfo btcInfo = new BtcInfo();
      String jsonString = fetchRaw(platform);
      JSONObject jsonObject = new JSONObject(jsonString);
       Log.e("mq", "fetch data:"+jsonObject.toString());
      // here we should use ...
      if (platform == "btcchina") {
    	  System.out.println(jsonObject.toString());
    	  jsonObject = jsonObject.getJSONObject("ticker");
    	  btcInfo.setName("比特币中国");
    	  btcInfo.setBuyPrice(jsonObject.getString("buy"));
    	  btcInfo.setSellPrice(jsonObject.getString("sell"));
    	  btcInfo.setVolume(jsonObject.getString("vol"));
    	  btcInfo.setLastPrice(jsonObject.getString("last"));
      } else if (platform == "MtGox") {
    	  if (jsonObject.getString("result").equals("success")) {
    		  jsonObject = jsonObject.getJSONObject("data");
    		  btcInfo.setName("MtGox");
    		  btcInfo.setLastPrice(jsonObject.getJSONObject("last").getString("display_short"));
    		  btcInfo.setBuyPrice(jsonObject.getJSONObject("buy").getString("display_short"));
    		  btcInfo.setSellPrice(jsonObject.getJSONObject("sell").getString("display_short"));
    		  btcInfo.setVolume(jsonObject.getJSONObject("vol").getString("display_short"));
    	  } else {
    		  System.out.println("error");
    	  }
      }
      return btcInfo;
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
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e) {
            }
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
