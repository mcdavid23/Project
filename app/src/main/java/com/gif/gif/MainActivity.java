package com.gif.gif;
import android.net.Uri;
import android.view.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

ArrayList<Uri> imageList = new ArrayList<Uri>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v("gif", "onCreate triggered");
    }


    public void openGallery(View v){
        if (v.getId() == R.id.GALLERY){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"Select Picture"), 1);

    }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            Uri selectedMediaUri = data.getData();
            if (selectedMediaUri.toString().contains("image")) {
                //handle image
            } else if (selectedMediaUri.toString().contains("video")) {
                //handle video
            }

            imageList.add(selectedMediaUri);
            Log.v("Array list Size:", Integer.toString(imageList.size()));
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
