package com.example.abdel.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter  extends ArrayAdapter<NewsData> {
    /**
     * Constructs a new {@link NewsAdapter}.
     *
     * @param context of the app
     * @param news    is the list of News, which is the data source of the adapter
     */
    public NewsAdapter(Context context, List<NewsData> news) {
        super(context, 0, news);
    }

    /**
     * Returns a list item view that displays information about the news at it's time
     * in the list of news.
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list, parent, false);
        }

        // Find the news at the given position in the list of news
        NewsData currentNews = getItem(position);

        // Find the TextView with view ID titleView
        TextView titleView = listItemView.findViewById(R.id.title_view);
        String title = currentNews.getTitle();
        // Display the title of the current news in that TextView
        titleView.setText(title);

        TextView sectionView = listItemView.findViewById(R.id.section_view);
        String section = currentNews.getSection();
        // Display the title of the current news in that TextView
        sectionView.setText(section);

        // Find the TextView with view ID titleView
        TextView dateView = listItemView.findViewById(R.id.time_view);
        String date = currentNews.getTime();
        // Display the date of the current news in that TextView
        dateView.setText(date);

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }

}
