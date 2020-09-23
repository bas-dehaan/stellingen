package nl.praesent.stellingen;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    //Stellingen array
    private ArrayList<String> al;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Maak een array met alle stellingen
        al = new ArrayList<>();
        // [DEBUG] voeg Buildconfig info toe als stelling
        al.add("Testversie " + BuildConfig.VERSION_NAME + "\n\n\nBuild date:\n23-09-2020\n\nBuild number:\n#" + BuildConfig.VERSION_CODE);
        // Instructies
        al.add("Welkom!");
        al.add("[INSTRUCTIE]\nSwipe een stelling naar links als je oneens bent of naar rechts als je het eens bent");
        al.add("[INSTRUCTIE]\nJe kunt ook gebruik maken van de rode en groene knop");
        al.add("[INSTRUCTIE]\nHoewel niet verplicht, Probeer de stelling te beandwoorden voordat de timer op 0 staat\n\n\nSucces!!!");
        // Voeg alle stellingen toe aan de array
        al.add("Een ananas op een pizza");
        al.add("Met 1 glaasje op achter het stuur stappen");
        al.add("Op het werk poepen");
        al.add("Een product op de verkeerde plek terug leggen in de supermarkt");
        al.add("Doorrijden met een bijna lege tank");
        al.add("Mee lopen met een een aankomende trein");
        al.add("Een anoniem telefoonnummer opnemen");
        al.add("CO2 afkopen bij het boeken van een vliegticket");
        al.add("Fooi geven in een restaurant");
        al.add("Praten tegen huisdieren");
        al.add("Je behoefte doen met de deur open");
        al.add("Pakketje aannemen voor de buren");
        al.add("Je rugleuning naar achteren zetten in het vliegtuig");
        al.add("Geld afgeven dat je gevonden hebt");

        arrayAdapter = new ArrayAdapter<>(this, R.layout.item, R.id.stelling, al);




        final SwipeFlingAdapterView flingContainer = findViewById(R.id.frame);

        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                al.remove(0);
                arrayAdapter.notifyDataSetChanged();
                startCountdown();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                Toast.makeText(MainActivity.this, getString(R.string.nee), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Toast.makeText(MainActivity.this, getString(R.string.ja), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
                //String VolgendeStelling = getString(R.string.class).endsWith(i);
                //al.add(VolgendeStelling);
                //arrayAdapter.notifyDataSetChanged();
                //Log.d("LIST", "notified");
                //i++;
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
            }
        });

        // Indien op de button @id.right geklikt wordt, behandel het als een swipe naar rechts
        Button bRight = findViewById(R.id.right);
        bRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flingContainer.getTopCardListener().selectRight();
            }
        });

        // Indien op de button @id.left geklikt wordt, behandel het als een swipe naar links
        Button bLeft = findViewById(R.id.left);
        bLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flingContainer.getTopCardListener().selectLeft();
            }
        });

    }

    //Progressbar
    private ProgressBar mCountdownBar;
    private TextView mCountdownText;

    private void startCountdown(){
        // Definieer de designelements
        mCountdownBar = findViewById(R.id.countdownbar);
        mCountdownText = findViewById(R.id.countdowntext);
        // Laat een Timer runnen van 7500ms, met stappen van 1ms
        CountDownTimer mCountdown = new CountDownTimer(7500, 1) {

            public void onTick(long millisUntilFinished) {
                mCountdownText.setText(Long.toString((millisUntilFinished + 1500) / 1500));
                mCountdownBar.setProgress((int) (millisUntilFinished / 75));
            }

            public void onFinish() {
                mCountdownText.setText("NU!");
            }
        };
        // Cancel een evt. nog lopende countdown en start een nieuwe
        mCountdown.cancel();
        mCountdown.start();
    }

}