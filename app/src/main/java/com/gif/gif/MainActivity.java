package com.gif.gif;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.view.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

ArrayList<Uri> imageList = new ArrayList<Uri>();

    private ShakeSensor mShakeSensor;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ShakeSensor initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeSensor = new ShakeSensor(new ShakeSensor.OnShakeListener() {
            @Override
            public void onShake() {
                // SHARE
                finish();
            }
        });

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
        mSensorManager.registerListener(mShakeSensor, mAccelerometer, SensorManager.SENSOR_DELAY_UI);

        Log.v("gif", "onResume triggered");
    }

    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(mShakeSensor);
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
