package com.boundarydevices.gpioapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "GpioApp";
    private static final int GPIO1 = 109;
    private static final int GPIO2 = 110;
    private static final int GPIO3 = 111;
    private static final int GPIO4 = 112;
    private static final int GPIO5 = 113;
    private static final int GPIO6 = 114;
    private static final int GPIO7 = 115;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToggleButton toogleButtonGpio1 = findViewById(R.id.toggleButtonGpio1);
        ToggleButton toogleButtonGpio2 = findViewById(R.id.toggleButtonGpio2);
        ToggleButton toogleButtonGpio3 = findViewById(R.id.toggleButtonGpio3);
        ToggleButton toogleButtonGpio4 = findViewById(R.id.toggleButtonGpio4);
        ToggleButton toogleButtonGpio5 = findViewById(R.id.toggleButtonGpio5);
        ToggleButton toogleButtonGpio6 = findViewById(R.id.toggleButtonGpio6);
        ToggleButton toogleButtonGpio7 = findViewById(R.id.toggleButtonGpio7);

        toogleButtonGpio1.setOnClickListener(this);
        toogleButtonGpio2.setOnClickListener(this);
        toogleButtonGpio3.setOnClickListener(this);
        toogleButtonGpio4.setOnClickListener(this);
        toogleButtonGpio5.setOnClickListener(this);
        toogleButtonGpio6.setOnClickListener(this);
        toogleButtonGpio7.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ToggleButton toggleButton = findViewById(v.getId());
        switch (v.getId()) {
            case R.id.toggleButtonGpio1:
                Log.i(TAG, "GPIO 1 is now " + toggleButton.isChecked());
                break;
            case R.id.toggleButtonGpio2:
                Log.i(TAG, "GPIO 2 is now " + toggleButton.isChecked());
                break;
            case R.id.toggleButtonGpio3:
                Log.i(TAG, "GPIO 3 is now " + toggleButton.isChecked());
                break;
            case R.id.toggleButtonGpio4:
                Log.i(TAG, "GPIO 4 is now " + toggleButton.isChecked());
                break;
            case R.id.toggleButtonGpio5:
                Log.i(TAG, "GPIO 5 is now " + toggleButton.isChecked());
                break;
            case R.id.toggleButtonGpio6:
                Log.i(TAG, "GPIO 6 is now " + toggleButton.isChecked());
                break;
            case R.id.toggleButtonGpio7:
                Log.i(TAG, "GPIO 7 is now " + toggleButton.isChecked());
                break;
            default:
                Log.d(TAG, "unknown id: " + v.getId());
        }
    }
}