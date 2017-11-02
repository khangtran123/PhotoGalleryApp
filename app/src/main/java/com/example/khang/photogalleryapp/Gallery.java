package com.example.khang.photogalleryapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import android.support.v4.app.ActivityCompat;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.Manifest;
import android.support.v4.content.ContextCompat;
import android.content.pm.PackageManager;
import android.widget.Toast;

public class Gallery extends AppCompatActivity {
    TextView getDate;
    String startD, endD, lat, longi;
    GridView gv;
    ArrayList<File> list;
    private int READ_STORAGE_PERMISSION_CODE = 23;
    private int WRITE_STORAGE_PERMISSION_CODE = 24;
    private File root;
    private ArrayList<File> imgList = new ArrayList<File>();
    private ArrayList<File> newimgList = new ArrayList<File>();
    ExifInterface intf = null;
    private boolean valid = false;
    private Float Longitude, Latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Button btnMenu = (Button) findViewById(R.id.btnMainMenu);

        //storage directory: /storage/emulated/0
        //permission testing section
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            Log.d("Files", "Permission Exists");
        else
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, READ_STORAGE_PERMISSION_CODE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            Log.d("Files", "Permission Exists");
        else
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_STORAGE_PERMISSION_CODE);

        //This just shows us the date picked from advanced search
        startD = getIntent().getStringExtra("STARTDATE");
        endD = getIntent().getStringExtra("ENDDATE");
        lat = getIntent().getStringExtra("LATITUDE");
        longi = getIntent().getStringExtra("LONGITUDE");

        String pathtoimage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera";
        File imageDirectory = new File(pathtoimage);
        System.out.println(imageDirectory);
        listImgs(imageDirectory, startD, endD, lat, longi);

        //now create a new array to populate photos based on given time
        //String hello = imgList.get(position).toString();

        gv = (GridView) findViewById(R.id.gridView);
        gv.setAdapter( new GridAdapter() );
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity( new Intent(getApplicationContext(), ViewImage.class).putExtra("img",imgList.get(position).toString()));
            }
        });

        //This redirects user back to the main menu
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Gallery.this, MainMenu.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == READ_STORAGE_PERMISSION_CODE){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permission granted now you can read the storage",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,"Oops you just denied the permission",Toast.LENGTH_LONG).show();
            }
        }
        if(requestCode == WRITE_STORAGE_PERMISSION_CODE){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permission granted now you can write the storage",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,"Oops you just denied the permission",Toast.LENGTH_LONG).show();
            }
        }
    }

    //This basically binds together the image array in imageReader and adds it to the gridview
    class GridAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return imgList.size();
        }

        @Override
        //This gets the absolute path to the image file
        public Object getItem(int position) {
            /*String imgPath = imgList.get(position).toString();
            Bitmap bm = BitmapFactory.decodeFile(imgPath);
            String photoDate = null;
            System.out.println("Start Date: " + startD);
            System.out.println("End Date: " + endD);

            if(startD != null && endD != null) {
                try {
                    ExifInterface exifInterface = new ExifInterface(imgPath);
                    if (exifInterface != null) {
                        photoDate = exifInterface.getAttribute(ExifInterface.TAG_GPS_DATESTAMP);
                        /*
                        1. 2017:05:24
                        2. 2016:08:28
                        3. 2016:08:25
                        4. 2015:08:02
                        5. 2015:11:29
                        6. null
                        7. null

                        if (startD != null && endD != null) {
                            if (startD.equals(photoDate) || endD.equals(photoDate)) {
                                //add img to new imgList
                                System.out.println("You are trying to get the search dates");
                                //return imgList.get(position);
                            }
                        }

                        //This section is trying to convert the exif date into a proper date format
                        //exif Date format -> yyyy:mm:dd
                        /*String date1 = "2016:08:25";
                        String date2 = exifInterface.getAttribute(ExifInterface.TAG_GPS_DATESTAMP);

                        if(date1.equals(date2)){
                            System.out.println("The photo date matches the desired date");
                        } else{
                            System.out.println("The photo doesn't match the date.");
                        }

                    } else {
                        System.out.println("Exif is null");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(photoDate);
            //so create two returns: one array will include all the photos, other array will include
            //photos within specified date
            System.out.println("Absolute Path: " + imgList.get(position));*/
            return imgList.get(position);
        }

        public Object getItems(int position) {
            return imgList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.single_grid, parent , false);
            ImageView iv = (ImageView) convertView.findViewById(R.id.imgV);

            //iv.setImageBitmap(BitmapFactory.decodeFile(getFilesDir().getAbsolutePath() + "/Picture" + filePath));

            iv.setImageURI(Uri.parse(getItem(position).toString()));

            return convertView;
        }
    }

    //This basically verifies if external directory exists, and checks to see if the file is
    //a directory or not, if not, iterate through the folder and add each jpg file in array list
    //for us to display in gridview

    public ArrayList<File> listImgs(File dir, String start, String end, String getLat, String getLong) {

        ArrayList<File> a = new ArrayList<File>();

        File[] img = dir.listFiles(); //lists all the files that belong in the directory of path given
        for (int i = 0; i < img.length; i++) {
            //isDirectory is a boolean function that checks to see if this specific file is a directory
            //if it is a directory, iterate through all files and add to the array
            System.out.println("Photo path list!: " + img[i]);
            String path = img[i].toString();

            //this section requests the decoder to subsample the original image
            //ie cut the image quality to minimize the memory usage
            //if value is > 1
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 16;

            Bitmap bm = BitmapFactory.decodeFile(path,options);
            String photoDate = null;

            //this try block section is for Exception handling -- adds the image path to the
            //imgList file array based on the entered date
            try {
                ExifInterface exifInterface = new ExifInterface(path);
                if (exifInterface != null) {
                    photoDate = exifInterface.getAttribute(ExifInterface.TAG_GPS_DATESTAMP);

                    String lat = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
                    String latRef = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
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
                    String getLatitude = String.valueOf(Latitude);
                    String getLongitude = String.valueOf(Longitude);
                        /*
                        1. 2017:05:24 Lat --> 32.767426 Long --> -117.2377
                        2. 2016:08:28 Lat --> 49.35455  Long --> -122.62416
                        3. 2016:08:25 Lat --> 49.288254  Long --> -123.117744
                        4. 2015:08:02 Lat --> 49.448277  Long --> -122.01867
                        5. 2015:11:29 Lat --> 49.38905   Long --> -123.206635
                        6. null
                        7. null
                         */
                    if ((start != null && end != null) || (getLat != null && getLong != null)) {
                        if ((start.equals(photoDate) || end.equals(photoDate)) || (getLat.equals(getLatitude) && getLong.equals(getLongitude))) {
                            //add img to new imgList
                            imgList.add(img[i]);
                            //return imgList.get(position);
                        }
                    } else{
                        imgList.add(img[i]);
                    }

                    //This section is trying to convert the exif date into a proper date format
                    //exif Date format -> yyyy:mm:dd
                        /*String date1 = "2016:08:25";
                        String date2 = exifInterface.getAttribute(ExifInterface.TAG_GPS_DATESTAMP);

                        if(date1.equals(date2)){
                            System.out.println("The photo date matches the desired date");
                        } else{
                            System.out.println("The photo doesn't match the date.");
                        }*/

                } else {
                    System.out.println("Exif is null");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            /*
            if (img[i].isDirectory()) {
                //imgList.addAll(listImgs(img[i]));
                //a.addAll(imageReader(img[i]));
                System.out.println("This is a directory");
            } else if (img[i].getName().endsWith(".jpg") || img[i].getName().endsWith(".JPG")) {
                imgList.add(img[i]);
                //System.out.println("Photo path!: " + img[i]);
            } else {
                System.out.println("No photos");
            } */
        }
        return a;
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
