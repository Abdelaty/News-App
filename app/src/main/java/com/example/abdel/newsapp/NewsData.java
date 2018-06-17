package com.example.abdel.newsapp;

public class NewsData {
    private String mTitle, mUrl, mTime;
    private int mImage;

    public NewsData(String title, String time, String url) {
        mTime = time;
        mTitle = title;
        mUrl = url;
    }


    /**
     * Get the News Content
     */

    public String getTime() {
        return mTime;
    }

    /**
     * Returns the Title of the News.
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Returns the Url of the News.
     */
    public String getUrl() {
        return mUrl;
    }

}
