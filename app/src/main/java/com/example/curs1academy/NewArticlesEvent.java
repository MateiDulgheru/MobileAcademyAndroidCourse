package com.example.curs1academy;

import java.util.ArrayList;
import java.util.List;

public class NewArticlesEvent {

    List<NewsArticle> newsArticleList;

    public NewArticlesEvent(List<NewsArticle> newsArticles){
        this.newsArticleList=newsArticles;
    }
}
