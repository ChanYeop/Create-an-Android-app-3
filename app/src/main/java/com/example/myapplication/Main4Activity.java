package com.example.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.hardware.Camera;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class Main4Activity extends AppCompatActivity {
    CameraPreview mPreview;
    Camera mCamera;
    ArrayAdapter adapter;
    private List imageList;
    private File file;
    private Bitmap imageBitmap;
    AdapterView.OnItemClickListener mCallback=null;

    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File file = getOutputMediaFile(MEDIA_TYPE_IMAGE);

            if(file == null) {
                return;
            }

            try{
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(data);
                fos.close();
                imageList.add(file.getName()); //각각의 파일의 이름을 추가해주었다.
                adapter.notifyDataSetChanged(); // 리스트에 값이 추가된 후에 다시 갱신되도록 설정
            } catch (IOException e) {
                Log.d("MainActivity", e.getMessage());
            }

        }
    };

    private File getOutputMediaFile(int type){

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(),
                "MyCamera");
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("CameraApp", "failed to create directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()); // 시간에 대한 패턴 지정
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg"); // 파일 경로 지정
        } else {
            return null;
        }
        return mediaFile;
    }

/*
    public void surfaceDestroyed(SurfaceHolder holder) {
// Surface will be destroyed when we return, so stop the preview.
        if (mCamera != null) {
// Call stopPreview() to stop updating the preview surface.
            mCamera.stopPreview();
        }
    }
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);


        imageList = new ArrayList();
        String rootSD = Environment.getExternalStorageDirectory().toString();
        file = new File(rootSD+ "/MyCamera");

        if(file.exists()){
            File list[] = file.listFiles();
            for(int i=0; i<list.length; i++){
                imageList.add(list[i].getName());
            }
        }

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, imageList);
        ListView lvImageList = (ListView) findViewById(R.id.listview2);
        lvImageList.setAdapter(adapter);


        lvImageList.setOnItemClickListener(new AdapterView.OnItemClickListener()

        {
            @Override
            public void onItemClick(AdapterView<?> parentView, View clickedView, int position, long id)

            {
                String selected_item=(String) parentView.getItemAtPosition(position );
                File imgFile = new  File(file.getPath() + File.separator +selected_item);

                if(imgFile.exists()){

                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    ImageView myImage = (ImageView) findViewById(R.id.imageView);
                    myImage.setImageBitmap(myBitmap);

                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        ConstraintLayout rootView = findViewById(R.id.rootView);
        if(openCameraSafe(0)) {
            mPreview = new CameraPreview(this, mCamera);
            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(400,400);
            rootView.addView(mPreview, 0, params);
        }
    }

    public void btnCamera(View view) {
                mCamera.takePicture(null, null, mPictureCallback);
    }

    private boolean openCameraSafe(int id) {
        boolean qOpen = false;
        try{
            releaseCameraAndPreview();
            mCamera = Camera.open(id);
            qOpen = (mCamera != null);
        } catch(Exception e) {
            Log.d("MainActivity", e.getMessage());
        }
        return qOpen;
    }

    private void releaseCameraAndPreview() {
        if(mPreview!=null) {
            mPreview.setCamera(null);
        }
        if(mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }




}
