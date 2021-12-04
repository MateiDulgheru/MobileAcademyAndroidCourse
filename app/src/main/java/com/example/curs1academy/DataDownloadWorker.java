package com.example.curs1academy;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.curs1academy.repository.ArticleRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

public class DataDownloadWorker extends Worker {

    private HackerNewsAPI hackerNewsAPI=new HackerNewsAPI();

    public DataDownloadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Response response=hackerNewsAPI.executeGetRequestSync(HackerNewsAPI.TOP_STORIES_ENDPOINT);

        Gson gson=new Gson();
        try {
            String stringResponse=response.body().string();
            List<String> list=gson.fromJson(stringResponse,new TypeToken<List<String>>() {}.getType());
            List<NewsArticle> newsArticleList=getArticles(list,10);
            //EventBus.getDefault().post(new NewArticlesEvent(newsArticleList));
            ArticleRepository.getInstance().updateAll(newsArticleList);
        } catch (IOException e) {
            return Result.retry();
        }
        return Result.success();
    }

    public List<NewsArticle> getArticles(List<String> stringList, int limit){

        ArrayList<NewsArticle> newsArticles=new ArrayList<>();
        for(int i=0; i<limit; i++){
            JSONObject jsonObject = hackerNewsAPI.getArticleById(stringList.get(i));
            String id=jsonObject.optString("id");
            String title=jsonObject.optString("title");
            String url=jsonObject.optString("url");
            //NewsArticle newsArticle=new NewsArticle();
            NewsArticle newsArticle=new NewsArticle(title, null, url, 0);
            newsArticle.id=id;

            newsArticles.add(newsArticle);

        }
        return newsArticles;
    }
}
