package fr.rtone.musiquedb.ui;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import fr.rtone.musiquedb.R;
import fr.rtone.musiquedb.database.Database;
import fr.rtone.musiquedb.models.Music;

public class NewMusicActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_music);

        findViewById(R.id.ButtonSave).setOnClickListener(this);
    }

    private EditText getEditTextMusicName(){
        return (EditText) findViewById(R.id.editTextMusicName);
    }

    private EditText getEditTextMusicAuthor(){
        return (EditText) findViewById(R.id.editTextMusicAuthor);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ButtonSave:
                Database.getInstance(NewMusicActivity.this).insertMusic(
                        // String title, String category, String author
                        new Music(
                                getEditTextMusicName().getText().toString(),
                                "category",
                                getEditTextMusicAuthor().getText().toString()
                        )
                );
                Database.getInstance(NewMusicActivity.this).closeConnecion();
                finish();
                break;
        }
    }
}

