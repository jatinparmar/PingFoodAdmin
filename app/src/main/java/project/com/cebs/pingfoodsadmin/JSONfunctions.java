package project.com.cebs.pingfoodsadmin;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jatin on 19-07-2017.
 */

public class JSONfunctions
{
    public static JSONObject getJSONfromURL1(String urlString)throws  Exception
    {
        try
        {
            URL url = new URL(urlString);
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            connection.connect();
            Log.d("Test","Connected");
            InputStream is=connection.getInputStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            String line= br.readLine();
            StringBuffer sb=new StringBuffer();
            while(line!=null)
            {
                sb.append(line+"\n");
                line= br.readLine();
            }

            return new JSONObject(new String(sb));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return new JSONObject("{data: }");
    }

    public static JSONObject getJSONfromURLPost(String url,String json) {
        InputStream is = null;
        String result = "";
        JSONObject jArray = null;

        // Download JSON data from URL
        try {
            StringEntity se = new StringEntity(json);
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);

            httppost.setEntity(se);
            httppost.setHeader("Accept", "application/json");
            httppost.setHeader("Content-type", "application/json");
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();

        } catch (Exception e) {
            Log.e("log_tag", "Error in http connection " + e.toString());
        }

        // Convert response to string
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
        } catch (Exception e) {
            Log.e("log_tag", "Error converting result " + e.toString());
        }

        try {

            jArray = new JSONObject(result);
        } catch (JSONException e) {
            Log.e("log_tag", "Error parsing data " + e.toString());
        }

        return jArray;
    }

    public static JSONObject getJSONfromURL(String url) {
        InputStream is = null;
        String result = "";
        JSONObject jArray = null;

        // Download JSON data from URL
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();

        } catch (Exception e) {
            Log.e("log_tag", "Error in http connection " + e.toString());
        }

        // Convert response to string
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
        } catch (Exception e) {
            Log.e("log_tag", "Error converting result " + e.toString());
        }

        try {

            jArray = new JSONObject(result);
        } catch (JSONException e) {
            Log.e("log_tag", "Error parsing data " + e.toString());
        }

        return jArray;
    }

}
