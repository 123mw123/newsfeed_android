package com.example.android.bbcnews.sync;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.example.android.bbcnews.MainActivity;
import com.example.android.bbcnews.NewsReport;
import com.example.android.bbcnews.NotificationUtils;
import com.example.android.bbcnews.QueryUtils;
import com.example.android.bbcnews.data.NewsContract;

import java.util.List;

/**
 * Created by Sai Teja on 2/26/2017.
 */

public class NewsreportSyncTask {
    synchronized public static void syncNews(Context context){
        NotificationUtils.notifyUserOfNewsReport(context);
        List<NewsReport> newsReports = QueryUtils.fetchEarthquakeData(MainActivity.BBC_REQUEST_URL);
        ContentValues[] values;
        if(newsReports!=null) {
            values = new ContentValues[newsReports.size()];
            for (int i = 0; i < newsReports.size(); i++) {
                ContentValues value = new ContentValues();
                value.put(NewsContract.NewsEntry.COLUMN_TITLE, newsReports.get(i).getTitle());
                value.put(NewsContract.NewsEntry.COLUMN_DATE, newsReports.get(i).getDate());
                value.put(NewsContract.NewsEntry.COLUMN_DESCRIPTION, newsReports.get(i).getDescription());
                value.put(NewsContract.NewsEntry.COLUMN_URL_TO_IMAGE, newsReports.get(i).getUrlToImage());
                value.put(NewsContract.NewsEntry.COLUMN_URL, newsReports.get(i).getUrl());
                values[i] = value;

            }


            if (values != null && values.length != 0) {
                int k = context.getContentResolver().delete(NewsContract.NewsEntry.CONTENT_URI, null, null);
                int j = context.getContentResolver().bulkInsert(NewsContract.NewsEntry.CONTENT_URI, values);


               

            }
        }


        }
}
