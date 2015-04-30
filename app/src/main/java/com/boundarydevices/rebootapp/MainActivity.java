package com.boundarydevices.rebootapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

    private String TAG = "BoundaryTestApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void doReboot(View view) {
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        Log.v(TAG, "Now rebooting...");
        pm.reboot(null);
    }
}
