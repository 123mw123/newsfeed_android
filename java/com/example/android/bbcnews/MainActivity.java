package com.example.android.bbcnews;

import android.app.LoaderManager;

import android.app.LoaderManager;
import android.content.Context;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import android.content.ContentValues;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.bbcnews.data.NewsContract;
import com.example.android.bbcnews.data.NewsProvider;
import com.example.android.bbcnews.sync.NewsreportSyncUtils;

import android.content.*;
import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.os.Build.VERSION_CODES.N;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private NewsReportAdapter mAdapter;
    public static final String LOG_TAG = MainActivity.class.getName();
    private static final int BBCNews_LOADER_ID = 1;
    public  static final String BBC_REQUEST_URL =
            "https://newsapi.org/v1/articles?source=bbc-news&sortBy=top&apiKey=0e67f414c4d949f687b43b49dedbbeab";
   


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(LOG_TAG, "MainActivity-onCreate");
        ListView newsReportListView = (ListView) findViewById(R.id.list);
        mAdapter = new NewsReportAdapter(this, null);
       

        LoaderManager loaderManager = getLoaderManager();

        Log.i(LOG_TAG, "calling initLoader");
        loaderManager.initLoader(BBCNews_LOADER_ID, null, this);
      
        newsReportListView.setAdapter(mAdapter);

        newsReportListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                
                Uri uri=ContentUris.withAppendedId(NewsContract.NewsEntry.CONTENT_URI,id);
              
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.setData(uri);
                startActivity(intent);
            }
        });

        NewsreportSyncUtils.startImmediateSync(this);
    }


    @Override
    public android.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {




return new android.content.CursorLoader(this, NewsContract.NewsEntry.CONTENT_URI, null, null, null, null) {
    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, "calling onstartloading");
        forceLoad();
    }

    @Override
    public Cursor loadInBackground() {





        Log.i(LOG_TAG, "calling Lodinbackground");
      
        return MainActivity.this.getContentResolver().query(NewsContract.NewsEntry.CONTENT_URI,null,null,null,null);
    }
};

    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor data) {
        Log.i(LOG_TAG, "calling onLoadFinished");
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }




    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.notify, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NotificationUtils.notifyUserOfNewsReport(this);
        return true;
    }
}
