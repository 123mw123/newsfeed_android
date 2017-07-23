package com.example.android.bbcnews;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.bbcnews.data.NewsContract;

import java.util.List;

import static android.R.attr.resource;
import static java.security.AccessController.getContext;

/**
 * Created by Sai Teja on 2/25/2017.
 */

public class NewsReportAdapter extends CursorAdapter {
    public NewsReportAdapter(Context context, Cursor c) {
        super(context, c,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.newsreport_list_item,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView titleView=(TextView) view.findViewById(R.id.title);
        titleView.setText(cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_TITLE)));
        TextView descriptionView=(TextView) view.findViewById(R.id.description);
        descriptionView.setText(cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_DESCRIPTION)));
        TextView dateView=(TextView) view.findViewById(R.id.date);
        dateView.setText(cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_DATE)));
        ImageView imageView=(ImageView) view.findViewById(R.id.image);
        Glide.with(context).load(cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_URL_TO_IMAGE)))
                .into(imageView);
    }


 
}
