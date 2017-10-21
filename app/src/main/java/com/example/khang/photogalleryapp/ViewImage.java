package com.example.khang.photogalleryapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.media.ExifInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class ViewImage extends AppCompatActivity {
    ImageView clickedImg;
    TextView Exif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        Intent i = getIntent();
        //File f = (File) i.getExtras().getParcelable("img");
        String f = getIntent().getStringExtra("img");
        //System.out.println("This is the absolute path: " + f);
        clickedImg = (ImageView) findViewById(R.id.imgClicked);
        //clickedImg.setImageURI(Uri.parse(f));

        //We now use Exif to get the GPS, and Date info for the picture
        Exif = (TextView) findViewById(R.id.txtExif);
        Bitmap bm = BitmapFactory.decodeFile(f);
        clickedImg.setImageBitmap(bm);
        Exif.setText(ReadExif(f));

        //imageFile is the absolute path to the image in String format
        /*What we want is to create a session variable (Through intents) that sends the chosen image directory over
           we grab that image path and pass that in as the String argument. */
    }

    String ReadExif(String imgFile){
        String exif = "Absolute Path: " + imgFile;
        try{
            ExifInterface exifInterface = new ExifInterface(imgFile);
            exif += "\nDate Taken: " + exifInterface.getAttribute(ExifInterface.TAG_DATETIME_DIGITIZED);
            exif += "\nDate Taken: " + exifInterface.getAttribute(ExifInterface.TAG_DATETIME_ORIGINAL);
            exif += "\nPhoto Description: " + exifInterface.getAttribute(ExifInterface.TAG_IMAGE_DESCRIPTION);
            exif += "\nGPS Coordinates";
            exif += "\nGPS Tag Date Stamp: " + exifInterface.getAttribute(ExifInterface.TAG_GPS_DATESTAMP);
            exif += "\nGPS Tag Time Stamp: " + exifInterface.getAttribute(ExifInterface.TAG_GPS_TIMESTAMP);
            exif += "\nGPS Tag Latitude: " + exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
            exif += "\nGPS Tag Longitude: " + exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);

            Toast.makeText(ViewImage.this, "Finished", Toast.LENGTH_LONG).show();
        }
        catch (IOException e){
            e.printStackTrace();
            Toast.makeText(ViewImage.this, e.toString(), Toast.LENGTH_LONG).show();
        }
        return exif;
    }



}
