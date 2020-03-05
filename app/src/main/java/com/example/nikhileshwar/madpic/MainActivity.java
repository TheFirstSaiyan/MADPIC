package com.example.nikhileshwar.madpic;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.support.v7.widget.Toolbar;
public class MainActivity extends AppCompatActivity {

    private Button takePictureButton;
    private ImageView imageView;
    private Button audioButton;
    File myFilesDir;
    private String name;
    private Toolbar mTopToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("hi","hi");
        takePictureButton = findViewById(R.id.cam);
        imageView = findViewById(R.id.imageView2);
        audioButton = findViewById(R.id.audio);
        audioButton.setEnabled(false);
        myFilesDir = new File(Environment.getExternalStorageDirectory(),"images_MAD");
        mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);
        if(!myFilesDir.exists())
            myFilesDir.mkdirs();
       // myFilesDir = new File(Environment.getExternalStoragePublicDirectory(
               // Environment.DIRECTORY_PICTURES), "CameraDemo");
        //myFilesDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.example.nikhileshwar.madpic/files");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            takePictureButton.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        name ="";


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_open) {
//            Toast.makeText(MainActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
//            Intent myIntent = new Intent(MainActivity.this, SecondActivity.class);
//            myIntent.putExtra("key", 0); //Optional parameters
//            MainActivity.this.startActivity(myIntent);


            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
           // RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"speak");
            intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE,true);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");

            try
            {
                startActivityForResult(intent,2);
            }
            catch(Exception e)
            {
                //Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
            }


            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                takePictureButton.setEnabled(true);
            }
        }
    }

    public void takePicture(View view) {
        name="";
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(myFilesDir.toString()+"/temp.jpg")));
        Log.i("HELLLO","HELLO");
        File file = new File(myFilesDir + "/temp.jpg");
        boolean deleted = file.delete();
        imageView.setImageResource(R.drawable.cam);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, 0);
    }

    public void recognizeSpeech(View view)
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"speak");
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE,true);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");

        try
        {
            startActivityForResult(intent,1);
        }
        catch(Exception e)
        {
            //Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==0){
            if(data == null)
            {
                //Toast.makeText(this,"null data",Toast.LENGTH_LONG).show();
            }
            try {
                Bitmap cameraBitmap;
                cameraBitmap = BitmapFactory.decodeFile(myFilesDir + "/temp.jpg");
                Bitmap.createBitmap(cameraBitmap);
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                cameraBitmap = Bitmap.createBitmap(cameraBitmap, 0, 0, cameraBitmap.getWidth(), cameraBitmap.getHeight(), matrix, true);
//                int nh = (int) ( cameraBitmap.getHeight() * (800.0 / cameraBitmap.getWidth()) );
//                cameraBitmap = Bitmap.createScaledBitmap(cameraBitmap, 600, nh, true);

                imageView.setImageBitmap(cameraBitmap);
                File file = new File(myFilesDir + "/temp.jpg");
                //boolean deleted = file.delete();
                audioButton.setEnabled(true);


                Log.i("DONE","DONE");
            }
            catch(Exception e){
                //Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
            }


        }
        else if(requestCode == 1)
        {
            Log.i("LALA","LALA");
            if(data == null)
            {
                Toast.makeText(this, "speak louder", Toast.LENGTH_LONG).show();
                File src = new File(myFilesDir + "/temp.jpg");
                File des = new File(myFilesDir + "/" + "IMG_"+new Date().toString() + ".jpg");
                src.renameTo(des);
                Toast.makeText(this,des.getName(),Toast.LENGTH_LONG).show();
            }
            else
            {
                ArrayList<String> list = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                audioButton.setEnabled(false);

                name = list.get(0);
                name =  name.replaceAll("[^A-Za-z0-9 ]", "").toLowerCase();
                File src = new File(myFilesDir + "/temp.jpg");
                File des = new File(myFilesDir + "/" + name+ ".jpg");
                src.renameTo(des);
                //Toast.makeText(this,des.getName(),Toast.LENGTH_LONG).show();
            }
        }
        else if(requestCode == 2)
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
                ArrayList<String>matching_files = new ArrayList<String>();
                //split the second string into words
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
                        if(count>0)
                        {
                            //file_name=file;
                            matching_files.add(file);
                        }
                    }
                    //Toast.makeText(this,Integer.toString(count),Toast.LENGTH_LONG).show();
                }
//                matching_files.add("mouse.jpg");
                if(matching_files.size() == 0)
                {
                    Toast.makeText(this,"requested tag does not exist!!",Toast.LENGTH_LONG).show();
                }
                else {
                    Intent myIntent = new Intent(MainActivity.this, ThirdActivity.class);
                    myIntent.putStringArrayListExtra("matching_files", matching_files); //Optional parameters
                    MainActivity.this.startActivity(myIntent);
                }



           }
        }
    }


}
