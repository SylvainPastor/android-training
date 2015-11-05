package fr.rtone.intent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class FormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

    }

    private EditText getEditTextFirstName(){
        return (EditText) findViewById(R.id.editTextFirstName);
    }

    public void onClick(View view) {
        Intent intent = new Intent();
        intent.putExtra("firstname", getEditTextFirstName().getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}
