package fr.rtone.temperature;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnSave;
    private EditText txtCelsius;
    private EditText txtFahrenheit;
    private ListView listTemp;
    private ArrayAdapter<String> adapter;
    private List<String> stringList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCelsius = (EditText)findViewById(R.id.editTextCelsius);
        txtFahrenheit = (EditText)findViewById(R.id.editTextFahrenheit);

        listTemp = (ListView)findViewById(R.id.ListViewTemp);
        stringList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,stringList);
        listTemp.setAdapter(adapter);

        /*
        listTemp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // i is the position
                stringList.remove(i);
                adapter.notifyDataSetChanged();
            }
        });
        */

        listTemp.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                stringList.remove(i);
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        txtCelsius.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (txtCelsius.hasFocus()) {
                    if (isNumeric(editable.toString())) {
                        double var = Double.valueOf(editable.toString());
                        txtFahrenheit.setText(celsiusFromFahrenheit(var));
                    }
                }
            }
        });

        txtFahrenheit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (txtFahrenheit.hasFocus()) {
                    if (isNumeric(editable.toString())) {
                        double var = Double.valueOf(editable.toString());
                        txtCelsius.setText(fahrenheitFromCelcius(var));
                    }
                }
            }
        });

        /***** Clavier gestion OK ***/
        txtFahrenheit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i== EditorInfo.IME_ACTION_DONE){
                    btnSave.performClick();
                }
                return false;
            }
        });

        btnSave = (Button)findViewById(R.id.ButtonSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save(view);
            }
        });

    }

    private Boolean isNumeric(String value) {
        return value.matches("[-+]?\\d*\\.?\\d+");
    }

    /************************* Methodes de convertion  *********************************/
    // °F = (9/5)*(°C + 32)
    private String fahrenheitFromCelcius(double celsius) {
        return Double.valueOf(((9.0/5.0)*celsius + 32.0)).toString();
    }

    // °C = (5/9)*(°F - 32)
    private String celsiusFromFahrenheit(double fahrenheit) {
        return Double.valueOf((5.0/9.0)*(fahrenheit-32.0)).toString();
    }

    /************************* Ajout dans la ListView  *********************************/
    private void save(View v) {
        stringList.add(txtCelsius.getText().toString()+ "°C est égal à " +
                txtFahrenheit.getText().toString() + "°F");
        adapter.notifyDataSetChanged();
    }

    /************************* Gestion du menu  *********************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_clear:
                stringList.clear();
                adapter.notifyDataSetChanged();
                txtCelsius.setText("");
                txtFahrenheit.setText("");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
