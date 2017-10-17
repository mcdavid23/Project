package com.gif.gif;
import android.view.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v("gif", "onCreate triggered");
    }


    public void onButtonClick(View v)
    {
        if(v.getId() == R.id.CREATE)
        {
            Intent i = new Intent(MainActivity.this,create.class);
            startActivity(i);


        }


    }








    protected void onStart() {
        super.onStart();
        Log.v("gif", "onStart triggered");
    }

    protected void onResume(){
        super.onResume();
        Log.v("gif", "onResume triggered");
    }

    protected void onPause(){
        super.onPause();
        Log.v("gif", "onPause triggered");
    }

    protected void onStop(){
        super.onStop();
        Log.v("gif", "onStop triggered");
    }

    protected void onDestroy(){
        super.onDestroy();
        Log.v("gif", "onDestory triggered");
    }


}
