package fr.rtone.guide.ui.listing;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import fr.rtone.guide.R;
import fr.rtone.guide.models.Restaurant;
import fr.rtone.guide.ui.details.DetailActivity;

public class ListingActivity extends AppCompatActivity {

    private TextView textViewTitle;
    private ListView listViewData;

    static final String INTENT_IS_HOTEL = "isHotel";

    List<Restaurant> restoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);

        textViewTitle = (TextView)findViewById(R.id.textViewTitle);
        listViewData = (ListView)findViewById(R.id.listViewData);

        if (getIntent().getExtras() != null) {
            boolean isHotel = getIntent().getExtras().getBoolean(INTENT_IS_HOTEL);
            boolean isHotel2 = getIntent().getBooleanExtra(INTENT_IS_HOTEL, false);

            if (isHotel) {
                textViewTitle.setText(/*getString(R.string.txtHotel)*/"Les hotels");
            }
            else {
                textViewTitle.setText(/*getString(R.string.txtRestos)*/"Les restaurants");
                restoList = new ArrayList<Restaurant>();
                restoList.add(new Restaurant("MAC Do","Mal bouffe", "0686720523", "info@macdo.com", "http://idata.over-blog.com/5/13/32/01/images-copie-40.jpg"));
                restoList.add(new Restaurant("MAC Crado","Mal bouffe XXXX", "xxxx", "info@macdo.com", "http://idata.over-blog.com/5/13/32/01/images-copie-40.jpg"));

                listViewData.setAdapter(new AdapterResto(ListingActivity.this, R.layout.activity_listing_item_resto, restoList));
                listViewData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i/*pos*/, long l) {
                        DetailActivity.show(ListingActivity.this, restoList.get(i));
                    }
                });
            }
        }
    }

    public static void show(Context c, boolean isHotel) {
        Intent intent = new Intent(c, ListingActivity.class);
        intent.putExtra(INTENT_IS_HOTEL, isHotel);
        c.startActivity(intent);
    }
}
