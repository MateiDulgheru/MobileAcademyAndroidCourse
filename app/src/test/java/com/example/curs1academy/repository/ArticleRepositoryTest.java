package com.example.curs1academy.repository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import com.example.curs1academy.mappers.ArticleMappers;
import com.example.curs1academy.repository.db.Article;
import com.example.curs1academy.repository.db.ArticleDatabase;
import com.example.curs1academy.repository.db.ArticlesDAO;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

class ArticlesRepositoryTest {

    ArticleRepository repo;
    ArticleDatabase db;
    ArticlesDAO dao;

    @Before
    public void setup() {
        db = mock(ArticleDatabase.class);
        dao = mock(ArticlesDAO.class);

        when(db.articlesDAO()).thenReturn(dao);
        repo = ArticleRepository.getInstance(db);
    }

    @Test
    public void insertAll() {
        List<Article> list = Arrays.asList(new Article("id", "title", "url"));
        repo.insertAll(ArticleMappers.toUiObjectList(list));
        verify(dao).update(list);
    }
}