package com.example.abdel.newsapp;

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
import java.util.List;

public class QueryUtils {

    private static final String RESPONSE = "response";
    private static final String RESULTS = "results";
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();
    private static final int readTimeout = 10000;
    private static final int connectTimeOut = 15000;
    private static final int defaultResponseCode = 200;

    private QueryUtils() {
    }

    public static List<NewsData> fetchNewsData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);

        }

        // Extract relevant fields from the JSON response and create a list of {@link news}s
        List<NewsData> news = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link news}
        return news;

    }


    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);

        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);

        }
        return url;

    }

    /**
     * +     * Make an HTTP request to the given URL and return a String as the response.
     * +
     */


    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;

        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(readTimeout /* milliseconds */);
            urlConnection.setConnectTimeout(connectTimeOut /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == defaultResponseCode) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the news JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
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

    /**
     * Return a list of {@link NewsData} objects that has been built up from
     * -     * parsing a JSON response.
     * +     * parsing the given JSON response.
     */
    private static List<NewsData> extractFeatureFromJson(String newsJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }

        List<NewsData> news = new ArrayList<>();
        try {  // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(newsJSON);

            // Extract the JSONArray associated with the key called "responses",
            // which represents a list of responses (or news).
            JSONArray newsArray = baseJsonResponse.getJSONObject(RESPONSE).getJSONArray(RESULTS);
            // For each news in the newsArray, create an {@link News} object
            for (int i = 0; i < newsArray.length(); i++) {

                // Get a single news at position i within the list of news
                JSONObject currentNews = newsArray.getJSONObject(i);

                // For a given News, extract the JSONObject associated with the
                // key called "results", which represents a list of all results
                // for that news.


                // Extract the value for the key called "webTitle"
                String title = currentNews.getString("webTitle");


                //  String name = currentNews.getString("pillarName");


                // Extract the value for the key called "sectionName"
                String section = currentNews.getString("sectionName");

                // Extract the value for the key called "time"
                String time = currentNews.getString("webPublicationDate");

                // Extract the value for the key called "Url"
                String url = currentNews.getString("webUrl");

                String name = "No author name";
                try {
                    JSONObject fields = currentNews.getJSONObject("fields");
                    if (fields.has("byline")) {
                        name = fields.getString("byline");
                    }
                } catch (Exception ex) {
                }
                // Create a new {@link News} object with the Title, time, url, authorName
                // and url from the JSON response.
                NewsData guardianNews = new NewsData(title, name, section, time, url);

                // Add the new {@link News} to the list of news.
                news.add(guardianNews);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the News JSON results", e);
        }

        // Return the list of news
        return news;
    }
}
