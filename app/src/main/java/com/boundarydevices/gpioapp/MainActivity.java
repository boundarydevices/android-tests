package com.boundarydevices.gpioapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GpioDevice.GpioDeviceListener {

    private static final String TAG = "GpioApp";
    private static final int GPIO1 = 109;
    private static final int GPIO2 = 110;
    private static final int GPIO3 = 111;
    private static final int GPIO4 = 112;
    private static final int GPIO5 = 113;
    private static final int GPIO6 = 114;
    private static final int GPIO7 = 115;
    private static final int GPIOR = 68;
    private static final int GPIOG = 69;
    private static final int GPIOB = 80;
    private static final int GPIO_FB1 = 71;
    private static final int GPIO_FB2 = 72;

    private static final boolean ActiveLow = true;

    private GpioDevice gpioDevice;

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
        ToggleButton toogleButtonGpioR = findViewById(R.id.toggleButtonGpioR);
        ToggleButton toogleButtonGpioG = findViewById(R.id.toggleButtonGpioG);
        ToggleButton toogleButtonGpioB = findViewById(R.id.toggleButtonGpioB);


        toogleButtonGpio1.setOnClickListener(this);
        toogleButtonGpio2.setOnClickListener(this);
        toogleButtonGpio3.setOnClickListener(this);
        toogleButtonGpio4.setOnClickListener(this);
        toogleButtonGpio5.setOnClickListener(this);
        toogleButtonGpio6.setOnClickListener(this);
        toogleButtonGpio7.setOnClickListener(this);
        toogleButtonGpioR.setOnClickListener(this);
        toogleButtonGpioG.setOnClickListener(this);
        toogleButtonGpioB.setOnClickListener(this);

        gpioDevice = new GpioDevice(this);
        gpioDevice.subscribePinEvent(GPIO_FB1, 2);
        gpioDevice.subscribePinEvent(GPIO_FB2, 2);

        /* Initialize all GPIO to 0 at first */
        gpioDevice.set(gpioDevice.getBank(GPIO1), gpioDevice.getPin(GPIO1), ActiveLow, 0);
        gpioDevice.set(gpioDevice.getBank(GPIO2), gpioDevice.getPin(GPIO2), ActiveLow, 0);
        gpioDevice.set(gpioDevice.getBank(GPIO3), gpioDevice.getPin(GPIO3), ActiveLow, 0);
        gpioDevice.set(gpioDevice.getBank(GPIO4), gpioDevice.getPin(GPIO4), ActiveLow, 0);
        gpioDevice.set(gpioDevice.getBank(GPIO5), gpioDevice.getPin(GPIO5), ActiveLow, 0);
        gpioDevice.set(gpioDevice.getBank(GPIO6), gpioDevice.getPin(GPIO6), ActiveLow, 0);
        gpioDevice.set(gpioDevice.getBank(GPIO7), gpioDevice.getPin(GPIO7), ActiveLow, 0);
        gpioDevice.set(gpioDevice.getBank(GPIOR), gpioDevice.getPin(GPIOR), ActiveLow, 0);
        gpioDevice.set(gpioDevice.getBank(GPIOG), gpioDevice.getPin(GPIOG), ActiveLow, 0);
        gpioDevice.set(gpioDevice.getBank(GPIOB), gpioDevice.getPin(GPIOB), ActiveLow, 0);
    }

    @Override
    public void onClick(View v) {
        ToggleButton toggleButton = findViewById(v.getId());
        switch (v.getId()) {
            case R.id.toggleButtonGpio1:
                Log.i(TAG, "GPIO 1 is now " + toggleButton.isChecked());
                gpioDevice.set(gpioDevice.getBank(GPIO1), gpioDevice.getPin(GPIO1),
                        ActiveLow, toggleButton.isChecked() ? 1 : 0);
                break;
            case R.id.toggleButtonGpio2:
                Log.i(TAG, "GPIO 2 is now " + toggleButton.isChecked());
                gpioDevice.set(gpioDevice.getBank(GPIO2), gpioDevice.getPin(GPIO2),
                        ActiveLow, toggleButton.isChecked() ? 1 : 0);
                break;
            case R.id.toggleButtonGpio3:
                Log.i(TAG, "GPIO 3 is now " + toggleButton.isChecked());
                gpioDevice.set(gpioDevice.getBank(GPIO3), gpioDevice.getPin(GPIO3),
                        ActiveLow, toggleButton.isChecked() ? 1 : 0);
                break;
            case R.id.toggleButtonGpio4:
                Log.i(TAG, "GPIO 4 is now " + toggleButton.isChecked());
                gpioDevice.set(gpioDevice.getBank(GPIO4), gpioDevice.getPin(GPIO4),
                        ActiveLow, toggleButton.isChecked() ? 1 : 0);
                break;
            case R.id.toggleButtonGpio5:
                Log.i(TAG, "GPIO 5 is now " + toggleButton.isChecked());
                gpioDevice.set(gpioDevice.getBank(GPIO5), gpioDevice.getPin(GPIO5),
                        ActiveLow, toggleButton.isChecked() ? 1 : 0);
                break;
            case R.id.toggleButtonGpio6:
                Log.i(TAG, "GPIO 6 is now " + toggleButton.isChecked());
                gpioDevice.set(gpioDevice.getBank(GPIO6), gpioDevice.getPin(GPIO6),
                        ActiveLow, toggleButton.isChecked() ? 1 : 0);
                break;
            case R.id.toggleButtonGpio7:
                Log.i(TAG, "GPIO 7 is now " + toggleButton.isChecked());
                gpioDevice.set(gpioDevice.getBank(GPIO7), gpioDevice.getPin(GPIO7),
                        ActiveLow, toggleButton.isChecked() ? 1 : 0);
                break;
            case R.id.toggleButtonGpioR:
                Log.i(TAG, "GPIO R is now " + toggleButton.isChecked());
                gpioDevice.set(gpioDevice.getBank(GPIOR), gpioDevice.getPin(GPIOR),
                        ActiveLow, toggleButton.isChecked() ? 1 : 0);
                break;
            case R.id.toggleButtonGpioG:
                Log.i(TAG, "GPIO G is now " + toggleButton.isChecked());
                gpioDevice.set(gpioDevice.getBank(GPIOG), gpioDevice.getPin(GPIOG),
                        ActiveLow, toggleButton.isChecked() ? 1 : 0);
                break;
            case R.id.toggleButtonGpioB:
                Log.i(TAG, "GPIO B is now " + toggleButton.isChecked());
                gpioDevice.set(gpioDevice.getBank(GPIOB), gpioDevice.getPin(GPIOB),
                        ActiveLow, toggleButton.isChecked() ? 1 : 0);
                break;
            default:
                Log.d(TAG, "unknown id: " + v.getId());
        }
    }

    @Override
    public void onGpioDeviceEvent(int gpio, int state) {
        Log.i(TAG, "Received event for GPIO " + gpio + " whose state is now " + state);
    }
}