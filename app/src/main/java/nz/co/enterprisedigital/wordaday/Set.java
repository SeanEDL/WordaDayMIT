package nz.co.enterprisedigital.wordaday;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;

public class Set extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        //Initialise preferences and variables
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = preferences.edit();
        final CheckBox checkBox =  (CheckBox) findViewById(R.id.checkBox);

        checkBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //If user wants notifications, saves preference so when MainApplication runs, it knows to run notification service
                if(checkBox.isChecked()){
                    editor.putString("newNotif","true");
                    editor.apply();
                }
                else{
                    editor.putString("newNotif","false");
                    editor.apply();
                }
            }
        });
    }
}
