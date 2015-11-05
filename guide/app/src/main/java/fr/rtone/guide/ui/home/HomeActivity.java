package fr.rtone.guide.ui.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import fr.rtone.guide.R;
import fr.rtone.guide.ui.listing.ListingActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void showHotel(View view) {
        Intent intent = new Intent(HomeActivity.this, ListingActivity.class);
        intent.putExtra("isHotel", true);
        startActivity(intent);
    }

    public void showResto(View view) {
        // Exemple 2
        ListingActivity.show(HomeActivity.this, false);
    }
}
