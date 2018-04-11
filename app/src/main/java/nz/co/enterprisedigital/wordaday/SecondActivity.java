package nz.co.enterprisedigital.wordaday;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    //START
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //Initialise and get extras
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String definition = extras.getString("Word");
        String level = extras.getString("Level");

        String[] wrdStrings = getResources().getStringArray(R.array.words);
        String[] defStrings = getResources().getStringArray(R.array.definition);
        String[] exmpStrings = getResources().getStringArray(R.array.example);

        //Loads words and definitions for correct level
        switch (level) {
            case "Beginner":
                wrdStrings = getResources().getStringArray(R.array.words);
                defStrings = getResources().getStringArray(R.array.definition);
                exmpStrings = getResources().getStringArray(R.array.example);
                break;
            case "Intermediate":
                wrdStrings = getResources().getStringArray(R.array.words2);
                defStrings = getResources().getStringArray(R.array.definition2);
                exmpStrings = getResources().getStringArray(R.array.example2);
                break;
            case "Advanced":
                wrdStrings = getResources().getStringArray(R.array.words3);
                defStrings = getResources().getStringArray(R.array.definition3);
                exmpStrings = getResources().getStringArray(R.array.example3);
                break;
            default:
                Toast.makeText(SecondActivity.this, level,
                        Toast.LENGTH_LONG).show();
                break;
        }

        //Finds location of word in string for later use
        int index = -1;
        for (int i=0;i<wrdStrings.length;i++) {
            if (wrdStrings[i].equals(definition)) {
                index = i;
                break;
            }
        }

        //Sets up text views with relevant texts using word string location from above
        try{
            TextView exm = findViewById(R.id.exampleTextView);
            exm.setText(exmpStrings[index]);
            TextView def = findViewById(R.id.definitionTextView);
            def.setText(defStrings[index]);
            TextView wrd = findViewById(R.id.wordTextView);
            wrd.setText(definition);
        }
        catch(Exception e){
            if (index == -1){
                index = 1;
            }
            TextView exm = findViewById(R.id.exampleTextView);
            exm.setText(exmpStrings[index]);
            TextView def = findViewById(R.id.definitionTextView);
            def.setText(defStrings[index]);
            TextView wrd = findViewById(R.id.wordTextView);
            wrd.setText(definition);
        }

        //Initialise image
        ImageView image;
        image = findViewById(R.id.imageView);
        if (wrdStrings[index].toString().equalsIgnoreCase("bad")) {
            image.setImageResource(R.drawable.bad);
        } else if (wrdStrings[index].toString().equalsIgnoreCase("attack")) {
            image.setImageResource(R.drawable.attack);
        } else if (wrdStrings[index].toString().equalsIgnoreCase("bag")) {
            image.setImageResource(R.drawable.bag);
        } else if (wrdStrings[index].toString().equalsIgnoreCase("atom")) {
            image.setImageResource(R.drawable.atom);
        } else if (wrdStrings[index].toString().equalsIgnoreCase("archer")) {
            image.setImageResource(R.drawable.archer);
        } else if (wrdStrings[index].toString().equalsIgnoreCase("cry")) {
            image.setImageResource(R.drawable.cry);
        } else if (wrdStrings[index].toString().equalsIgnoreCase("stop")) {
            image.setImageResource(R.drawable.stop);
        } else if (wrdStrings[index].toString().equalsIgnoreCase("bright")) {
            image.setImageResource(R.drawable.bright);
        } else if (wrdStrings[index].toString().equalsIgnoreCase("sail")) {
            image.setImageResource(R.drawable.sail);
        } else if (wrdStrings[index].toString().equalsIgnoreCase("fire")) {
            image.setImageResource(R.drawable.fire);
        } else if (wrdStrings[index].toString().equalsIgnoreCase("charge")) {
            image.setImageResource(R.drawable.charge);
        } else if (wrdStrings[index].toString().equalsIgnoreCase("ground")) {
            image.setImageResource(R.drawable.ground);
        } else if (wrdStrings[index].toString().equalsIgnoreCase("force")) {
            image.setImageResource(R.drawable.force);
        } else if (wrdStrings[index].toString().equalsIgnoreCase("reading")) {
            image.setImageResource(R.drawable.reading);
        } else if (wrdStrings[index].toString().equalsIgnoreCase("long")) {
            image.setImageResource(R.drawable.long2);
        }
    }
}
