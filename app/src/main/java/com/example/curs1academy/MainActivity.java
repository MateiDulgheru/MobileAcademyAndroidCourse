package com.example.curs1academy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String ACTION_TIME_IS_UP="Time's up";
    private  final String TAG="MainActivity";
    private EditText editTextName;
    private EditText editTextPassword;
    private BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction()==ACTION_TIME_IS_UP){
                Toast.makeText(MainActivity.this,"Timpu s-a terminat",Toast.LENGTH_LONG).show();
            }
        }
    };



    MainActivityViewModel viewModel;

    private int counter;

    private HackerNewsAPI newsAPI=new HackerNewsAPI();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel= ViewModelProviders.of(this).get(MainActivityViewModel.class);

        setUpView();

//        if(savedInstanceState!=null){
//            counter=savedInstanceState.getInt("counter_key");
//        }

        Log.d(TAG,"OnCreate");

        Intent serviceIntent=new Intent(this,CounterService.class);
        serviceIntent.setAction(CounterService.ACTION_COUNT);
        startService(serviceIntent);

        //getTopStories();

        new RequestAsyncTask(this).execute();

    }

    private void setUpView(){

        Button button;
        button=findViewById(R.id.btn_Submit);
        editTextName=findViewById(R.id.edit_Name);
        editTextPassword=findViewById(R.id.edit_Password);
        button.setOnClickListener(this);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        if(viewModel.retrieveName()!=null)
        {
            editTextName.setText(viewModel.retrieveName());
        }

    }

    private void handleMainButtonOnClick(){
        String name=editTextName.getText().toString(), password=editTextPassword.getText().toString();
        Log.d(TAG,name+" "+password);

        //Toast.makeText(this, name+" "+password, Toast.LENGTH_LONG).show();

        Intent intent=new Intent(this,SecondActivity.class);
        //startActivity(new Intent(MainActivity.this, SecondActivity.class));
        //intent.putExtra(SecondActivity.NAME_KEY,name);
        intent.putExtra(SecondActivity.NAME_KEY, new UserData(name,password));
        //startActivity(intent);
        startActivityForResult(intent,SecondActivity.REQUEST_CODE);

        viewModel.saveName(name);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SecondActivity.REQUEST_CODE && resultCode==SecondActivity.RESULT_CODE){
            Toast.makeText(this, "Result received successfully", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_Submit) {
            //openURI();
            handleMainButtonOnClick();
            //counter++;
            viewModel.counter++;
            //Toast.makeText(this, counter+"", Toast.LENGTH_LONG).show();
            Toast.makeText(this, viewModel.counter+"", Toast.LENGTH_LONG).show();


        }
    }

    public void openURI(){
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.youtube.com/"));
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"OnStart");
        IntentFilter filter=new IntentFilter(ACTION_TIME_IS_UP);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.d(TAG,"OnResume");
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.d(TAG,"OnPause");
//    }
//
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"OnStop");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.d(TAG,"OnDestroy");
//    }

    public void getTopStories(){
        newsAPI.getRequestAsync(HackerNewsAPI.TOP_STORIES_ENDPOINT, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "Failure on request",e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Toast.makeText(MainActivity.this,response.body().string(),Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("counter_key", counter);
        super.onSaveInstanceState(outState);
    }

    public void refreshContent(List<String> strings){
        Toast.makeText(MainActivity.this,strings.toString(),Toast.LENGTH_LONG).show();
    }
}