package com.boundarydevices.platformtesting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TestAccelerometer extends Activity implements SensorEventListener {

    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private TextView mTextX;
    private TextView mTextY;
    private TextView mTextZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_accelerometer);

        mContext = this;

        setTitle("Platform Testing - Accelerometer");

        /* Initialize the result */
        TestResults.addResult(TAG, TestResults.TEST_RESULT_FAILED);

        mTextX = (TextView) findViewById(R.id.value_x);
        mTextY = (TextView) findViewById(R.id.value_y);
        mTextZ = (TextView) findViewById(R.id.value_z);

        final Button button_accel_yes = (Button) findViewById(R.id.button_accel_yes);
        button_accel_yes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(TAG, "button_accel_yes");
                TestResults.addResult(TAG, TestResults.TEST_RESULT_SUCCESS);
                Intent intent = new Intent(mContext, TestBacklight.class);
                startActivity(intent);
            }
        });

        final Button button_accel_no = (Button) findViewById(R.id.button_accel_no);
        button_accel_no.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(TAG, "button_accel_no");
                Intent intent = new Intent(mContext, TestBacklight.class);
                startActivity(intent);
            }
        });

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
        mTextX.setText(Float.toString(event.values[0]));
        mTextY.setText(Float.toString(event.values[1]));
        mTextZ.setText(Float.toString(event.values[2]));
    }
}
