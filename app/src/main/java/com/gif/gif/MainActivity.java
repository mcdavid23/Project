package com.gif.gif;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener{


    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    ArrayList<Bitmap> list = new ArrayList<Bitmap>();
    private Button btnShare;
    private Intent shareIntent;
    String shareBody = "Check out this APP!";
    private LinearLayout lnrImages;
    private Button btnAddPhots;
    private Button btnSaveImages;
    private ArrayList<String> imagesPathList;
    private Bitmap yourbitmap;
    private Bitmap resized;
    private final int PICK_IMAGE_MULTIPLE =1;
    private ShakeSensor mShakeSensor;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private FileOutputStream outStream = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnShare = (Button)findViewById(R.id.shareButton);
        btnShare.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,"My App");
                shareIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
                startActivity(Intent.createChooser(shareIntent,"Share via "));






            }
        });

        lnrImages = (LinearLayout)findViewById(R.id.lnrImages);
        btnAddPhots = (Button)findViewById(R.id.btnAddPhots);
        btnSaveImages = (Button)findViewById(R.id.btnSaveImages);
        btnAddPhots.setOnClickListener(this);
        btnSaveImages.setOnClickListener(this);

        // ShakeSensor initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeSensor = new ShakeSensor(new ShakeSensor.OnShakeListener() {
            @Override
            public void onShake() {

                finish();
            }
        });
            Log.v("gif", "onCreate triggered");
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAddPhots:
                Intent intent = new Intent(MainActivity.this,CustomPhotoGalleryActivity.class);
                startActivityForResult(intent,PICK_IMAGE_MULTIPLE);
                break;
            case R.id.btnSaveImages:
                if(imagesPathList !=null){
                    if(imagesPathList.size()>1) {
                        Toast.makeText(MainActivity.this, imagesPathList.size() + " no of images are selected", Toast.LENGTH_SHORT).show();
                        File sdCard = Environment.getExternalStorageDirectory();
                        File dir = new File (sdCard.getAbsolutePath() + "/dir1/dir2");
                        dir.mkdirs();
                        File file = new File(dir, "GIFName_" + System.currentTimeMillis() +".gif");

                        try{
                            FileOutputStream f = new FileOutputStream(file);
                            f.write(generateGIF(list));

                        }catch(Exception e){
                            e.printStackTrace();
                        }


                    }else{
                        Toast.makeText(MainActivity.this, imagesPathList.size() + " no of image are selected", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this," no images are selected", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if(requestCode == PICK_IMAGE_MULTIPLE){
                imagesPathList = new ArrayList<String>();
                String[] imagesPath = data.getStringExtra("data").split("\\|");
                try{
                    lnrImages.removeAllViews();
                }catch (Throwable e){
                    e.printStackTrace();
                }
                for (int i=0;i<imagesPath.length;i++){
                    imagesPathList.add(imagesPath[i]);
                    yourbitmap = BitmapFactory.decodeFile(imagesPath[i]);
                    ImageView imageView = new ImageView(this);
                    imageView.setImageBitmap(yourbitmap);
                    imageView.setAdjustViewBounds(true);
                    lnrImages.addView(imageView);
                    list.add(yourbitmap);

                }

                Log.d("BitMap Length",Integer.toString(list.size()));
            }
        }

    }
    //generate Gif from a array of Bitmaps
    public static byte[] generateGIF(ArrayList<Bitmap> bitmaps) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        AnimatedGifEncoder encoder = new AnimatedGifEncoder();
        encoder.start(bos);
        encoder.setRepeat(0);
        encoder.setFrameRate(10);
        for (Bitmap bitmap : bitmaps) {
            encoder.addFrame(bitmap);
        }
        encoder.finish();
        return bos.toByteArray();
    }



    @Override
    protected void onStart() {
        super.onStart();
        Log.v("gif", "onStart triggered");
    }

    @Override
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(mShakeSensor, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
        Log.v("gif", "onResume triggered");
    }

    @Override
    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(mShakeSensor);
        Log.v("gif", "onPause triggered");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v("gif", "onStop triggered");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.v("gif", "onDestory triggered");
    }

}

