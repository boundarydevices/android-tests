package com.example.android.deviceowner;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {

    private static final String TAG = "DeviceOwnerBootReceiver";
    private static final String KIOSK_PACKAGE = "com.boundarydevices.kioskapp";
    private static final String[] APP_PACKAGES = {KIOSK_PACKAGE};
    private DevicePolicyManager mDevicePolicyManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive()");
        mDevicePolicyManager =
                (DevicePolicyManager) context.getSystemService(Activity.DEVICE_POLICY_SERVICE);
        // Set our lock task app
        ComponentName adminName = DeviceOwnerReceiver.getComponentName(context);
        mDevicePolicyManager.setLockTaskPackages(adminName, APP_PACKAGES);
        ActivityOptions options = ActivityOptions.makeBasic();
        options.setLockTaskEnabled(true);

        // Start our kiosk app's main activity with our lock task mode option.
        PackageManager packageManager = context.getPackageManager();
        Intent launchIntent = packageManager.getLaunchIntentForPackage(KIOSK_PACKAGE);
        if (launchIntent != null)
            context.startActivity(launchIntent, options.toBundle());
    }
}