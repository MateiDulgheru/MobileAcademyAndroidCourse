package com.example.curs1academy.repository.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities={Article.class},version = 1)
public abstract class ArticleDatabase extends RoomDatabase {

    public abstract ArticlesDAO articlesDAO();

    private static ArticleDatabase instance;

    public static ArticleDatabase getDatabase(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context, ArticleDatabase.class, "Articole").build();
        }
        return instance;
    }
}
