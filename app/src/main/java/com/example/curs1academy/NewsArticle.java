package com.example.curs1academy;

import java.util.Objects;

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

    public NewsArticle() {    }

    public NewsArticle(String name, String iconUrl, String newsUrl, long date, String id) {
        this.name = name;
        this.iconUrl = iconUrl;
        this.newsUrl = newsUrl;
        this.date = date;
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsArticle that = (NewsArticle) o;
        return name.equals(that.name) && newsUrl.equals(that.newsUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, newsUrl);
    }
}
