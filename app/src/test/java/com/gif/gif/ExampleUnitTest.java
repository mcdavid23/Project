package com.gif.gif;

import android.os.Environment;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    public class ExampleInstrumentedTest {
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


        }

        @Test
        public void checkImageCountInSdCard(){
            File photoDir = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DCIM + "/";
            File[] files = dir.listFiles();
            int numberOfImages=files.length;
            assertEquals(42,numberOfImages);
        }



    }
}