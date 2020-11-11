package com.boundarydevices.gpioapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GpioDevice.GpioDeviceListener {

    private static final String TAG = "GpioApp";

    //Door actuators
    private static final int GPIO1 = 109;
    private static final int GPIO2 = 110;
    private static final int GPIO3 = 111;
    private static final int GPIO4 = 112;
    private static final int GPIO5 = 113;
    private static final int GPIO6 = 114;
    private static final int GPIO7 = 115;

    //LEDs
    private static final int GPIOR1 = 69;
    private static final int GPIOG1 = 68;
    private static final int GPIOB1 = 80;

    private static final int GPIOR2 = 82;
    private static final int GPIOG2 = 75;
    private static final int GPIOB2 = 78;

    private static final int GPIOR3 = 97;
    private static final int GPIOG3 = 128;
    private static final int GPIOB3 = 99;

    private static final int GPIOR4 = 101;
    private static final int GPIOG4 = 100;
    private static final int GPIOB4 = 102;

    private static final int GPIOR5 = 104;
    private static final int GPIOG5 = 103;
    private static final int GPIOB5 = 105;

    private static final int GPIOR6 = 117;
    private static final int GPIOG6 = 119;
    private static final int GPIOB6 = 121;

    private static final int GPIOR7 = 120;
    private static final int GPIOG7 = 116;
    private static final int GPIOB7 = 122;

    //Door feedback
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

        ToggleButton toogleButtonGpioR1 = findViewById(R.id.toggleButtonGpioR1);
        ToggleButton toogleButtonGpioG1 = findViewById(R.id.toggleButtonGpioG1);
        ToggleButton toogleButtonGpioB1 = findViewById(R.id.toggleButtonGpioB1);

        ToggleButton toogleButtonGpioR2 = findViewById(R.id.toggleButtonGpioR2);
        ToggleButton toogleButtonGpioG2 = findViewById(R.id.toggleButtonGpioG2);
        ToggleButton toogleButtonGpioB2 = findViewById(R.id.toggleButtonGpioB2);

        ToggleButton toogleButtonGpioR3 = findViewById(R.id.toggleButtonGpioR3);
        ToggleButton toogleButtonGpioG3 = findViewById(R.id.toggleButtonGpioG3);
        ToggleButton toogleButtonGpioB3 = findViewById(R.id.toggleButtonGpioB3);

        ToggleButton toogleButtonGpioR4 = findViewById(R.id.toggleButtonGpioR4);
        ToggleButton toogleButtonGpioG4 = findViewById(R.id.toggleButtonGpioG4);
        ToggleButton toogleButtonGpioB4 = findViewById(R.id.toggleButtonGpioB4);

        ToggleButton toogleButtonGpioR5 = findViewById(R.id.toggleButtonGpioR5);
        ToggleButton toogleButtonGpioG5 = findViewById(R.id.toggleButtonGpioG5);
        ToggleButton toogleButtonGpioB5 = findViewById(R.id.toggleButtonGpioB5);

        ToggleButton toogleButtonGpioR6 = findViewById(R.id.toggleButtonGpioR5);
        ToggleButton toogleButtonGpioG6 = findViewById(R.id.toggleButtonGpioG5);
        ToggleButton toogleButtonGpioB6 = findViewById(R.id.toggleButtonGpioB5);

        ToggleButton toogleButtonGpioR7 = findViewById(R.id.toggleButtonGpioR5);
        ToggleButton toogleButtonGpioG7 = findViewById(R.id.toggleButtonGpioG5);
        ToggleButton toogleButtonGpioB7 = findViewById(R.id.toggleButtonGpioB5);


        toogleButtonGpio1.setOnClickListener(this);
        toogleButtonGpio2.setOnClickListener(this);
        toogleButtonGpio3.setOnClickListener(this);
        toogleButtonGpio4.setOnClickListener(this);
        toogleButtonGpio5.setOnClickListener(this);
        toogleButtonGpio6.setOnClickListener(this);
        toogleButtonGpio7.setOnClickListener(this);

        toogleButtonGpioR1.setOnClickListener(this);
        toogleButtonGpioG1.setOnClickListener(this);
        toogleButtonGpioB1.setOnClickListener(this);

        toogleButtonGpioR2.setOnClickListener(this);
        toogleButtonGpioG2.setOnClickListener(this);
        toogleButtonGpioB2.setOnClickListener(this);

        toogleButtonGpioR3.setOnClickListener(this);
        toogleButtonGpioG3.setOnClickListener(this);
        toogleButtonGpioB3.setOnClickListener(this);

        toogleButtonGpioR4.setOnClickListener(this);
        toogleButtonGpioG4.setOnClickListener(this);
        toogleButtonGpioB4.setOnClickListener(this);

        toogleButtonGpioR5.setOnClickListener(this);
        toogleButtonGpioG5.setOnClickListener(this);
        toogleButtonGpioB5.setOnClickListener(this);

        toogleButtonGpioR6.setOnClickListener(this);
        toogleButtonGpioG6.setOnClickListener(this);
        toogleButtonGpioB6.setOnClickListener(this);

        toogleButtonGpioR7.setOnClickListener(this);
        toogleButtonGpioG7.setOnClickListener(this);
        toogleButtonGpioB7.setOnClickListener(this);

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

        gpioDevice.set(gpioDevice.getBank(GPIOR1), gpioDevice.getPin(GPIOR1), ActiveLow, 1);
        gpioDevice.set(gpioDevice.getBank(GPIOG1), gpioDevice.getPin(GPIOG1), ActiveLow, 1);
        gpioDevice.set(gpioDevice.getBank(GPIOB1), gpioDevice.getPin(GPIOB1), ActiveLow, 1);

        gpioDevice.set(gpioDevice.getBank(GPIOR2), gpioDevice.getPin(GPIOR2), ActiveLow, 1);
        gpioDevice.set(gpioDevice.getBank(GPIOG2), gpioDevice.getPin(GPIOG2), ActiveLow, 1);
        gpioDevice.set(gpioDevice.getBank(GPIOB2), gpioDevice.getPin(GPIOB2), ActiveLow, 1);

        gpioDevice.set(gpioDevice.getBank(GPIOR3), gpioDevice.getPin(GPIOR3), ActiveLow, 1);
        gpioDevice.set(gpioDevice.getBank(GPIOG3), gpioDevice.getPin(GPIOG3), ActiveLow, 1);
        gpioDevice.set(gpioDevice.getBank(GPIOB3), gpioDevice.getPin(GPIOB3), ActiveLow, 1);

        gpioDevice.set(gpioDevice.getBank(GPIOR4), gpioDevice.getPin(GPIOR4), ActiveLow, 1);
        gpioDevice.set(gpioDevice.getBank(GPIOG4), gpioDevice.getPin(GPIOG4), ActiveLow, 1);
        gpioDevice.set(gpioDevice.getBank(GPIOB4), gpioDevice.getPin(GPIOB4), ActiveLow, 1);

        gpioDevice.set(gpioDevice.getBank(GPIOR5), gpioDevice.getPin(GPIOR5), ActiveLow, 1);
        gpioDevice.set(gpioDevice.getBank(GPIOG5), gpioDevice.getPin(GPIOG5), ActiveLow, 1);
        gpioDevice.set(gpioDevice.getBank(GPIOB5), gpioDevice.getPin(GPIOB5), ActiveLow, 1);

        gpioDevice.set(gpioDevice.getBank(GPIOR6), gpioDevice.getPin(GPIOR6), ActiveLow, 1);
        gpioDevice.set(gpioDevice.getBank(GPIOG6), gpioDevice.getPin(GPIOG6), ActiveLow, 1);
        gpioDevice.set(gpioDevice.getBank(GPIOB6), gpioDevice.getPin(GPIOB6), ActiveLow, 1);

        gpioDevice.set(gpioDevice.getBank(GPIOR7), gpioDevice.getPin(GPIOR7), ActiveLow, 1);
        gpioDevice.set(gpioDevice.getBank(GPIOG7), gpioDevice.getPin(GPIOG7), ActiveLow, 1);
        gpioDevice.set(gpioDevice.getBank(GPIOB7), gpioDevice.getPin(GPIOB7), ActiveLow, 1);

        //comments
        //Turn all LEDs off
        for(int i=118; i<=140; i++) {
            gpioDevice.set(gpioDevice.getBank(i), gpioDevice.getPin(i), ActiveLow, 1);
        }

        //
        Handler handler = new Handler();
        int count = 0;
        for(int i=118; i<=140; i++)
        {
            count++;
            final int ij = i;
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    gpioDevice.set(gpioDevice.getBank(ij-1), gpioDevice.getPin(ij-1), ActiveLow, 0);
                }
            }, 500 * count);
        }
        //Party mode
//        for (int )

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
            case R.id.toggleButtonGpioR1:
                Log.i(TAG, "GPIO R is now " + toggleButton.isChecked());
                gpioDevice.set(gpioDevice.getBank(GPIOR1), gpioDevice.getPin(GPIOR1),
                        ActiveLow, toggleButton.isChecked() ? 0 : 1);
                break;
            case R.id.toggleButtonGpioG1:
                Log.i(TAG, "GPIO G is now " + toggleButton.isChecked());
                gpioDevice.set(gpioDevice.getBank(GPIOG1), gpioDevice.getPin(GPIOG1),
                        ActiveLow, toggleButton.isChecked() ? 0 : 1);
                break;
            case R.id.toggleButtonGpioB1:
                Log.i(TAG, "GPIO B is now " + toggleButton.isChecked());
                gpioDevice.set(gpioDevice.getBank(GPIOB1), gpioDevice.getPin(GPIOB1),
                        ActiveLow, toggleButton.isChecked() ? 0 : 1);
                break;
            case R.id.toggleButtonGpioR2:
                Log.i(TAG, "GPIO R is now " + toggleButton.isChecked());
                gpioDevice.set(gpioDevice.getBank(GPIOR2), gpioDevice.getPin(GPIOR2),
                        ActiveLow, toggleButton.isChecked() ? 0 : 1);
                break;
            case R.id.toggleButtonGpioG2:
                Log.i(TAG, "GPIO G is now " + toggleButton.isChecked());
                gpioDevice.set(gpioDevice.getBank(GPIOG2), gpioDevice.getPin(GPIOG2),
                        ActiveLow, toggleButton.isChecked() ? 0 : 1);
                break;
            case R.id.toggleButtonGpioB2:
                Log.i(TAG, "GPIO B is now " + toggleButton.isChecked());
                gpioDevice.set(gpioDevice.getBank(GPIOB2), gpioDevice.getPin(GPIOB2),
                        ActiveLow, toggleButton.isChecked() ? 0 : 1);
                break;
            case R.id.toggleButtonGpioR3:
                Log.i(TAG, "GPIO R is now " + toggleButton.isChecked());
                gpioDevice.set(gpioDevice.getBank(GPIOR3), gpioDevice.getPin(GPIOR3),
                        ActiveLow, toggleButton.isChecked() ? 0 : 1);
                break;
            case R.id.toggleButtonGpioG3:
                Log.i(TAG, "GPIO G is now " + toggleButton.isChecked());
                gpioDevice.set(gpioDevice.getBank(GPIOG3), gpioDevice.getPin(GPIOG3),
                        ActiveLow, toggleButton.isChecked() ? 0 : 1);
                break;
            case R.id.toggleButtonGpioB3:
                Log.i(TAG, "GPIO B is now " + toggleButton.isChecked());
                gpioDevice.set(gpioDevice.getBank(GPIOB3), gpioDevice.getPin(GPIOB3),
                        ActiveLow, toggleButton.isChecked() ? 0 : 1);
                break;
            case R.id.toggleButtonGpioR4:
                Log.i(TAG, "GPIO R is now " + toggleButton.isChecked());
                gpioDevice.set(gpioDevice.getBank(GPIOR4), gpioDevice.getPin(GPIOR4),
                        ActiveLow, toggleButton.isChecked() ? 0 : 1);
                break;
            case R.id.toggleButtonGpioG4:
                Log.i(TAG, "GPIO G is now " + toggleButton.isChecked());
                gpioDevice.set(gpioDevice.getBank(GPIOG4), gpioDevice.getPin(GPIOG4),
                        ActiveLow, toggleButton.isChecked() ? 0 : 1);
                break;
            case R.id.toggleButtonGpioB4:
                Log.i(TAG, "GPIO B is now " + toggleButton.isChecked());
                gpioDevice.set(gpioDevice.getBank(GPIOB4), gpioDevice.getPin(GPIOB4),
                        ActiveLow, toggleButton.isChecked() ? 0 : 1);
                break;
            case R.id.toggleButtonGpioR5:
                Log.i(TAG, "GPIO R is now " + toggleButton.isChecked());
                gpioDevice.set(gpioDevice.getBank(GPIOR5), gpioDevice.getPin(GPIOR5),
                        ActiveLow, toggleButton.isChecked() ? 0 : 1);
                break;
            case R.id.toggleButtonGpioG5:
                Log.i(TAG, "GPIO G is now " + toggleButton.isChecked());
                gpioDevice.set(gpioDevice.getBank(GPIOG5), gpioDevice.getPin(GPIOG5),
                        ActiveLow, toggleButton.isChecked() ? 0 : 1);
                break;
            case R.id.toggleButtonGpioB5:
                Log.i(TAG, "GPIO B is now " + toggleButton.isChecked());
                gpioDevice.set(gpioDevice.getBank(GPIOB5), gpioDevice.getPin(GPIOB5),
                        ActiveLow, toggleButton.isChecked() ? 0 : 1);
                break;
            case R.id.toggleButtonGpioR6:
                Log.i(TAG, "GPIO R is now " + toggleButton.isChecked());
                gpioDevice.set(gpioDevice.getBank(GPIOR6), gpioDevice.getPin(GPIOR6),
                        ActiveLow, toggleButton.isChecked() ? 0 : 1);
                break;
            case R.id.toggleButtonGpioG6:
                Log.i(TAG, "GPIO G is now " + toggleButton.isChecked());
                gpioDevice.set(gpioDevice.getBank(GPIOG6), gpioDevice.getPin(GPIOG6),
                        ActiveLow, toggleButton.isChecked() ? 0 : 1);
                break;
            case R.id.toggleButtonGpioB6:
                Log.i(TAG, "GPIO B is now " + toggleButton.isChecked());
                gpioDevice.set(gpioDevice.getBank(GPIOB6), gpioDevice.getPin(GPIOB6),
                        ActiveLow, toggleButton.isChecked() ? 0 : 1);
                break;
            case R.id.toggleButtonGpioR7:
                Log.i(TAG, "GPIO R is now " + toggleButton.isChecked());
                gpioDevice.set(gpioDevice.getBank(GPIOR7), gpioDevice.getPin(GPIOR7),
                        ActiveLow, toggleButton.isChecked() ? 0 : 1);
                break;
            case R.id.toggleButtonGpioG7:
                Log.i(TAG, "GPIO G is now " + toggleButton.isChecked());
                gpioDevice.set(gpioDevice.getBank(GPIOG7), gpioDevice.getPin(GPIOG7),
                        ActiveLow, toggleButton.isChecked() ? 0 : 1);
                break;
            case R.id.toggleButtonGpioB7:
                Log.i(TAG, "GPIO B is now " + toggleButton.isChecked());
                gpioDevice.set(gpioDevice.getBank(GPIOB7), gpioDevice.getPin(GPIOB7),
                        ActiveLow, toggleButton.isChecked() ? 0 : 1);
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