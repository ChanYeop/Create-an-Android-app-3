package com.example.myapplication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Main3Activity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private Sensor mSensor2;
    private ArrayList SensorList;
    ListView lvSensorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        SensorList = new ArrayList();
        List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL); // 모든 센서를 넣어줌
        ListView lvSensorList = (ListView) findViewById(R.id.listview);

        for (Sensor s: deviceSensors){
            SensorList.add("Name: "+s.getName() +"\n"+ s.getPower() +" / "+ s.getResolution() +" / "+ s.getMaximumRange()); // 센서에 대한 정보를 받아서 리스트에 추가
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, SensorList);
        lvSensorList.setAdapter(adapter);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){

            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); //가속도 센서를 사용
            mSensor2 = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY); // 중력 센서를 사용
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensor2,
                SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            TextView textView = findViewById(R.id.textView);
            textView.setText(String.valueOf(sensorEvent.values[0]));
            TextView textView2 = findViewById(R.id.textView2);
            textView2.setText(String.valueOf(sensorEvent.values[1]));
            TextView textView3 = findViewById(R.id.textView4);
            textView3.setText(String.valueOf(sensorEvent.values[2]));
        }
        else if(sensorEvent.sensor.getType() == Sensor.TYPE_GRAVITY) {
            TextView textView4 = findViewById(R.id.textView6);
            textView4.setText(String.valueOf(sensorEvent.values[0]));
            TextView textView5 = findViewById(R.id.textView7);
            textView5.setText(String.valueOf(sensorEvent.values[1]));
            TextView textView6 = findViewById(R.id.textView8);
            textView6.setText(String.valueOf(sensorEvent.values[2]));
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
// Do something here if sensor accuracy changes.
    }
}
