package com.example.curs1academy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    public static final String NAME_KEY = "NUME";
    public static final int REQUEST_CODE=100;
    public static final int RESULT_CODE=200;

    private Button button;
    private RecyclerView listView;

    ArrayList<NewsArticle> list=new ArrayList<>();
    NewsRecyclerViewAdapter adapter=new NewsRecyclerViewAdapter(list);


    
    public static final String TAG=SecondActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //String nume=getIntent().getStringExtra(NAME_KEY);
        //UserData userData= (UserData) getIntent().getSerializableExtra(NAME_KEY);
        UserData userData = getIntent().getParcelableExtra(NAME_KEY);
        Toast.makeText(this, userData.nume, Toast.LENGTH_LONG).show();

        setUpViews();

        setUpToolbar();

        setUpDRawer();

        Intent intent=new Intent(this, FetchNetworkDataIntentService.class);
        startService(intent);

    }

    private void setUpViews() {
        button=findViewById(R.id.btnSecondAct);
        listView=findViewById(R.id.lvSecondActivity);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setResult(RESULT_CODE);
//                finish();
//            }
//      });
        //setUpList();
        setUpArticleList();
        button.setOnClickListener(v -> {
            setResult(RESULT_CODE);
            finish();
        });

    }

    private List<String> getDummyData(){

        List<String> list=new ArrayList<>();
        list.add("Bucuresti");
        list.add("Bacau");
        list.add("Vaslui");
        list.add("Iasi");
        list.add("Ramnicu-Valcea");
        list.add("Campina");
        list.add("Comarnic");

        return list;
    }

    private void setUpList(){
        ArrayList<String> listaOrase=new ArrayList<>();
        //List<String> listaOrase=getDummyData();
        listaOrase.addAll(getDummyData());
        ListView listview=findViewById(R.id.lvSecondActivity);
        ArrayAdapter adapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,listaOrase);
        listview.setAdapter(adapter);
        listaOrase.add("Chisinau");
        adapter.notifyDataSetChanged();
    }

    private List<NewsArticle> getArticleData(){
        List<NewsArticle> list=new ArrayList<>();

        list.add(new NewsArticle("nume1",null,"https://www.google.com",0));
        list.add(new NewsArticle("nume2",null,"url2",0));
        list.add(new NewsArticle("nume3",null,"url3",0));
        list.add(new NewsArticle("nume4",null,"url4",0));

        return list;
    }

    private void setUpArticleList(){
        //List<NewsArticle> list=getArticleData();

        list.addAll(getArticleData());

        listView.setAdapter(adapter);

        listView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnItemClickListener(new NewsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(NewsArticle newsArticle) {
                Log.d(TAG,"Article clicked "+newsArticle.name);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout,ArticleFragment.initInstance(newsArticle.newsUrl),"")
                        .addToBackStack(null).commit();

//                list.add(0,new NewsArticle("Articol nou","","URL",0));
//                adapter.notifyItemInserted(0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.second_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_info){
            Toast.makeText(this,"Merge optiunea",Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);

    }

    public void setUpToolbar(){
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void setUpDRawer(){
        DrawerLayout drawerLayout=findViewById(R.id.dl_activity_second);
        ActionBarDrawerToggle actionBarDrawerToggle=new ActionBarDrawerToggle(this,
                drawerLayout,R.string.app_name, R.string.app_name);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    protected void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void processArticle(NewArticlesEvent newArticlesEvent){
        list.clear();
        list.addAll(newArticlesEvent.newsArticleList);
        adapter.notifyDataSetChanged();
    }
}