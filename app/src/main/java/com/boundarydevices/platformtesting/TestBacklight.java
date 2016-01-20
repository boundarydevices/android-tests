package com.boundarydevices.platformtesting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.System;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class TestBacklight extends Activity {

    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_backlight);

        mContext = this;

        setTitle("Platform Testing - Backlight");

        /* Initialize the result */
        TestResults.addResult(TAG, TestResults.TEST_RESULT_FAILED);

        final Button button_backlight_yes = (Button) findViewById(R.id.button_backlight_yes);
        button_backlight_yes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(TAG, "button_backlight_yes");
                TestResults.addResult(TAG, TestResults.TEST_RESULT_SUCCESS);
                Intent intent = new Intent(mContext, TestNfc.class);
                startActivity(intent);
            }
        });

        final Button button_backlight_no = (Button) findViewById(R.id.button_backlight_no);
        button_backlight_no.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(TAG, "button_backlight_no");
                Intent intent = new Intent(mContext, TestNfc.class);
                startActivity(intent);
            }
        });

        final Button button_backlight_retry = (Button) findViewById(R.id.button_backlight_retry);
        button_backlight_retry.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(TAG, "button_backlight_retry");
                new BacklightTask().execute(0, 50, 100, 150, 200, 255);
            }
        });

        new BacklightTask().execute(0, 50, 100, 150, 200, 255);
    }

    private class BacklightTask extends AsyncTask<Integer, Integer, Long> {
        protected Long doInBackground(Integer... values) {
            int count = values.length;
            for (int i = 0; i < count; i++) {
                publishProgress(values[i]);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return 0L;
        }

        protected void onProgressUpdate(Integer... values) {
            Log.v(TAG, "Set backlight to " + values[0]);
            System.putInt(getContentResolver(), System.SCREEN_BRIGHTNESS, values[0]);
        }
    }
}
