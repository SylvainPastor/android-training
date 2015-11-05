package fr.rtone.musiquedb.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import fr.rtone.musiquedb.R;
import fr.rtone.musiquedb.database.Database;
import fr.rtone.musiquedb.models.AdapterMusic;
import fr.rtone.musiquedb.models.Music;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private AdapterMusic adapter;
    private List<Music> musicList;
    private Database musicdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.ListViewMusic);
        musicList = new ArrayList<Music>();
        adapter = new AdapterMusic(
                MainActivity.this, R.layout.activity_music, musicList);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (musicdb.deleteMusic(musicList.get(i))>0) {
                    musicList.remove(i);
                    adapter.notifyDataSetChanged();
                }
                 return true;
            }
        });
    }

    private void loadMusicFromDb() {
        musicdb = Database.getInstance(MainActivity.this);
        ArrayList<Music> musics = musicdb.getMusics();
        if (musics != null) {
            musicList.clear();
            musicList.addAll(musics);
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadMusicFromDb();
    }

    @Override
    protected void onStop() {
        super.onStop();
        musicdb.closeConnecion();
    }

    /************************* Gestion du menu  *********************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                startActivity(new Intent(MainActivity.this,NewMusicActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
