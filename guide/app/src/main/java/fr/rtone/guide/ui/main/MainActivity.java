package fr.rtone.guide.ui.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import fr.rtone.guide.R;
import fr.rtone.guide.ui.home.HomeActivity;

public class MainActivity extends AppCompatActivity {

    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this /*src*/, HomeActivity.class /*dest*/);
                startActivity(intent);
                //finish(); // Termine cette activite - On préfere le faire dans le manifest
            }
        }, 2000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (timer!=null)
            timer.cancel();
    }
}
