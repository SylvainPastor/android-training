package fr.rtone.meteo.ui;

import android.app.Dialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;

import fr.rtone.meteo.R;
import fr.rtone.meteo.model.OpenWeatherMap;
import fr.rtone.meteo.utils.Constant;
import fr.rtone.meteo.utils.FastDialog;
import fr.rtone.meteo.utils.Network;
import fr.rtone.meteo.utils.Preference;

public class MainActivity extends AppCompatActivity {

    private TextView textCity;
    private TextView textTemperature;
    private ImageView imageViewIcon;
    EditText textViewCity;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewCity = (EditText) findViewById(R.id.textViewInputCity);
        textViewCity.setText(Preference.getCity(MainActivity.this));
        // deplace le curseur à la fin du texte
        textViewCity.setSelection(textViewCity.getText().length());

        textCity = (TextView) findViewById(R.id.textCity);
        textTemperature = (TextView) findViewById(R.id.textTemperature);
        imageViewIcon = (ImageView) findViewById(R.id.imageViewIcon);

        resetTextViews();

        // gestion du enter clavier
        textViewCity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    onClick(null);
                }
                return false;
            }
        });
    }

    private void resetTextViews() {
        textCity.setText("");
        textTemperature.setText("");
    }

    public void onClick(View view) {
        // check name
        if (textViewCity.getText().toString().isEmpty()) {
            FastDialog.showDialog(MainActivity.this,FastDialog.SIMPLE_DIALOG,"Vous devez saisiz le nom d'une ville");
            return;
        }

        // check network
        if (!Network.isNetworkAvailable(MainActivity.this)) {
            FastDialog.showDialog(MainActivity.this,FastDialog.SIMPLE_DIALOG,"Vous devez activez le reseau");
            return;
        }

        // Execute query
        //testAsyncTaskApi();

        // Test with Volley
        testVolleyApi();
    }

    private void testAsyncTaskApi() {
        new WeatherTask().execute(String.format(Constant.URL, textViewCity.getText().toString()));
    }

    private void testVolleyApi() {
        dialog = FastDialog.showProgressDialog(MainActivity.this, "Chargement...");
        dialog.show();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = String.format(Constant.URL, textViewCity.getText().toString());

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Gson gson = new Gson();
                        OpenWeatherMap result = gson.fromJson(response, OpenWeatherMap.class);
                        if (result.cod.equals("200")) {
                            Preference.setCity(MainActivity.this, result.name);
                            textCity.setText(result.name);
                            textTemperature.setText(result.main.temp + " °C");
                            Picasso.with(MainActivity.this).load(
                                    String.format(Constant.URL_IMAGE, result.weather.get(0).icon))
                                    .into(imageViewIcon);
                        }
                        else {
                            resetTextViews();
                            FastDialog.showDialog(MainActivity.this, FastDialog.SIMPLE_DIALOG, result.message);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        resetTextViews();
                        FastDialog.showDialog(MainActivity.this, FastDialog.SIMPLE_DIALOG, "Erreur pas de resultat");
                    }
                });
        queue.add(request);
    }

    class WeatherTask
            extends AsyncTask<String,Integer,OpenWeatherMap> {

        /*
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        */
        Dialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = FastDialog.showProgressDialog(MainActivity.this, "Chargement...");
            dialog.show();

            super.onPreExecute();
        }

        @Override
        protected OpenWeatherMap doInBackground(String... params) {

            try {
                HttpResponse data = Network.getData(params[0]);
                if (data.getStatusLine().getStatusCode() == HttpStatus.SC_OK /*200*/) {
                    Reader reader = new InputStreamReader(data.getEntity().getContent());
                    //read json
                    Gson gson = new Gson();
                    return gson.fromJson(reader, OpenWeatherMap.class);
                    // publishProgress(10);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(OpenWeatherMap result) {
            dialog.dismiss();
            if (result == null) {
                textCity.setText("");
                textTemperature.setText("");
                FastDialog.showDialog(MainActivity.this, FastDialog.SIMPLE_DIALOG, "Erreur pas de resultat");
            }
            else {
                if (result.cod.equals("200")) {
                    textCity.setText(result.name);
                    textTemperature.setText(result.main.temp + " °C");
                    Picasso.with(MainActivity.this).load(
                            String.format(Constant.URL_IMAGE, result.weather.get(0).icon))
                            .into(imageViewIcon);
                }
                else {
                    textCity.setText("");
                    textTemperature.setText("");
                    FastDialog.showDialog(MainActivity.this, FastDialog.SIMPLE_DIALOG, result.message);
                }
            }
            super.onPostExecute(result);
        }
    }
}
