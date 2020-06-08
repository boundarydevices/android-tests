package com.boundarydevices.gpioapp;

public class GpioDevice {
    static {
        System.loadLibrary("gpio-android-jni");
    }
    public native int get(int bank, int pin, boolean active_low);
    public native int set(int bank, int pin, boolean active_low, int value);
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
}
