package com.example.curs1academy.mappers;

import static org.junit.Assert.*;

import com.example.curs1academy.NewsArticle;
import com.example.curs1academy.repository.db.Article;

import org.junit.Assert;
import org.junit.Test;

public class ArticleMappersTest {

    @Test
    public void toUiObject() {
        NewsArticle article=ArticleMappers.toUiObject(new Article("100","Stire","url"));
        NewsArticle expected=new NewsArticle("Stire",null,"url",0);

        assertEquals(expected,article);
    }

    @Test
    public void toDbObject() {
    }

    @Test
    public void toDbObjectList() {
    }

    @Test
    public void toUiObjectList() {
    }
}