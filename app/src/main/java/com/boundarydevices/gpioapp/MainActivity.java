package com.boundarydevices.gpioapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GpioDevice.GpioDeviceListener {

    private static final String TAG = "GpioApp";
    private static final int LOCK_NB_GPIO = 2;
    private static final int[] LOCK1 = { 109, 110 };
    private static final int[] LOCK2 = { 111, 112 };
    private static final int[] LOCK3 = { 113, 114 };
    private static final int[] LOCK4 = { 115, 72 };
    private static final int[] LOCK5 = { 70, 71 };
    private static final int[] LOCK6 = { 64, 65 };
    private static final int[] LOCK7 = { 73, 74 };

    private static final boolean ActiveLow = false;

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

        toogleButtonGpio1.setOnClickListener(this);
        toogleButtonGpio2.setOnClickListener(this);
        toogleButtonGpio3.setOnClickListener(this);
        toogleButtonGpio4.setOnClickListener(this);
        toogleButtonGpio5.setOnClickListener(this);
        toogleButtonGpio6.setOnClickListener(this);
        toogleButtonGpio7.setOnClickListener(this);

        gpioDevice = new GpioDevice(this);

        /* Initialize all GPIO to 0 at first */
        for (int i = 0; i < LOCK_NB_GPIO; i++) {
            gpioDevice.set(gpioDevice.getBank(LOCK1[i]), gpioDevice.getPin(LOCK1[i]), ActiveLow, 0);
            gpioDevice.set(gpioDevice.getBank(LOCK2[i]), gpioDevice.getPin(LOCK2[i]), ActiveLow, 0);
            gpioDevice.set(gpioDevice.getBank(LOCK3[i]), gpioDevice.getPin(LOCK3[i]), ActiveLow, 0);
            gpioDevice.set(gpioDevice.getBank(LOCK4[i]), gpioDevice.getPin(LOCK4[i]), ActiveLow, 0);
            gpioDevice.set(gpioDevice.getBank(LOCK5[i]), gpioDevice.getPin(LOCK5[i]), ActiveLow, 0);
            gpioDevice.set(gpioDevice.getBank(LOCK6[i]), gpioDevice.getPin(LOCK6[i]), ActiveLow, 0);
            gpioDevice.set(gpioDevice.getBank(LOCK7[i]), gpioDevice.getPin(LOCK7[i]), ActiveLow, 0);
        }
    }

    @Override
    public void onClick(View v) {
        final ToggleButton toggleButton = findViewById(v.getId());
        int on = toggleButton.isChecked() ? 0 : 1;
        toggleButton.setEnabled(false);

        switch (v.getId()) {
            case R.id.toggleButtonGpio1:
                Log.i(TAG, "LOCK 1 is now " + on);
                gpioDevice.set(gpioDevice.getBank(LOCK1[on]), gpioDevice.getPin(LOCK1[on]),
                        ActiveLow, 1);
                break;
            case R.id.toggleButtonGpio2:
                Log.i(TAG, "LOCK 2 is now " + on);
                gpioDevice.set(gpioDevice.getBank(LOCK2[on]), gpioDevice.getPin(LOCK2[on]),
                        ActiveLow, 1);
                break;
            case R.id.toggleButtonGpio3:
                Log.i(TAG, "LOCK 3 is now " + on);
                gpioDevice.set(gpioDevice.getBank(LOCK3[on]), gpioDevice.getPin(LOCK3[on]),
                        ActiveLow, 1);
                break;
            case R.id.toggleButtonGpio4:
                Log.i(TAG, "LOCK 4 is now " + on);
                gpioDevice.set(gpioDevice.getBank(LOCK4[on]), gpioDevice.getPin(LOCK4[on]),
                        ActiveLow, 1);
                break;
            case R.id.toggleButtonGpio5:
                Log.i(TAG, "LOCK 5 is now " + on);
                gpioDevice.set(gpioDevice.getBank(LOCK5[on]), gpioDevice.getPin(LOCK5[on]),
                        ActiveLow, 1);
                break;
            case R.id.toggleButtonGpio6:
                Log.i(TAG, "LOCK 6 is now " + on);
                gpioDevice.set(gpioDevice.getBank(LOCK6[on]), gpioDevice.getPin(LOCK6[on]),
                        ActiveLow, 1);
                break;
            case R.id.toggleButtonGpio7:
                Log.i(TAG, "LOCK 7 is now " + on);
                gpioDevice.set(gpioDevice.getBank(LOCK7[on]), gpioDevice.getPin(LOCK7[on]),
                        ActiveLow, 1);
                break;
            default:
                Log.d(TAG, "unknown id: " + v.getId());
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // wait for 5 second before enabling next move
                switch (toggleButton.getId()) {
                    case R.id.toggleButtonGpio1:
                        Log.i(TAG, "LOCK 1 reset");
                        for (int i = 0; i < LOCK_NB_GPIO; i++)
                            gpioDevice.set(gpioDevice.getBank(LOCK1[i]), gpioDevice.getPin(LOCK1[i]), ActiveLow, 0);
                        break;
                    case R.id.toggleButtonGpio2:
                        Log.i(TAG, "LOCK 2 reset");
                        for (int i = 0; i < LOCK_NB_GPIO; i++)
                            gpioDevice.set(gpioDevice.getBank(LOCK2[i]), gpioDevice.getPin(LOCK2[i]), ActiveLow, 0);
                        break;
                    case R.id.toggleButtonGpio3:
                        Log.i(TAG, "LOCK 3  reset");
                        for (int i = 0; i < LOCK_NB_GPIO; i++)
                            gpioDevice.set(gpioDevice.getBank(LOCK3[i]), gpioDevice.getPin(LOCK3[i]), ActiveLow, 0);
                        break;
                    case R.id.toggleButtonGpio4:
                        Log.i(TAG, "LOCK 4 reset");
                        for (int i = 0; i < LOCK_NB_GPIO; i++)
                            gpioDevice.set(gpioDevice.getBank(LOCK4[i]), gpioDevice.getPin(LOCK4[i]), ActiveLow, 0);
                        break;
                    case R.id.toggleButtonGpio5:
                        Log.i(TAG, "LOCK 5 reset");
                        for (int i = 0; i < LOCK_NB_GPIO; i++)
                            gpioDevice.set(gpioDevice.getBank(LOCK5[i]), gpioDevice.getPin(LOCK5[i]), ActiveLow, 0);
                        break;
                    case R.id.toggleButtonGpio6:
                        Log.i(TAG, "LOCK 6 reset");
                        for (int i = 0; i < LOCK_NB_GPIO; i++)
                            gpioDevice.set(gpioDevice.getBank(LOCK6[i]), gpioDevice.getPin(LOCK6[i]), ActiveLow, 0);
                        break;
                    case R.id.toggleButtonGpio7:
                        Log.i(TAG, "LOCK 7 reset");
                        for (int i = 0; i < LOCK_NB_GPIO; i++)
                            gpioDevice.set(gpioDevice.getBank(LOCK7[i]), gpioDevice.getPin(LOCK7[i]), ActiveLow, 0);
                        break;
                    default:
                        Log.d(TAG, "unknown id: " + toggleButton.getId());
                }
                toggleButton.setEnabled(true);
            }
        }, 5000);
    }

    @Override
    public void onGpioDeviceEvent(int gpio, int state) {
        Log.i(TAG, "Received event for GPIO " + gpio + " whose state is now " + state);
    }
}