package com.example.android.bbcnews.sync;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

/**
 * Created by Sai Teja on 2/26/2017.
 */

public class NewsreportSyncUtils {
    private static boolean sInitialized=false;
    private static final int SYNC_INTERVAL_HOURS = 1;
    private static final int SYNC_INTERVAL_SECONDS = (int) TimeUnit.MINUTES.toSeconds(SYNC_INTERVAL_HOURS);
    private static final int SYNC_FLEXTIME_SECONDS = SYNC_INTERVAL_SECONDS ;
    private static final String BBC_NEWS_SYNC_TAG="bbcnews-sync";
    static void scheduleFirebaseJobDispatcherSync(@NonNull final Context context) {
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Job syncNewsreportJob= dispatcher.newJobBuilder()
                                .setService(NewsreportFirbaseJobService.class)
               .setTag(BBC_NEWS_SYNC_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(SYNC_INTERVAL_SECONDS,SYNC_INTERVAL_SECONDS+SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true)
                .build();
        dispatcher.schedule(syncNewsreportJob);
    }
  
    public static void startImmediateSync(@NonNull final Context context) {
        scheduleFirebaseJobDispatcherSync(context);
        Intent intentToSyncImmediately=new Intent(context,NewsreportSyncIntentService.class);
        context.startService(intentToSyncImmediately);

    }
}
