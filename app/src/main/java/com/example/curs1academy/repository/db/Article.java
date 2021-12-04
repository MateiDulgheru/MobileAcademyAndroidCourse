package com.example.curs1academy.repository.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "Articles")
public class Article {

    @PrimaryKey
    @NotNull
    public String id;
    @ColumnInfo(name="Title")
    public String title;
    @ColumnInfo(name = "Url")
    public String url;

    public Article(String id, String title, String url) {
        this.id = id;
        this.title = title;
        this.url = url;
    }
}
