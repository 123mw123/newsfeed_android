package com.example.android.bbcnews.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Sai Teja on 2/25/2017.
 */

public class NewsContract  {
    public static final String CONTENT_AUTHORITY="com.example.android.bbcnews";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_NEWSREPORT = "newsreport";

    public static final class NewsEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_NEWSREPORT)
                .build();
        public static final String TABLE_NAME="newsreport";
        public final static String _ID=BaseColumns._ID;
        public static final String COLUMN_TITLE="title";
        public static final String COLUMN_DESCRIPTION="description";
        public static final String COLUMN_DATE="date";
        public static final String COLUMN_URL_TO_IMAGE="imageurl";
        public static final String COLUMN_URL="url";

    }
}
