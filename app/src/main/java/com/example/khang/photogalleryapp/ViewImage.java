package com.example.khang.photogalleryapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.File;

public class ViewImage extends AppCompatActivity {
    ImageView clickedImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        Intent i = getIntent();
        //File f = (File) i.getExtras().getParcelable("img");
        String f = getIntent().getStringExtra("img");
        clickedImg = (ImageView) findViewById(R.id.imgClicked);
        clickedImg.setImageURI(Uri.parse(f));
    }

}
