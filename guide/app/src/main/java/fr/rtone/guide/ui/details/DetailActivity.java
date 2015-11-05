package fr.rtone.guide.ui.details;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import fr.rtone.guide.R;
import fr.rtone.guide.models.Restaurant;

public class DetailActivity extends AppCompatActivity {

    static final String INTENT_RESTO = "RESTO";
    private TextView textViewTitle;
    private TextView textViewCategory;
    private Button btnSite;
    private Button btnEmail;
    private Button btnPhone;
    private ImageView image;

    Picasso picasso;
    Picasso.Listener listener;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed(); désactive la fleche retour du clavier
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textViewTitle = (TextView)findViewById(R.id.textViewTitle);
        textViewCategory = (TextView)findViewById(R.id.textViewCategory);

        btnSite = (Button)findViewById(R.id.btnSite);
        btnEmail = (Button)findViewById(R.id.btnEmail);
        btnPhone = (Button)findViewById(R.id.btnPhone);
        image = (ImageView)findViewById(R.id.imageLogo);

        if(getIntent().getExtras() != null){
            Restaurant resto = (Restaurant) getIntent().getExtras().get(INTENT_RESTO);
            textViewTitle.setText(resto.name);
            textViewCategory.setText(resto.category);
            btnSite.setText(resto.url);
            btnPhone.setText(resto.phone);

            // Appel telephonique
            btnPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent callintent = new Intent(Intent.ACTION_DIAL);
                    callintent.setData(Uri.parse("tel:" + btnPhone.getText()));
                    startActivity(callintent);
                }
            });
            // appel du navigateur web
            btnSite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(btnSite.getText().toString())));
                }
            });

            // donner l'acces a INTERNET dans AndroidManifest
            Picasso.with(DetailActivity.this).load(resto.image).into(image);
        }
        else {
            textViewTitle.setText("");
            textViewCategory.setText("");
        }
    }

    public static void show(Context context, Restaurant resto)
    {
        Intent intent = new Intent(context,DetailActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable(INTENT_RESTO,resto);
        intent.putExtras(bundle);

        context.startActivity(intent);
    }
}
