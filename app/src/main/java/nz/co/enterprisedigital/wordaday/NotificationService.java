package nz.co.enterprisedigital.wordaday;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ListView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static nz.co.enterprisedigital.wordaday.MainActivity.intent;

/**
 * Created by Sean Cardiff on 8/04/2018.
 */

public class NotificationService extends Service {

    Timer timer;
    TimerTask timerTask;
    String TAG = "Timers";
    int Your_X_SECS = 5;


    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);

        startTimer();

        return START_STICKY;
    }


    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");


    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        stoptimertask();
        super.onDestroy();


    }

    final Handler handler = new Handler();


    public void startTimer() {
        timer = new Timer();

        initializeTimerTask();

        timer.schedule(timerTask, 0, Your_X_SECS * 86400); //
    }

    public void stoptimertask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void initializeTimerTask() {

        timerTask = new TimerTask() {
            public void run() {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String level = preferences.getString("lvl", "");
                ListView listView;
                Random rand = new Random();
                String[] listOfWords = new String[0];
                String wrd;
                int loc;
                loc = rand.nextInt(listOfWords.length);

                switch (level) {
                    case "Beginner": {
                        listOfWords = getResources().getStringArray(R.array.words);
                        break;
                    }
                    case "Intermediate": {
                        listOfWords = getResources().getStringArray(R.array.words2);
                        break;
                    }
                    case "Advanced": {
                        listOfWords = getResources().getStringArray(R.array.words3);
                        break;
                    }
                }
                wrd = listOfWords[loc];

                //Display notification, only if client has selected it
                String notificationPref = preferences.getString("newNotif", "");
                if (notificationPref.equals("true")) {
                    android.support.v4.app.NotificationCompat.Builder mBuilder = new android.support.v4.app.NotificationCompat.Builder(getApplicationContext());

                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

                    mBuilder.setContentIntent(pendingIntent);

                    mBuilder.setSmallIcon(R.drawable.ic_notifications_black_24dp);
                    mBuilder.setContentTitle(wrd);
                    mBuilder.setContentText("Click to see your word of the day!");

                    NotificationManager mNotificationManager =

                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                    mNotificationManager.notify(001, mBuilder.build());
                }

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("word", wrd);
                editor.apply();
            }
        };
    }
}