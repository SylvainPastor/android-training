package fr.rtone.musiquedb.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import fr.rtone.musiquedb.models.Music;

public class Database extends SQLiteOpenHelper {
	// database name
	public static final String DB_NAME						= "mesmusiques";
	// table name 
	public static final String TBL_MUSIQUE					= "music";
	// table column name  
	public static final String MUSIQUE_ID					= "_id";
	public static final String MUSIQUE_CATEGORIE			= "category";
	public static final String MUSIQUE_NOM					= "name";
	public static final String MUSIQUE_DESCRIPTION			= "description";
	public static final String MUSIQUE_CHANTEUR				= "singer";

	private static SQLiteDatabase db;

	//Database.getInstance(AddActivity.this).addOrUpdateMusic(musiques.getContentValues());
	private static Database mInstance = null;

	public static Database getInstance(Context context) {
		if (context == null) {
			throw new IllegalArgumentException(
					"Context is null ! Databases can't be initialized with null context");
		}

		if (mInstance == null) {
			//Log.e("database", "mInstance == null");
			mInstance = new Database(context.getApplicationContext());
			open();
		}

		return mInstance;
	}

	private Database(Context c) {
		super(c, DB_NAME, null, 1); // +1 version to upgrade database
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.e("onCreate", "Database update");
		
		db.execSQL("CREATE TABLE IF NOT EXISTS " + TBL_MUSIQUE + " (" +
                MUSIQUE_ID + " INTEGER PRIMARY KEY," +
                MUSIQUE_CATEGORIE + " VARCHAR," +
                MUSIQUE_NOM + " VARCHAR," +
                MUSIQUE_DESCRIPTION + " VARCHAR, " +
                MUSIQUE_CHANTEUR + " VARCHAR" +
                ");");
	}
	
	private static void open() {

		if (db == null) {
			db = mInstance.getWritableDatabase();
		}
	}

	public synchronized void closeConnecion() {
		if (mInstance != null) {
			mInstance.close();
			db.close();
			mInstance = null;
			db = null;
		}
	}
	
	@Override
	public synchronized void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onCreate(db);
	}

	// Ajouter méthode select
    public ArrayList<Music> getMusics() {
        if (db == null) {
            return null;
        }

        Cursor c = db.query(TBL_MUSIQUE, new String[]{
                MUSIQUE_ID,
                MUSIQUE_CATEGORIE,
                MUSIQUE_CHANTEUR,
                MUSIQUE_NOM
        }, null, null, null, null, MUSIQUE_NOM);

        if (c.getCount() == 0) {
            return null;
        }

        ArrayList<Music> musics = new ArrayList<Music>();
        while (c.moveToNext()) {
            musics.add(
                    // long id, String author, String category, String title
                    new Music(
                            c.getLong(c.getColumnIndex(MUSIQUE_ID)),
                            c.getString(c.getColumnIndex(MUSIQUE_CHANTEUR)),
                            c.getString(c.getColumnIndex(MUSIQUE_CATEGORIE)),
                            c.getString(c.getColumnIndex(MUSIQUE_NOM))
            ));
        }
        return  musics;
    }

	// Ajouter méthode insert
    public long insertMusic(Music music) {
        ContentValues content = new ContentValues();
        content.put(MUSIQUE_CATEGORIE,music.category);
        content.put(MUSIQUE_CHANTEUR,music.author);
        content.put(MUSIQUE_NOM,music.title);
        if (db != null) {
            music.id = db.insert(TBL_MUSIQUE,null, content);
            return music.id;
        }
        return -1;
    }

    public long deleteMusic(Music music) {
        if (db != null) {
            return db.delete(TBL_MUSIQUE, MUSIQUE_ID + " = " + music.id, null);
        }
        return -1;
    }
}