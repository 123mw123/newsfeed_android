package com.example.android.bbcnews.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Sai Teja on 2/25/2017.
 */

public class NewsProvider extends ContentProvider {
    public static final String LOG_TAG=ContentProvider.class.getSimpleName();
    public static final int CODE_NEWSREPORT=100;
    private static final int CODE_NEWSREPORT_ID=101;
    public static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {

        sUriMatcher.addURI(NewsContract.CONTENT_AUTHORITY,NewsContract.PATH_NEWSREPORT,CODE_NEWSREPORT);
        sUriMatcher.addURI(NewsContract.CONTENT_AUTHORITY,NewsContract.PATH_NEWSREPORT+"/#",CODE_NEWSREPORT_ID);
    }
    private NewsDbHelper mNewsDbHelper;
    @Override
    public boolean onCreate() {
        mNewsDbHelper=new NewsDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database=mNewsDbHelper.getReadableDatabase();
        Cursor cursor=null ;
        int match=sUriMatcher.match(uri);
        switch(match){
            case CODE_NEWSREPORT :
            cursor=database.query(NewsContract.NewsEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case CODE_NEWSREPORT_ID:
                selection= NewsContract.NewsEntry._ID+"=?";
                selectionArgs=new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor =database.query(NewsContract.NewsEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);

                    Log.i(LOG_TAG,"Hello "+sUriMatcher.match(uri) );

                break;
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db=mNewsDbHelper.getWritableDatabase();
        Log.i(LOG_TAG,"Hello "+sUriMatcher.match(uri) );
        switch (sUriMatcher.match(uri)){

            case CODE_NEWSREPORT:

                db.beginTransaction();
                int rowsinserted=0;
                try{
                    for(ContentValues value:values){
                        long _id=db.insert(NewsContract.NewsEntry.TABLE_NAME,null,value);
                        if (_id != -1) {
                            rowsinserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                }finally {
                    db.endTransaction();
                }
                if (rowsinserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return rowsinserted;
            default:
                return super.bulkInsert(uri, values);
        }
        }






    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int numRowsDeleted;
        if (null == selection) selection = "1";
        switch (sUriMatcher.match(uri)) {
            case CODE_NEWSREPORT:
                numRowsDeleted=mNewsDbHelper.getWritableDatabase().delete(NewsContract.NewsEntry.TABLE_NAME, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (numRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numRowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
