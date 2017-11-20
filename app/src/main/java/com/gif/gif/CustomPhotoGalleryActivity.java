package com.gif.gif;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import static com.gif.gif.MainActivity.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE;

public class CustomPhotoGalleryActivity extends Activity {

    private GridView grdImages;
    private Button btnSelect;
    private ImageAdapter imageAdapter;
    private String[] arrPath;
    private boolean[] thumbnailsselection;
    private int ids[];
    private int count;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;



    /**
     * Overrides methods
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_photo_gallery);
        grdImages = (GridView) findViewById(R.id.grdImages);
        btnSelect = (Button) findViewById(R.id.btnSelect);

        final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
        final String orderBy = MediaStore.Images.Media._ID;

        if (checkPermissionREAD_EXTERNAL_STORAGE(this)) {
            // do your stuff..

            Cursor imagecursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);
            int image_column_index = imagecursor.getColumnIndex(MediaStore.Images.Media._ID);
            this.count = imagecursor.getCount();
            this.arrPath = new String[this.count];
            ids = new int[count];
            this.thumbnailsselection = new boolean[this.count];
            for (int i = 0; i < this.count; i++) {
                imagecursor.moveToPosition(i);
                ids[i] = imagecursor.getInt(image_column_index);
                int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
                arrPath[i] = imagecursor.getString(dataColumnIndex);
            }

            imageAdapter = new ImageAdapter();
            grdImages.setAdapter(imageAdapter);
            imagecursor.close();


            btnSelect.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    final int len = thumbnailsselection.length;
                    int cnt = 0;
                    String selectImages = "";
                    for (int i = 0; i < len; i++) {
                        if (thumbnailsselection[i]) {
                            cnt++;
                            selectImages = selectImages + arrPath[i] + "|";
                        }
                    }
                    if (cnt == 0) {
                        Toast.makeText(getApplicationContext(), "Please select at least one image", Toast.LENGTH_LONG).show();
                    } else {

                        Log.d("SelectedImages", selectImages);
                        Intent i = new Intent();
                        i.putExtra("data", selectImages);
                        setResult(Activity.RESULT_OK, i);
                        finish();
                    }
                }
            });
        }
    }
    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();

    }

    /**
     * Class method
     */

    /**
     * This method used to set bitmap.
     * @param iv represented ImageView
     * @param id represented id
     */

    private void setBitmap(final ImageView iv, final int id) {

        new AsyncTask<Void, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(Void... params) {
                return MediaStore.Images.Thumbnails.getThumbnail(getApplicationContext().getContentResolver(), id, MediaStore.Images.Thumbnails.MICRO_KIND, null);
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                super.onPostExecute(result);
                iv.setImageBitmap(result);
            }
        }.execute();
    }


    /**
     * List adapter
     * @author tasol
     */

    public class ImageAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public ImageAdapter() {
            mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return count;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.custom_gallery_item, null);
                holder.imgThumb = (ImageView) convertView.findViewById(R.id.imgThumb);
                holder.chkImage = (CheckBox) convertView.findViewById(R.id.chkImage);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.chkImage.setId(position);
            holder.imgThumb.setId(position);
            holder.chkImage.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    int id = cb.getId();
                    if (thumbnailsselection[id]) {
                        cb.setChecked(false);
                        thumbnailsselection[id] = false;
                    } else {
                        cb.setChecked(true);
                        thumbnailsselection[id] = true;
                    }
                }
            });
            holder.imgThumb.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    int id = holder.chkImage.getId();
                    if (thumbnailsselection[id]) {
                        holder.chkImage.setChecked(false);
                        thumbnailsselection[id] = false;
                    } else {
                        holder.chkImage.setChecked(true);
                        thumbnailsselection[id] = true;
                    }
                }
            });
            try {
                setBitmap(holder.imgThumb, ids[position]);
            } catch (Throwable e) {
            }
            holder.chkImage.setChecked(thumbnailsselection[position]);
            holder.id = position;
            return convertView;
        }
    }

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }

    public void showDialog(final String msg, final Context context,
                           final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[] { permission },
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }


    /**
     * Inner class
     * @author tasol
     */
    class ViewHolder {
        ImageView imgThumb;
        CheckBox chkImage;
        int id;
    }

}
