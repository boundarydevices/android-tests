package com.boundarydevices.platformtesting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class TestTouchscreen extends Activity {

    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private boolean touches[] = {false, false, false, false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_touchscreen);

        mContext = this;

        setTitle("Platform Testing - Touchscreen");

        /* Initialize the result */
        TestResults.addResult(TAG, TestResults.TEST_RESULT_FAILED);

        final Button button_touch_1 = (Button) findViewById(R.id.button_touch_1);
        button_touch_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(TAG, "button_touch_1");
                touches[0] = true;
                checkButtons();
            }
        });
        final Button button_touch_2 = (Button) findViewById(R.id.button_touch_2);
        button_touch_2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(TAG, "button_touch_2");
                touches[1] = true;
                checkButtons();
            }
        });
        final Button button_touch_3 = (Button) findViewById(R.id.button_touch_3);
        button_touch_3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(TAG, "button_touch_3");
                touches[2] = true;
                checkButtons();
            }
        });
        final Button button_touch_4 = (Button) findViewById(R.id.button_touch_4);
        button_touch_4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(TAG, "button_touch_4");
                touches[3] = true;
                checkButtons();
            }
        });
        final Button button_touch_skip = (Button) findViewById(R.id.button_touch_skip);
        button_touch_skip.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(TAG, "button_touch_skip");
                TestResults.addResult(TAG, TestResults.TEST_RESULT_SKIPPED);
                Intent intent = new Intent(mContext, TestAccelerometer.class);
                startActivity(intent);
            }
        });
    }

    void checkButtons() {
        for (int i = 0; i < touches.length; i++) {
            if (touches[i] == false)
                return;
        }
        Log.v(TAG, "All buttons touched, next test");
        TestResults.addResult(TAG, TestResults.TEST_RESULT_SUCCESS);
        Intent intent = new Intent(mContext, TestAccelerometer.class);
        startActivity(intent);
    }
}
