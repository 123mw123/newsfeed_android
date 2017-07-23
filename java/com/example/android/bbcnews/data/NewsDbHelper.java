package com.example.android.bbcnews.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.os.Build.VERSION_CODES.N;

/**
 * Created by Sai Teja on 2/25/2017.
 */

public class NewsDbHelper extends SQLiteOpenHelper {
    private  static final String DATABASE_NAME ="report.db";
    private static final int DATABASE_VERSION=1;

    public NewsDbHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
String SQL_CREATE_NEWS_TABLE="CREATE TABLE "+ NewsContract.NewsEntry.TABLE_NAME+
        "("+ NewsContract.NewsEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
        + NewsContract.NewsEntry.COLUMN_TITLE+" TEXT NOT NULL, "+
        NewsContract.NewsEntry.COLUMN_DESCRIPTION+" TEXT,"+
        NewsContract.NewsEntry.COLUMN_DATE+" TEXT,"
        + NewsContract.NewsEntry.COLUMN_URL_TO_IMAGE+" TEXT,"
        + NewsContract.NewsEntry.COLUMN_URL+" TEXT)";
        db.execSQL(SQL_CREATE_NEWS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
