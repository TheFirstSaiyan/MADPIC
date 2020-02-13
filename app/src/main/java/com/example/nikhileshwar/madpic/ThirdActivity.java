package com.example.nikhileshwar.madpic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.Image;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThirdActivity extends AppCompatActivity {
    private  ListView listView;
    private ArrayList<String>file_names=new ArrayList<>();
    File myFilesDir;
    //static CustomAdaptor customAdaptor;
    ViewPager viewPager;
    private Button gallery;
    private String view_gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Intent intent = getIntent();
        file_names = intent.getStringArrayListExtra("matching_files");
        myFilesDir = new File(Environment.getExternalStorageDirectory(),"images_MAD");
        viewPager = findViewById(R.id.view_pager);

        Toast.makeText(this,file_names.toString(),Toast.LENGTH_LONG).show();
        ImageAdapter adapter= new ImageAdapter(this, file_names);
        gallery  = findViewById(R.id.gallery_button);
        viewPager.setAdapter(adapter);
       // listView=findViewById(R.id.list);
//        customAdaptor=new CustomAdaptor(this,file_names);
//        listView.setAdapter(customAdaptor);

    }
    public void viewInGallery(View view)
    {

        int pos = viewPager.getCurrentItem();
        view_gallery=myFilesDir + "/" +file_names.get(pos);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(view_gallery), "image/*");
        startActivity(intent);
    }


}
