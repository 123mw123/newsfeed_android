package com.example.android.bbcnews.sync;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by Sai Teja on 2/26/2017.
 */

public class NewsreportSyncIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param /name Used to name the worker thread, important only for debugging.
     */
    public NewsreportSyncIntentService() {
        super("NewsreportSyncIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        NewsreportSyncTask.syncNews(this);

    }
}
