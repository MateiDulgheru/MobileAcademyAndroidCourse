package com.example.curs1academy;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.connection.RealCall;

public class HackerNewsAPI {

    private static final String TAG = HackerNewsAPI.class.getSimpleName();
    private static final String BASE_ENDPOINT = "https://hacker-news.firebaseio.com/v0/";
    public static final String TOP_STORIES_ENDPOINT = BASE_ENDPOINT + "topstories.json";
    public static final String NEW_STORIES_ENDPOINT = BASE_ENDPOINT + "newstories.json";
    public static final String ITEM_ENDPOINT = BASE_ENDPOINT + "item/";

    public Response executeGetRequestSync(String url){

        OkHttpClient httpClient=new OkHttpClient();
        Request request=new Request.Builder().url(url).build();
        Call call=httpClient.newCall(request);
        try {
            return call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getRequestAsync(String url, Callback callback){

        OkHttpClient httpClient=new OkHttpClient();
        Request request=new Request.Builder().url(url).build();
        Call call=httpClient.newCall(request);
        call.enqueue(callback);
    }

    public JSONObject getArticleById(String id){
        String url=ITEM_ENDPOINT + id + ".json";
        Response response=executeGetRequestSync(url);
        try {
            JSONObject jsonObject=new JSONObject(response.body().string());
            return jsonObject;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
