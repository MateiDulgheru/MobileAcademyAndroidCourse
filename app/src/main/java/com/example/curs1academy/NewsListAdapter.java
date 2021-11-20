package com.example.curs1academy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class NewsListAdapter extends ArrayAdapter<NewsArticle> {
    public NewsListAdapter(@NonNull Context context, int resource, @NonNull List<NewsArticle> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        NewsArticle newsArticle=getItem(position);
        if(convertView==null)
        {
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.news_article_list_item,parent,false);
            ViewHolder viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        ViewHolder holder=(ViewHolder) convertView.getTag();
        holder.bind(newsArticle);

        return convertView;
    }

    class ViewHolder{

        TextView articleName, articleDesc;

        private ViewHolder(View convertView){
            articleName=convertView.findViewById(R.id.Title);
            articleDesc=convertView.findViewById(R.id.Description);
        }

        private void bind(NewsArticle article){
            articleName.setText(article.name);
            articleDesc.setText(article.newsUrl);
        }
    }
}
