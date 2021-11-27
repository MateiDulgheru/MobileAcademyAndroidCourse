package com.example.curs1academy;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import okhttp3.Response;

class RequestAsyncTask extends AsyncTask<Void, Void, List<String>> {

    HackerNewsAPI newsAPI=new HackerNewsAPI();
    WeakReference<MainActivity> mainActivityWeakReference;

    public RequestAsyncTask(MainActivity mainActivity){

        mainActivityWeakReference=new WeakReference<>(mainActivity);

    }



    @Override
    protected List<String> doInBackground(Void... voids) {
        Response response=newsAPI.executeGetRequestSync(HackerNewsAPI.TOP_STORIES_ENDPOINT);
        Gson gson=new Gson();
        try {
            String stringResponse=response.body().string();
            List<String> list=gson.fromJson(stringResponse,new TypeToken<List<String>>() {}.getType());
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<String> strings) {
        super.onPostExecute(strings);
        //Toast.makeText(MainActivity.this,strings.toString(),Toast.LENGTH_LONG).show();
        MainActivity mainActivity=mainActivityWeakReference.get();
        if(mainActivity!=null){
            mainActivity.refreshContent(strings);
        }
    }
}
