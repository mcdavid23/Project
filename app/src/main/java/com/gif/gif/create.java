package com.gif.gif;

/**
 * Created by eidal-rabadi on 10/9/17.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.gif.gif.R;

public class create extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void recordScreenClick(View v){
        if(v.getId() == R.id.recordScreenButton)
        {
            finish();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
        }
    }






}
