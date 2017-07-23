package com.example.android.bbcnews;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;

import static android.os.Build.VERSION_CODES.N;
import static java.security.AccessController.getContext;

/**
 * Created by Sai Teja on 2/25/2017.
 */

public class QueryUtils {
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse="";
        if(url==null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection= (HttpURLConnection) url.openConnection();
           urlConnection.setReadTimeout(20000 /* milliseconds */);
          urlConnection.setConnectTimeout(25000 /* milliseconds */);
      urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        }
        catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
     /*   HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Log.i(LOG_TAG,"success");
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();
            return response;
        } finally {
            urlConnection.disconnect();
        }*/

    }
    private static ArrayList<NewsReport> extractFeatureFromJson(String NewsReportJSON) {
        ArrayList<NewsReport> NewsReports= new ArrayList<NewsReport>();
        if (TextUtils.isEmpty(NewsReportJSON)) {
            return null;
        }
        try{
            JSONObject baseJSONResponse=new JSONObject(NewsReportJSON);
            JSONArray newsReportArray=baseJSONResponse.getJSONArray("articles");
            for(int i=0;i<newsReportArray.length();i++){
                JSONObject newsReportObject=newsReportArray.getJSONObject(i);
                NewsReports.add(new NewsReport(newsReportObject.getString("title"),newsReportObject.getString("description"),
                        newsReportObject.getString("url"),newsReportObject.getString("publishedAt"),newsReportObject.getString("urlToImage")));
            }
            return NewsReports;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static ArrayList<NewsReport> fetchEarthquakeData(String requestUrl) {
        URL url = createUrl(requestUrl);
        Log.i(LOG_TAG,"calling fetchEarthquakeData");
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
       ArrayList<NewsReport> newsReportArrayList = extractFeatureFromJson(jsonResponse);
      
        return newsReportArrayList;

    }
}
