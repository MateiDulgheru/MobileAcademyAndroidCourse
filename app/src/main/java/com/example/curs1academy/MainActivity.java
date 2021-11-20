package com.example.curs1academy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private  final String TAG="MainActivity";
    private EditText editTextName;
    private EditText editTextPassword;

    MainActivityViewModel viewModel;

    private int counter;


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

//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.d(TAG,"OnStart");
//    }
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
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.d(TAG,"OnStop");
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.d(TAG,"OnDestroy");
//    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("counter_key", counter);
        super.onSaveInstanceState(outState);
    }
}