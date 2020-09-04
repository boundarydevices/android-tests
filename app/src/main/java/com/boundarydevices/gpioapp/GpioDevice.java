package com.boundarydevices.gpioapp;

import android.util.Log;

public class GpioDevice {
    private static final String TAG = "GpioDevice";
    private static final boolean DEBUG = false;
    static {
        System.loadLibrary("gpio-android-jni");
    }
    private GpioDeviceListener listener = null;
    public interface GpioDeviceListener {
        public void onGpioDeviceEvent(int gpio, int state);
    }
    public GpioDevice(GpioDeviceListener listener) {
        this.listener = listener;
    }
    public native int get(int bank, int pin, boolean active_low);
    public native int set(int bank, int pin, boolean active_low, int value);
    public native int waitPinEvent(int bank, int pin, int timeout_s);
    public int get(int bank, int pin) {
        return get(bank, pin, false);
    }
    public int set(int bank, int pin, int value) {
        return set(bank, pin, false, value);
    }
    public int getBank(int gpioNumber) {
        int bank = 0;
        while ((gpioNumber -= 32) > 0)
            bank++;
        return bank;
    }
    public int getPin(int gpioNumber) {
        return gpioNumber % 32;
    }
    public void subscribePinEvent(int gpio, int timeout_s) {
        Log.d(TAG, "subscribePinEvent " + gpio + " timeout " + timeout_s);
        GpioDeviceTask task = new GpioDeviceTask(gpio, timeout_s);
        task.start();
    }
    class GpioDeviceTask extends Thread {
        int gpio;
        int timeout_s;
        GpioDeviceTask(int gpio, int timeout_s) {
            this.gpio = gpio;
            this.timeout_s = timeout_s;
        }
        @Override
        public void run() {
            int ret = 0;
            while (ret >= 0) {
                ret = waitPinEvent(getBank(gpio), getPin(gpio), timeout_s);
                if (ret > 0) {
                    if (listener != null) {
                        listener.onGpioDeviceEvent(gpio, get(getBank(gpio), getPin(gpio)));
                    } else {
                        Log.e(TAG, "No listener!");
                    }
                } else if (ret == 0) {
                    if (DEBUG) Log.v(TAG, "Timeout for GPIO " + gpio);
                } else {
                    Log.e(TAG, "Error for GPIO " + gpio + " ret " + ret);
                }
            }
        }
    }
}
