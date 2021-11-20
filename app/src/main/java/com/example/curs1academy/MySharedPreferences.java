package com.example.curs1academy;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {

    private final String fileName="fisierPorba";
    private SharedPreferences preferences=
            MobileActivityApplication.getAppContext().getSharedPreferences("fisierProba", Context.MODE_PRIVATE);
    private final String NAME_KEY="NUME";

    public void saveName(String nume){

        preferences.edit().putString(NAME_KEY,nume).apply();

    }

    public String retrieveName(){

        return preferences.getString(NAME_KEY,null);
    }
}
