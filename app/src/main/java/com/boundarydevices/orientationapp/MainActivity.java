package com.boundarydevices.orientationapp;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.provider.Settings.System;

public class MainActivity extends Activity implements SensorEventListener {

    private String TAG = "BoundaryOrientationApp";
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private boolean blanked = false;
    private static final boolean VDEBUG = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
        double x = event.values[0];
        double y = event.values[1];
        double z = event.values[2];

        if (VDEBUG) Log.v(TAG, "onSensorEvent(" + x + ", " + y + ", " + z + ")");

        // If some values are exactly zero, then likely the sensor is not powered up yet.
        // ignore these events to avoid false horizontal positives.
        if (x == 0.0 || y == 0.0 || z == 0.0) return;

        // magnitude of the acceleration vector projected onto XY plane
        final double xy = Math.sqrt(x*x + y*y);

        // compute the vertical angle
        double angle = Math.atan2(xy, z);

        // convert to degrees
        angle = angle * 180.0 / Math.PI;

        if (VDEBUG) Log.d(TAG, "angle: " + angle);

        if ((angle > 70.0) && (blanked == false)) {
            Log.d(TAG, "blanking display");
            blanked = true;
            System.putInt(getContentResolver(), System.SCREEN_BRIGHTNESS, 0);
        } else if ((angle < 70.0) && (blanked == true)) {
            Log.d(TAG, "unblanking display");
            blanked = false;
            System.putInt(getContentResolver(), System.SCREEN_BRIGHTNESS, 255);
        }
    }
}