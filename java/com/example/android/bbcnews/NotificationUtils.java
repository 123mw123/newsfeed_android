package com.example.android.bbcnews;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.android.bbcnews.data.NewsContract;

import static android.R.attr.id;
import static android.os.Build.VERSION_CODES.N;

/**
 * Created by Sai Teja on 2/27/2017.
 */

public class NotificationUtils {
    public static final String[] NEWS_REPORT_NOTIFICATION_PROJECTION={
            NewsContract.NewsEntry._ID,
            NewsContract.NewsEntry.COLUMN_TITLE,
            NewsContract.NewsEntry.COLUMN_DESCRIPTION

    };
    public static final int INDEX_NEWS_REPORT_ID = 0;
    public static final int INDEX_TITLE = 1;
    public static final int INDEX_DESCRIPTION = 2;

    private static final int NEWS_REPORT_NOTIFICATION_ID = 3004;

    public static void notifyUserOfNewsReport(Context context){
        Cursor cursor=context.getContentResolver().query(NewsContract.NewsEntry.CONTENT_URI,NEWS_REPORT_NOTIFICATION_PROJECTION
        , null,
                null,
                null);
        if(cursor.moveToFirst()){
            String notificationTitle = context.getString(R.string.app_name);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                    .setColor(ContextCompat.getColor(context,R.color.colorPrimary))
                    .setSmallIcon(R.drawable.ic_drink_notification)
                    .setLargeIcon(largeIcon(context))
                    .setContentTitle(notificationTitle)
                    .setContentText(cursor.getString(INDEX_DESCRIPTION))
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(cursor.getString(INDEX_TITLE)))
                    .setAutoCancel(true);
            Intent detailIntentForToday = new Intent(context, DetailActivity.class);
            detailIntentForToday.setData(ContentUris.withAppendedId(NewsContract.NewsEntry.CONTENT_URI,1));
            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
            taskStackBuilder.addNextIntentWithParentStack(detailIntentForToday);
            PendingIntent resultPendingIntent = taskStackBuilder
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            notificationBuilder.setContentIntent(resultPendingIntent);
            NotificationManager notificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
            }

            notificationManager.notify(NEWS_REPORT_NOTIFICATION_ID, notificationBuilder.build());
            Log.e("notifyyyyyyyyyy","cationnnnnnnnnnnnnnnnnnnnnnnnnnnnn");
        }
        cursor.close();


    }
    private static Bitmap largeIcon(Context context) {
        Resources res = context.getResources();
        Bitmap largeIcon = BitmapFactory.decodeResource(res, R.drawable.ic_local_drink_black_24px);
        return largeIcon;
    }
}
