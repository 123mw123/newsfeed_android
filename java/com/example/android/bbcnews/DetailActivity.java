package com.example.android.bbcnews;
import android.app.LoaderManager;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.content.CursorLoader;
//import android.content.Loader;
//import android.support.v4.app.LoaderManager;
//import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

import com.example.android.bbcnews.data.NewsContract;

/**
 * Created by Sai Teja on 2/25/2017.
 */

public class DetailActivity  extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<Cursor> {
    private String LOG_TAG=DetailActivity.class.getSimpleName();
    private Uri mCurrentUri;
    private final int EXISTING_REPORT_LOADER=0;
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent=getIntent();
        mCurrentUri=intent.getData();
         webView=(WebView)findViewById(R.id.web);
        getLoaderManager().initLoader(EXISTING_REPORT_LOADER, null,this);
        //webView.loadUrl(String.valueOf(intent.getData()));
    }

    @Override
    public android.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
              NewsContract.NewsEntry.COLUMN_URL
        };
        Log.e(LOG_TAG,"cursorerrrrrrrrrrrrr");
        return new CursorLoader(this, mCurrentUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor cursor) {

        if(cursor.moveToFirst()){
            int columnindex=cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_URL);
            String url=cursor.getString(columnindex);
            webView.loadUrl(url);

        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {

    }


   /* @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(cursor.moveToFirst()){
            int columnindex=cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_URL);
            String url=cursor.getString(columnindex);
            webView.loadUrl(url);
        }
    }



    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }*/
}
