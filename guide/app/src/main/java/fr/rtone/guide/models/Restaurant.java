package fr.rtone.guide.models;

import java.io.Serializable;

/**
 * Created by sylvain on 03/11/15.
 */
public class Restaurant implements Serializable{
    public String name, category, phone, url, image;

    public Restaurant(String name, String category, String phone, String url, String image) {
        this.name = name;
        this.category = category;
        this.phone = phone;
        this.url = url;
        this.image = image;
    }
}
