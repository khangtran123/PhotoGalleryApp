package com.example.khang.photogalleryapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class PhotoUpload extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_upload);

        Button btnLib = (Button) findViewById(R.id.btnLibrary);
        Button btnTake = (Button) findViewById(R.id.btnTakeP);
        Button btnCont = (Button) findViewById(R.id.btnContinue);

        btnCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PhotoUpload.this, PhotoEdit.class));
            }
        });

        btnLib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PhotoUpload.this, Gallery.class));
            }
        });

        btnTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(PhotoUpload.this, FunctionNotCreated.class))
                setupCamera();
            }
        });

    }

    private void setupCamera() {
        Intent startCam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startCam.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath(), "/Camera/Sample" + ".jpg")));
        startActivityForResult(startCam, 1001);
    }

    //this function gets called once a snapshot takes place
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            LocationManager addGeo = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location getLocation = (Location) addGeo.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            double longitude = getLocation.getLongitude();
            double lattitude = getLocation.getLatitude();
            File currentPic = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath(), "/Camera" + ".jpg");
            //now we call on the addGeotag function
            addGeotag(currentPic.getAbsolutePath(),lattitude,longitude);
        }
    }

    //this public function takes in the longitude and latitude arguements and saves them into the picture
    //exif metadata
    public void addGeotag(String filename, double latitude, double longitude){
        try{
            ExifInterface exifInterface = new ExifInterface(filename);

            //the next 2 sections basically formulates the numbers that makes up lat and long
            int num1Lat = (int) Math.floor(latitude);
            int num2Lat = (int) Math.floor((latitude - num1Lat) * 60);
            double num3lat = (latitude - ((double) num1Lat + ((double) num2Lat / 60))) * 3600000;

            int num1Long = (int) Math.floor(longitude);
            int num2Long = (int) Math.floor((longitude - num1Lat) * 60);
            double num3long = (longitude - ((double) num1Long + ((double) num2Long / 60))) * 3600000;

            //now we set the current latitude and longitude to current photo
            exifInterface.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, num1Long + "/1," + num2Long + "/1," + num3long + "/1000");
            exifInterface.setAttribute(ExifInterface.TAG_GPS_LATITUDE, num1Lat + "/1," + num2Lat + "/1," + num3lat + "/1000");

            if (longitude > 0){
                exifInterface.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, "N");
            } else{
                exifInterface.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, "S");
            }

            if (latitude > 0){
                exifInterface.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, "E");
            } else{
                exifInterface.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, "W");
            }

            //now we save all the attributed settings: Longitude + Longitude Ref && Latitude + Latitude Ref
            exifInterface.saveAttributes();

        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

}
