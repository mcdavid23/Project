package com.gif.gif;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;

import org.junit.Test;
import static org.mockito.Mockito.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.*;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {


       int image1width = 200;
    int image1Height = 200;



        @Test
        public void testGenerateGIF1(){
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            AnimatedGifEncoder encoder = new AnimatedGifEncoder();
            ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
            Bitmap image1 = BitmapFactory.decodeFile("com/gif/gif/image1.bmp");
            Bitmap image2 = BitmapFactory.decodeFile("com/gif/gif/image2.bmp");
            bitmaps.add(image1);
            bitmaps.add(image2);
            encoder.start(bos);
            int count = 0;
            for (Bitmap bitmap : bitmaps) {
                encoder.addFrame(bitmap);
                count++;
            }
            encoder.finish();
            assertEquals(2,count);
        }

    @Test
    public void testGenerateGIF2(){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        AnimatedGifEncoder encoder = new AnimatedGifEncoder();
        ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
        Bitmap image1 = BitmapFactory.decodeFile("com/gif/gif/image1.bmp");
        Bitmap image2 = BitmapFactory.decodeFile("com/gif/gif/image2.bmp");
        bitmaps.add(image1);
        bitmaps.add(image2);
        assertEquals(2, bitmaps.size());
    }


    @Test
    public void testResizeBitmap(){
        Bitmap image1 = BitmapFactory.decodeFile("com/gif/gif/image1.bmp");
        int width = image1width;
        int height = image1Height;
        float scaleWidth = ((float) 1000) / width;
        float scaleHeight = ((float) 1000) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        assertEquals(true, height > scaleHeight & width > scaleWidth);
    }



    // @Mock
    // File sdCard = Environment.getExternalStorageDirectory();
/*
        @Test
        public void checkIfFileIsCreated() {

            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File (sdCard.getAbsolutePath() + "/dir1/dir2");
            File file = new File(dir, "GIFName_" + "1511202975903" +".gif");
            assertEquals(true,file.exists());

        }

        @Test
        public void checkIfDirectoryIsCreated() {
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File (sdCard.getAbsolutePath() + "/dir1/dir2");
            assertEquals(true,dir.exists());


        }*/

}