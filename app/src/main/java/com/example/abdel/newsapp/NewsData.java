package com.example.abdel.newsapp;

public class NewsData {
    private String mTitle, mUrl, mTime,mSection;

    public NewsData(String title,String section, String time, String url) {
        mTime = time;
        mTitle = title;
        mUrl = url;
        mSection=section;
    }


    /**
     * Get the News section
     */
    public String getSection() {
        return mSection;
    }

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
