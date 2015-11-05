package fr.rtone.meteo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by sylvain on 05/11/15.
 */
public class Preference {

    private static final String PREF_CITY = "PREF_CITY";

    private static SharedPreferences get(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c);
    }

    public static String getCity(Context c) {
        return get(c).getString(PREF_CITY, "");
    }

    public static void setCity(Context c, String city) {
        get(c).edit().putString(PREF_CITY, city).commit();
    }
}
