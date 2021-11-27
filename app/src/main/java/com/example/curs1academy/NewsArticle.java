package com.example.curs1academy;

public class NewsArticle {

    public String name;
    public String iconUrl;
    public String newsUrl;
    public long date;
    public String id;

    public NewsArticle(String name, String iconUrl, String newsUrl, long date) {
        this.name = name;
        this.iconUrl = iconUrl;
        this.newsUrl = newsUrl;
        this.date = date;
    }

    public NewsArticle() {

    }

    public NewsArticle(String name, String iconUrl, String newsUrl, long date, String id) {
        this.name = name;
        this.iconUrl = iconUrl;
        this.newsUrl = newsUrl;
        this.date = date;
        this.id = id;
    }
}
