package fr.rtone.musiquedb.models;

/**
 * Created by sylvain on 05/11/15.
 */
public class Music {
    public String title, category, author;
    public long id;

    public Music(String title, String category, String author) {
        this.title = title;
        this.category = category;
        this.author = author;
        id = -1;
    }

    public Music(long id, String author, String category, String title) {
        this.id = id;
        this.author = author;
        this.category = category;
        this.title = title;
    }
}