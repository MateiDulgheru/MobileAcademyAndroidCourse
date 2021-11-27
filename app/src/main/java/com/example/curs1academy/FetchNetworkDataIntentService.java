package com.example.curs1academy;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;


public class FetchNetworkDataIntentService extends IntentService {

    private HackerNewsAPI hackerNewsAPI=new HackerNewsAPI();



    public FetchNetworkDataIntentService(String name){
        super(name);
    }

    public FetchNetworkDataIntentService() {
        super("FetchNetworkDataIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("FetchNetworkDataService",Thread.currentThread().getName());
        HackerNewsAPI hackerNewsAPI=new HackerNewsAPI();
        Response response=hackerNewsAPI.executeGetRequestSync(HackerNewsAPI.TOP_STORIES_ENDPOINT);

        Gson gson=new Gson();
        try {
            String stringResponse=response.body().string();
            List<String> list=gson.fromJson(stringResponse,new TypeToken<List<String>>() {}.getType());
            List<NewsArticle> newsArticleList=getArticles(list,10);
            EventBus.getDefault().post(new NewArticlesEvent(newsArticleList));
        } catch (IOException e) {
            e.printStackTrace();
        }
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