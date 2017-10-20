package com.example.khang.photogalleryapp;

import android.content.Intent;
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
import java.util.ArrayList;

import android.Manifest;
import android.support.v4.content.ContextCompat;
import android.content.pm.PackageManager;
import android.widget.Toast;

public class Gallery extends AppCompatActivity {
    TextView getDate;
    String startD, endD;
    GridView gv;
    ArrayList<File> list;
    private int READ_STORAGE_PERMISSION_CODE = 23;
    private int WRITE_STORAGE_PERMISSION_CODE = 24;
    private File root;
    private ArrayList<File> imgList = new ArrayList<File>();
    ExifInterface intf = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Button btnMenu = (Button) findViewById(R.id.btnMainMenu);

        //storage directory: /storage/emulated/0
        //Environment.getExternalStorageDirectory() -- Return the primary shared/external storage directory
        //Environment.getExternalStorageDirectory().getAbsolutePath() -- Gives you the full path of the SDCard
        //String filepath = Environment.getExternalStorageDirectory().toString() + "/Picture";

        //permission testing section
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            Log.d("Files", "Permission Exists");
        else
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, READ_STORAGE_PERMISSION_CODE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            Log.d("Files", "Permission Exists");
        else
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_STORAGE_PERMISSION_CODE);

        //list = imageReader(Environment.getExternalStorageDirectory());
        //File sdDir = new File(Environment.getExternalStorageDirectory())
        String pathtoimage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
        //String pathtoimage = Environment.getFilesDir();
        //File imageDirectory = new File(Environment.getExternalStorageDirectory().getPath().toString() + "/document");
        File imageDirectory = new File(pathtoimage);
        System.out.println(imageDirectory);
        //Log.d(imageDirectory.toString(),"dfdsf");
        //list = listImgs(imageDirectory);
        listImgs(imageDirectory);
        gv = (GridView) findViewById(R.id.gridView);
        gv.setAdapter( new GridAdapter() );
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity( new Intent(getApplicationContext(), ViewImage.class).putExtra("img",imgList.get(position).toString()));
            }
        });

        /*for(int i=0; i<imgList.size(); i++){
            String picDate = intf.getAttribute(ExifInterface.TAG_DATETIME);
        } */

        //This just shows us the date picked from advanced search
        startD = getIntent().getStringExtra("STARTDATE");
        endD = getIntent().getStringExtra("ENDDATE");

        //startD = "9-24-2017";
        //endD = "9-25-2017";
        //This redirects user back to the main menu
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Gallery.this, MainMenu.class));
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
            System.out.println(imgList);
            return imgList.size();
        }

        @Override
        public Object getItem(int position) {
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

    public ArrayList<File> listImgs(File dir) {

        ArrayList<File> a = new ArrayList<File>();

        File[] img = dir.listFiles(); //lists all the files that belong in the directory of path given

        for(int i = 0; i < img.length; i++){
            //isDirectory is a boolean function that checks to see if this specific file is a directory
            //if it is a directory, iterate through all files and add to the array
            if(img[i].isDirectory()){
                imgList.addAll(listImgs(img[i]));
                //a.addAll(imageReader(img[i]));
            }
            else if(img[i].getName().startsWith("9-24-2017") || img[i].getName().endsWith("9-25-2017")){
                imgList.add(img[i]);
            }
            else if(img[i].getName().endsWith(".jpg") || img[i].getName().endsWith(".JPG")){
                //img[i].getName().startsWith("9-24-2017") || img[i].getName().endsWith("9-25-2017
                //if(img[i].getName().endsWith(".jpg") || img[i].getName().endsWith(".JPG")){
                    imgList.add(img[i]);
            }
            else{
                System.out.println("No photos");
            }
        }
        return a;
    }

    ArrayList<File> imageReader(File root){
        ArrayList<File> a = new ArrayList<>();
        //String filepath = root + "/Picture";
        File[] img = root.listFiles(); //lists all the files that belong in the directory of path given
        for(int i = 0; i < img.length; i++){
            //isDirectory is a boolean function that checks to see if this specific file is a directory
            //if it is a directory, iterate through all files and add to the array
            if(img[i].isDirectory()){
                a.addAll(imageReader(img[i]));
            }
            else{
                //img[i].getName().startsWith("9-24-2017") || img[i].getName().endsWith("9-25-2017
                if(img[i].getName().endsWith(".jpg") || img[i].getName().endsWith(".JPG") ){
                    a.add(img[i]);
                }
            }
        }
        return a;
    }
}
