package org.hse.baseproject;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    private final static String PREFERENCE_FILE = "org.hse.android.file";

    private final SharedPreferences sharedPreferences;

    public PreferenceManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);
    }

    public void saveValue(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getValue(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }
}
