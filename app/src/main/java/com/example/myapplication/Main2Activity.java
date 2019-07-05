package com.example.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class Main2Activity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    FusedLocationProviderClient mFusedLocationClient;
    LocationCallback onRequestLocation;
    private TextView tx1;
    private TextView tx2;
   //int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);//좌표에 대한 정보를 넣어준다.
        tx1 = (TextView) findViewById(R.id.lotxt);
        tx2 = (TextView) findViewById(R.id.lotxt2);
    }


    public void btnLastLocation(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_DENIED) {
                mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(android.location.Location location) {
                                if (location != null) {
                                    Log.d("MainActivity",
                                            "location: " + location.getLatitude() + ", " + location.getLongitude());
                                    tx1.setText(Double.toString(location.getLatitude()));
                                    tx2.setText(Double.toString(location.getLongitude()));
                                }
                            }

                        });
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            }
        }

    public void btnLocationUpdate(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_DENIED) {
            LocationRequest request = new LocationRequest();
            request.setInterval(1000);
            request.setFastestInterval(500);
            request.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

            //mFusedLocationClient.requestLocationUpdates(request, new LocationCallback()
            onRequestLocation = new LocationCallback() {

                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    for (Location location : locationResult.getLocations()) {
                        Log.d("MyLocation", "location: " + location.getLatitude() + ", " + location.getLongitude());
                    }
                }
                // private void stopLocationUpdates() {
                //     mFusedLocationClient.removeLocationUpdates(request);
                // }
            };
            mFusedLocationClient.requestLocationUpdates(request, onRequestLocation, null); //gps값을 요청
        } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            }
    }

    public void stopLocationUpdates(View view) {
            mFusedLocationClient.removeLocationUpdates(onRequestLocation); // 자원을 낭비를 막기 위해 멈춰줌
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        if(requestCode == 0){
            if(grantResults[0] == 0){
                Toast.makeText(this,"권한이 승인됨",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"권한이 승인되지 않음",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
