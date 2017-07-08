package com.example.ipmedt41617.ipmedt4_h;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {
    private static SharedPrefs sharePref = new SharedPrefs();
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    // Voorkomen dat meerdere instanties worden aangemaakt

    private SharedPrefs() {
    }

    // Voor het ophalen van de SharedPreferences instance

    public static SharedPrefs getInstance(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
        return sharePref;
    }

    // String waarde met waarde plaatsen in SharedPreferences

    public void putStringValue(String name, String value) {
        editor.putString(name, value);
        editor.commit();
    }

    // Bool waarde met waarde plaatsen in SharedPreferences

    public void putBooleanValue(String name, Boolean bool){
        editor.putBoolean(name, bool);
        editor.commit();
    }

    // String verkrijgen uit SharedPreferences

    public String getStringValue(String name) {
        return sharedPreferences.getString(name, "");
    }

    // Bool verkrijgen uit SharedPreferences

    public Boolean getBooleanValue(String name) {
        return sharedPreferences.getBoolean(name, true);
    }

    public void removePlaceObj(String name) {
        editor.remove(name);
        editor.commit();
    }

    // Alle opgeslagen waarden van de applicatie uit SharedPreferences verwijderen

    public void clearAll() {
        editor.clear();
        editor.commit();
    }
}