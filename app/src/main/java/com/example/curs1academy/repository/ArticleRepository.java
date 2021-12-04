package com.example.curs1academy.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.curs1academy.MobileActivityApplication;
import com.example.curs1academy.NewsArticle;
import com.example.curs1academy.mappers.ArticleMappers;
import com.example.curs1academy.repository.db.Article;
import com.example.curs1academy.repository.db.ArticleDatabase;
import com.example.curs1academy.repository.db.ArticlesDAO;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ArticleRepository {

    ArticlesDAO articlesDAO;
    public LiveData<List<Article>> articlesLiveData;
    private ExecutorService executorService= Executors.newSingleThreadExecutor();

    private static ArticleRepository instance;

    public ArticleRepository(ArticleDatabase db) {
    }

    public static ArticleRepository getInstance(ArticleDatabase db){
        if(instance == null){
            instance =  new ArticleRepository(db);
        }

        return instance;
    }


    private ArticleRepository (){
        //instance=new ArticleRepository();
        ArticleDatabase database=ArticleDatabase.getDatabase(MobileActivityApplication.getAppContext());
        articlesDAO=database.articlesDAO();
        //getAll();
        articlesLiveData=articlesDAO.getAll();
    }

    public static ArticleRepository getInstance(){
        if(instance==null){
            instance=new ArticleRepository();
        }
        return instance;
    }

    public void insertAll(List<NewsArticle> newsArticles){
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                articlesDAO.insertAll(ArticleMappers.toDbObjectList(newsArticles));
            }
        });

    }

    public void updateAll(List<NewsArticle> newsArticles){
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                articlesDAO.update(ArticleMappers.toDbObjectList(newsArticles));
            }
        });
    }

    private void getAll(){
//        executorService.submit(new Runnable() {
//            @Override
//            public void run() {
//                articlesLiveData=articlesDAO.getAll();
//            }
//        });
        articlesLiveData=articlesDAO.getAll();
    }
}
