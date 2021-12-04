package com.example.curs1academy.mappers;

import com.example.curs1academy.NewsArticle;
import com.example.curs1academy.repository.db.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleMappers {
    public static NewsArticle toUiObject(Article article) {
        return new NewsArticle(
                article.title,
                null,
                article.url,
                0
        );
    }

    public static Article toDbObject(NewsArticle newsArticle) {
        return new Article (
                newsArticle.id,
                newsArticle.name,
                newsArticle.newsUrl
        );
    }

    public static List<Article> toDbObjectList(List<NewsArticle> list) {
        ArrayList<Article> dbList = new ArrayList<>();
        for(NewsArticle article : list) {
            dbList.add(toDbObject(article));
        }
        return dbList;
    }

    public static List<NewsArticle> toUiObjectList(List<Article> list) {
        ArrayList<NewsArticle> uiList = new ArrayList<>();
        for(Article article : list) {
            uiList.add(ArticleMappers.toUiObject(article));
        }

        return uiList;
    }
}

