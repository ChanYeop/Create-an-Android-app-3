package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(intent);
            }
        });


        Button button2 = (Button) findViewById(R.id.button5);
        button2.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), Main3Activity.class);
                startActivity(intent);
            }
        });

        Button button3 = (Button) findViewById(R.id.button6);
        button3.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) //권한을 사용시 이것을 통해 권한 승인
                        != PackageManager.PERMISSION_DENIED) {
                    if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_DENIED) {
                        Intent intent = new Intent(getApplicationContext(), Main4Activity.class);
                        startActivity(intent);
                    }
                }
                else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        if(requestCode == 0){
            if(grantResults[0] == 0){
                Toast.makeText(this,"권한이 승인됨",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"권한이 승인 되지 않음",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
