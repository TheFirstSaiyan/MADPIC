package com.example.nikhileshwar.madpic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class ImageAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<String> file_names = new ArrayList<String>();
    File myFilesDir;
    private ArrayList<Bitmap> bitmaps=new ArrayList<>();

    ImageAdapter(Context ctx,ArrayList<String> files)
    {
        context = ctx;
        file_names = files;
        myFilesDir = new File(Environment.getExternalStorageDirectory(),"images_MAD");
        for(int i=0;i<files.size();i++)
        {
            Bitmap cameraBitmap;
            cameraBitmap = BitmapFactory.decodeFile(myFilesDir + "/" +file_names.get(i));
            Bitmap.createBitmap(cameraBitmap);
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            cameraBitmap = Bitmap.createBitmap(cameraBitmap, 0, 0, cameraBitmap.getWidth(), cameraBitmap.getHeight(), matrix, true);
            bitmaps.add(cameraBitmap);
        }
    }
    @Override
    public int getCount() {
        return file_names.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //add code
        File f = new File(myFilesDir +"/" +  file_names.get(position));
        if(f.exists()) {
            //view_gallery=myFilesDir + "/" +file_name;
            //Toast.makeText(this, file_names.get(position), Toast.LENGTH_LONG).show();
//            Bitmap cameraBitmap;
//            cameraBitmap = BitmapFactory.decodeFile(myFilesDir + "/" +file_names.get(position));
//            Bitmap.createBitmap(cameraBitmap);
//            Matrix matrix = new Matrix();
//            matrix.postRotate(90);
//            cameraBitmap = Bitmap.createBitmap(cameraBitmap, 0, 0, cameraBitmap.getWidth(), cameraBitmap.getHeight(), matrix, true);
            imageView.setImageBitmap(bitmaps.get(position));
            container.addView(imageView,0);
        }

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView)object);
    }
}
