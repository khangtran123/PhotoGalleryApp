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
    private boolean valid = false;
    private Float Longitude, Latitude;

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
            if (exifInterface != null){
                exif += "\nDate Taken: " + exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
                exif += "\nPhoto Description: " + exifInterface.getAttribute(ExifInterface.TAG_IMAGE_DESCRIPTION);
                exif += "\n";
                exif += "\nGPS Coordinates";
                exif += "\nGPS Tag Date Stamp: " + exifInterface.getAttribute(ExifInterface.TAG_GPS_DATESTAMP);
                exif += "\nGPS Tag Time Stamp: " + exifInterface.getAttribute(ExifInterface.TAG_GPS_TIMESTAMP);

                //exif += "\nGPS Tag Latitude: " + exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
                //exif += "\nGPS Tag Latitude Reference: " + exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
                String lat = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
                String latRef = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);

                //exif += "\nGPS Tag Longitude: " + exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
                //exif += "\nGPS Tag Longitude Reference: " + exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);
                String lon = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
                String longRef = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);

                if ((lat != null) && (latRef != null) && (lon != null) && (longRef != null)){
                    valid = true;

                    //if the Latitude Reference resides in the North, call on convertToDegree function
                    //else if the Latitude Reference resides in the South
                    if(latRef.equals("N")){
                        Latitude = convertToDegree(lat);
                    } else {
                        Latitude = 0 - convertToDegree(lat);
                    }

                    //if the Longitude Reference resides in the East, call on convertToDegree function
                    //else if the Longitude Reference resides in the East
                    if(longRef.equals("E")){
                        Longitude = convertToDegree(lon);
                    } else {
                        Longitude = 0 - convertToDegree(lon);
                    }

                }

                exif += "\nGPS Tag Latitude: " + String.valueOf(Latitude);
                exif += "\nGPS Tag Latitude Reference: " + latRef;
                exif += "\nGPS Tag Longitude: " + String.valueOf(Longitude);
                exif += "\nGPS Tag Longitude Reference: " + longRef;
                Toast.makeText(ViewImage.this, "Finished", Toast.LENGTH_LONG).show();
            } else{
                System.out.println("Exif is null");
            }

        }
        catch (IOException e){
            e.printStackTrace();
            Toast.makeText(ViewImage.this, e.toString(), Toast.LENGTH_LONG).show();
        }
        return exif;
    }

    private Float convertToDegree(String stringDMS){
        Float results = null;
        String[] DMS = stringDMS.split(",",3);

        String[] stringD = DMS[0].split("/",2);
        Double D0 = new Double(stringD[0]);
        Double D1 = new Double(stringD[1]);
        Double floatD = D0/D1;

        String[] stringM = DMS[1].split("/",2);
        Double M0 = new Double(stringM[0]);
        Double M1 = new Double(stringM[1]);
        Double floatM = M0/M1;

        String[] stringS = DMS[2].split("/",2);
        Double S0 = new Double(stringS[0]);
        Double S1 = new Double(stringS[1]);
        Double floatS = S0/S1;

        results = new Float(floatD + (floatM/60) + (floatS/3600));

        return results;
    }

    public boolean isValid(){
        return valid;
    }
    
}
