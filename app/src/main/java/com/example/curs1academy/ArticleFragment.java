package com.example.curs1academy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ArticleFragment extends Fragment {

    public static final String URL_KEY="cheie_url";

    public static ArticleFragment initInstance(String url){

        ArticleFragment articleFragment=new ArticleFragment();
        Bundle bundle=new Bundle();
        bundle.putString(URL_KEY,url);

        articleFragment.setArguments(bundle);
        return articleFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.article_fragment,container,false);
        loadURL(view);
        return view;
    }

    public void loadURL(View view){
        String url=getArguments().getString(URL_KEY);
        WebView webView=view.findViewById(R.id.WebView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }
}
