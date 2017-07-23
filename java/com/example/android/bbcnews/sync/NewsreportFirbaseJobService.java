package com.example.android.bbcnews.sync;
import android.os.AsyncTask;
import android.content.Context;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.RetryStrategy;

/**
 * Created by Sai Teja on 2/26/2017.
 */

public class NewsreportFirbaseJobService extends JobService {
    private AsyncTask<Void, Void, Void> mFetchNewsTask;
    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        mFetchNewsTask=new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                NewsreportSyncTask.syncNews(getApplicationContext());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                jobFinished(jobParameters, false);

            }
        };
mFetchNewsTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if(mFetchNewsTask!=null){
            mFetchNewsTask.cancel(true);
        }
        return true;
    }
}
