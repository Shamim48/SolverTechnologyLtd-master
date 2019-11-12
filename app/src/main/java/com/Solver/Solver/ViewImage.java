package com.Solver.Solver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
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

import com.Solver.Solver.ModelClass.SaveImageHelper;
import com.Solver.Solver.ModelClass.TouchImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import dmax.dialog.SpotsDialog;

public class ViewImage extends AppCompatActivity  {

    private static final Object PERMISSION_CODE =1000 ;
    TouchImageView touchImageView;
    String imageUri;
    int angle = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image2);
       /* if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

          //  requestPermissions(new String[]{},PERMISSION_CODE);
        }*/



        Intent intent=getIntent();
         imageUri=intent.getStringExtra("imageUri");
        touchImageView=findViewById(R.id.image);
        Glide.with(this).load(imageUri).placeholder(R.drawable.ic_image_black_24dp).into(touchImageView);
        touchImageView.setMaxZoom(6f);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

       MenuInflater inflater= getMenuInflater();
       inflater.inflate(R.menu.download_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.downloadMenuId:
                /*final ProgressDialog progressDialog=new ProgressDialog(ViewImage.this);
                progressDialog.setTitle("Please Wait..");
                progressDialog.setMessage("Image Downloading...");
                progressDialog.show();*/

                AlertDialog dialog=new SpotsDialog(ViewImage.this);
                dialog.show();
                dialog.setMessage("Downloading...");

                String fileName= UUID.randomUUID().toString()+".jpg";
                Picasso.get().load(imageUri).into(new SaveImageHelper(getBaseContext(),dialog,
                        getApplicationContext().getContentResolver(),
                        fileName,"Description"));
                Toast.makeText(getApplicationContext(),"Coming Soon.",Toast.LENGTH_SHORT).show();

                break;

            case R.id.rotateMenuId:
                angle = angle + 90;
                touchImageView.setRotation(angle);

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


    //save image
    public static void imageDownload(Context ctx, String url){

       /* Picasso.with(ctx)
                .load("http://blog.concretesolutions.com.br/wp-content/uploads/2015/04/Android1.png")
                .into(getTarget(url));*/
       // Glide.with(ctx).load(url).into(getTarget(url));


    }


    //target to save
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
