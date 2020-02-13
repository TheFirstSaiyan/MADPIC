package com.example.nikhileshwar.madpic;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SecondActivity extends AppCompatActivity {
    private Button audioButton;
    private ImageView imageView;
    File myFilesDir;
    private Button gallery;
    private String view_gallery="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Toast.makeText(SecondActivity.this,"HI", Toast.LENGTH_LONG).show();
        audioButton = findViewById(R.id.audio);
        imageView = findViewById(R.id.imageView);
        gallery  = findViewById(R.id.view);
        myFilesDir = new File(Environment.getExternalStorageDirectory(),"images_MAD");
    }



    public void recognizeSpeech(View view)
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"speak");
        try
        {
            startActivityForResult(intent,1);
        }
        catch(Exception e)
        {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    public void viewInGallery(View view)
    {
//        if(view_gallery!="")
//         startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromFile(new File(view_gallery))));
//        else
//            Toast.makeText(this,"No Image selected",Toast.LENGTH_LONG).show();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(view_gallery), "image/*");
        startActivity(intent);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1)
        {
            Log.i("LALA","LALA");
            if(data == null)
            {

                Toast.makeText(this,"speak louder",Toast.LENGTH_LONG).show();
            }
            else
            {
                ArrayList<String> list = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                String[] file_names = new File(Environment.getExternalStorageDirectory(),"images_MAD").list();

                String first = list.get(0);
                String file_name="";
                //split the second string into words
                int max=0;
                for(String file: file_names)
                {
                    int count=0;
                    String second=file.substring(0,file.length()-4);
                    List<String> wordsOfSecond = Arrays.asList(second.split(" "));
                    //split and compare each word of the first string
                    for (String word : first.split(" ")) {
                        if(wordsOfSecond.contains(word))
                        {
                            count++;
                        }
                        if(count>max)
                        {
                            file_name=file;
                        }
                    }
                    Toast.makeText(this,Integer.toString(count),Toast.LENGTH_LONG).show();
                }


                //String file_name = name;
                File f = new File(myFilesDir +"/" +  file_name);
                if(f.exists()) {
                    view_gallery=myFilesDir + "/" +file_name;
                    Toast.makeText(this, file_name, Toast.LENGTH_LONG).show();
                    Bitmap cameraBitmap;
                    cameraBitmap = BitmapFactory.decodeFile(myFilesDir + "/" +file_name);
                    Bitmap.createBitmap(cameraBitmap);
                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                    cameraBitmap = Bitmap.createBitmap(cameraBitmap, 0, 0, cameraBitmap.getWidth(), cameraBitmap.getHeight(), matrix, true);
                    imageView.setImageBitmap(cameraBitmap);
                }
                else
                {
                    Toast.makeText(this,"requested tag does not exist!!",Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}
