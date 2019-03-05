

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.*;

public class jsonFromUrlHelper {

  private static String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }

  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
//    InputStream is = new URL(url).openStream();
	  HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
    connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
    connection.connect();
    BufferedInputStream is = new BufferedInputStream(connection.getInputStream());
    try {
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
      String jsonText = readAll(rd);
      JSONObject json = new JSONObject(jsonText);
      return json;
//      System.out.println(json.toString());
//      System.out.println(json.getString("result"));
      
    } finally {
    	if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
            }
        }
        if (connection != null) {
            connection.disconnect();
        }
    }
  }
  
  //-------------------getUserId
//  public static int readUserId(Message m) throws IOException, JSONException {
//	  JSONObject j = json.getJSONObject("from");
//	  int a = j.getInt("id");
//	  return a;
//    		
//  }
  
  
  
  
  

//  public static void main(String[] args) throws IOException, JSONException {
////    JSONObject json = readJsonFromUrl("https://etherscamdb.info/api/check/0xDaa29859836D97C810c7F9D350D4A1B3E8CafC9a");
//	  JSONObject json = readJsonFromUrl("https://etherscamdb.info/api/check/wuxiaworld.com");
//	  System.out.println(json.toString());
//    System.out.println(json.getString("result"));
//  }
}