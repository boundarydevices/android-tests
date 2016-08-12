package com.boundarydevices.i2capp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

    /* ISL1208 definitions */
    private final int I2C_RTC_BUS = 0;
    private final int I2C_RTC_ADDR = 0x6F;
    private final int I2C_RTC_MONTH = 0x4;

    private TextView mTextView;
    private int mFileDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int result;
        byte data[] = new byte[4];

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView)findViewById(R.id.MainTextView);

        /* Create i2c device object */
        I2CDevice device;
        device = new I2CDevice();

        /* Open ISL1208 device */
        mFileDesc = device.open(I2C_RTC_BUS, I2C_RTC_ADDR);
        mTextView.setText("Opening device on bus " + I2C_RTC_BUS + " @0x" +
                Integer.toHexString(I2C_RTC_ADDR) + "...\n");

        /* Read the byte at offset 0x4 which is the month */
        result = device.readByte(mFileDesc, I2C_RTC_MONTH);
        if (result < 0) {
            mTextView.append("Error read: " + result);
            return;
        }
        mTextView.append("Received month value: " + result + "\n");

        mTextView.append("\nReading 4bytes at once...\n");
        result = device.readBytesArray(mFileDesc, I2C_RTC_MONTH, data, data.length);
        if (result < 0) {
            mTextView.append("Error read: " + result);
            return;
        }
        mTextView.append("Received: " + result + "bytes");
        for (int i = 0; i < data.length; i++)
            mTextView.append(" " + Integer.toHexString(data[i]));

        /* Closing the device file descriptor */
        device.close(mFileDesc);
    }
}
