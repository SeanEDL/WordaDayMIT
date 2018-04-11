package nz.co.enterprisedigital.wordaday;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

public class Startup extends AppCompatActivity {
    //Creates menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.startup, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //START
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        //Checks to see if program was launched before
        String fLaunch = preferences.getString("firstTime", "");

        if (fLaunch.equals("")){
            editor.putString("firstTime","false");
            editor.apply();
            selection();
        }
        else{
            //If launched before go straight to MainActivity and send through level
            String level = preferences.getString("lvl", "");
            Intent myIntent = new Intent(Startup.this,MainActivity.class);
            myIntent.putExtra("Level", level);
            startActivity(myIntent);
        }

    }
    protected void selection(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = preferences.edit();
        //Initialise spinner
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.levels, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button button =  (Button) findViewById(R.id.button);

        //Parse checkbox and spinner inputs
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //If user wants notifications, saves preference so when MainApplication runs, it knows to run notification service
               CheckBox checkBox =  (CheckBox) findViewById(R.id.checkBoxNewWords);
                if(checkBox.isChecked()){
                    editor.putString("newNotif","true");
                    editor.apply();
                    selection();
                }
                else{
                    editor.putString("newNotif","false");
                    editor.apply();
                    selection();
                }

                //Sends through level and starts MainActivity when user selects level and clicks to start
                final String level = spinner.getSelectedItem().toString();
                Intent myIntent = new Intent(Startup.this,MainActivity.class);
                myIntent.putExtra("Level", level);

                editor.putString("lvl",level);
                editor.apply();
                selection();

                startActivity(myIntent);
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.info2:
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(this);
                }
                builder.setTitle("Info")
                        .setMessage(getResources().getString(R.string.info))
                        .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        //.setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                // myIntent = new Intent(Startup.this,about.class);
                //startActivity(myIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
