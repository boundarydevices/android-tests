package com.boundarydevices.gpioapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "GpioApp";
    private GpioDevice gpioDevice;
    private EditText gpioBankBox, gpioPinBox;
    private TextView gpioText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonSet = findViewById(R.id.buttonSet);
        Button buttonClear = findViewById(R.id.buttonClear);
        Button buttonGet = findViewById(R.id.buttonGet);
        gpioText = findViewById(R.id.gpioText);
        buttonSet.setOnClickListener(this);
        buttonClear.setOnClickListener(this);
        buttonGet.setOnClickListener(this);
        gpioBankBox = findViewById(R.id.gpioBankBox);
        gpioPinBox = findViewById(R.id.gpioPinBox);

        gpioDevice = new GpioDevice();
    }

    @Override
    public void onClick(View v) {
        int gpioBank = Integer.parseInt(gpioBankBox.getText().toString());
        int gpioPin = Integer.parseInt(gpioPinBox.getText().toString());

        switch (v.getId()) {
            case R.id.buttonSet:
                Log.i(TAG, "Set GPIO " + gpioBank + " " + gpioPin);
                gpioText.setText("Set GPIO " + gpioBank + " " + gpioPin);
                gpioDevice.set(gpioBank, gpioPin, 1);
                break;
            case R.id.buttonClear:
                Log.i(TAG, "Clear GPIO " + gpioBank + " " + gpioPin);
                gpioText.setText("Clear GPIO " + gpioBank + " " + gpioPin);
                gpioDevice.set(gpioBank, gpioPin, 0);
                break;
            case R.id.buttonGet:
                Log.i(TAG, "Get GPIO " + gpioBank + " " + gpioPin);
                int value = gpioDevice.get(gpioBank, gpioPin);
                gpioText.setText("Clear GPIO " + gpioBank + " " + gpioPin + ": " + value);
                break;
            default:
                Log.d(TAG, "unknown id: " + v.getId());
        }
    }
}