package nz.co.enterprisedigital.wordaday;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //Initialise variables
    ListView listView;
    private Random rand = new Random();
    public String[] listOfWords = new String[0];
    String Level = null;
    static String wrd;
    int loc;
    static Intent intent;
    Bundle extras;

    //Sets word list according to level
    void init (){
        Intent intent2 = getIntent();
        Level = intent2.getStringExtra("Level");
        switch (Level){
            case "Beginner":{
                listOfWords = getResources().getStringArray(R.array.words);
                break;
            }
            case "Intermediate":{
                listOfWords = getResources().getStringArray(R.array.words2);
                break;
            }
            case "Advanced":{
                listOfWords = getResources().getStringArray(R.array.words3);
                break;
            }
        }
    }

    //Initialises random word intent
    void init_intent(){
        //Sends position of word in word list, and level word is in
        loc = rand.nextInt(listOfWords.length);
        wrd = listOfWords[loc];
        intent = new Intent(MainActivity.this, SecondActivity.class);
        extras = new Bundle();
        extras.putString("Word", wrd);
        extras.putString("Level", Level);
        intent.putExtras(extras);
    }

    //START
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialises daily word notification, if user selected on landing page
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String notificationPref = preferences.getString("newNotif", "");

        if (notificationPref.equals("true")) {
            startService(new Intent(this, NotificationService.class));
        }

        //Run init
        init();

        //Initialise listview with words from the word list
        listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, listOfWords);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                Bundle extras = new Bundle();
                extras.putString("Word", listView.getItemAtPosition(i).toString());
                extras.putString("Level", Level);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        listView.setAdapter(mAdapter);
    }

    //Creates option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Runs
    public void daily(){
        //Gets today's word as stored by daily word notification service
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        wrd = preferences.getString("word", "");

        //Creates intent using todays word and the level it is from
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        Bundle extras = new Bundle();
        extras.putString("Word", wrd);
        extras.putString("Level", Level);
        intent.putExtras(extras);
        //Starts SecondActivity
        startActivity(intent);

        //Changes function to send notification for testing
        /*android.support.v4.app.NotificationCompat.Builder mBuilder = new android.support.v4.app.NotificationCompat.Builder(this);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.ic_notifications_black_24dp);
        mBuilder.setContentTitle(wrd);
        mBuilder.setContentText("Click to see your word of the day!");
        NotificationManager mNotificationManager =

                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(001, mBuilder.build());*/
    }

    //Option menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.settings:
                Intent settings = new Intent(MainActivity.this, Set.class);
                startActivity(settings);
                return true;

            case R.id.random:
                init_intent();
                startActivity(intent);
                return true;

            case R.id.daily:
                daily();
                return true;

            case R.id.difficulty:
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("firstTime","");
                editor.apply();
                Intent myIntent = new Intent(MainActivity.this,Startup.class);
                startActivity(myIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
