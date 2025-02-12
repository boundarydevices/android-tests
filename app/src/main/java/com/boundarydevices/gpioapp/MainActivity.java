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
        if ((gpioBankBox.getText().length() == 0) || (gpioPinBox.getText().length() == 0)) {
            gpioText.setText("Requires both Bank & Pin to be set!");
            return;
        }
        int gpioBank = Integer.parseInt(gpioBankBox.getText().toString());
        int gpioPin = Integer.parseInt(gpioPinBox.getText().toString());
        int ret;

        switch (v.getId()) {
            case R.id.buttonSet:
                gpioText.setText("Set GPIO " + gpioBank + " " + gpioPin);
                ret = gpioDevice.set(gpioBank, gpioPin, 1);
                if (ret < 0)
                    gpioText.setText("Couldn't set GPIO " + ret);
                break;
            case R.id.buttonClear:
                gpioText.setText("Clear GPIO " + gpioBank + " " + gpioPin);
                ret = gpioDevice.set(gpioBank, gpioPin, 0);
                if (ret < 0)
                    gpioText.setText("Couldn't clear GPIO " + ret);
                break;
            case R.id.buttonGet:
                int value = gpioDevice.get(gpioBank, gpioPin);
                if (value < 0)
                    gpioText.setText("Couldn't get GPIO " + value);
                else
                    gpioText.setText("Get GPIO " + gpioBank + " " + gpioPin + ": " + value);
                break;
            default:
                Log.d(TAG, "unknown id: " + v.getId());
        }
    }
}