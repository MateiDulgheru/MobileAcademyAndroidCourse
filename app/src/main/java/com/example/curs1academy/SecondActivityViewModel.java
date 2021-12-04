package com.example.curs1academy;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.curs1academy.mappers.ArticleMappers;
import com.example.curs1academy.repository.ArticleRepository;
import com.example.curs1academy.repository.db.Article;

import java.util.List;

public class SecondActivityViewModel extends ViewModel {

    private ArticleRepository articleRepository=ArticleRepository.getInstance();
    public LiveData<List<NewsArticle>> liveData= Transformations.map(articleRepository.articlesLiveData,
            ArticleMappers::toUiObjectList);

}
