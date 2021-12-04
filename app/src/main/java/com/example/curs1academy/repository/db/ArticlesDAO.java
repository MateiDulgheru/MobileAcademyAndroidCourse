package com.example.curs1academy.repository.db;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public abstract class ArticlesDAO {

    @Insert(onConflict = REPLACE)
    public abstract void insertAll(List<Article> articleList);

    @Query("SELECT * FROM Articles")
    public abstract LiveData<List<Article>> getAll();

    @Delete
    abstract void delete(Article article);

    @Query("DELETE FROM Articles")
    abstract void deleteAll();

    @Transaction
    public void update(List<Article> articles){
        deleteAll();
        insertAll(articles);
    }
}
