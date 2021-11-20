package com.example.curs1academy;

import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {

    public int counter;
    private MySharedPreferences preferences=new MySharedPreferences();

    public void saveName(String nume){
        preferences.saveName(nume);
    }

    public String retrieveName()
    {
        return preferences.retrieveName();
    }
}
