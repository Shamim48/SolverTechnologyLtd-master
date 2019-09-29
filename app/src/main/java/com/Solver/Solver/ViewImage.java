package com.Solver.Solver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.Solver.Solver.ModelClass.TouchImageView;
import com.bumptech.glide.Glide;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;
public class ViewImage extends AppCompatActivity {

    TouchImageView touchImageView;
    String imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image2);
        Intent intent=getIntent();
         imageUri=intent.getStringExtra("imageUri");
        touchImageView=findViewById(R.id.image);
        Glide.with(this).load(imageUri).placeholder(R.drawable.ic_image_black_24dp).into(touchImageView);
        touchImageView.setMaxZoom(6f);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

       MenuInflater inflater= getMenuInflater();
       inflater.inflate(R.menu.crop_image_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.downloadMenuId:
               // Uri url = new URL(imageUri);
               // File f  = new File(url.getPath());

               // addImageToGallery(f.getPath(), this);
               // saveImageToExternalStorage(imageUri.toString());
                Toast.makeText(getApplicationContext(),"Coming Soon.",Toast.LENGTH_SHORT).show();
                break;
                default:


        }
        return super.onOptionsItemSelected(item);
    }

    public static void addImageToGallery(final String filePath, final Context context)
    {

        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, filePath);
        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    private void saveImageToExternalStorage(Bitmap finalBitmap) {
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File myDir = new File(root + "/saved_images_1");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        // Tell the media scanner about the new file so that it is
        // immediately available to the user.
        MediaScannerConnection.scanFile(this, new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });

    }

}
