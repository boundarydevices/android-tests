package com.boundarydevices.platformtesting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemProperties;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TouchConfiguration extends Activity {

    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private Button mButtonSkip;
    private Button mButtonStart;
    private EditText mFwPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_configuration);

        mContext = this;

        setTitle("Platform Testing - Touch screen setup");

        mFwPath = (EditText) findViewById(R.id.touch_conf_edittext);

        mButtonSkip = (Button) findViewById(R.id.touch_conf_skip);
        mButtonSkip.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(TAG, "Go to first test...");
                Intent intent = new Intent(mContext, TestTouchscreen.class);
                startActivity(intent);
            }
        });

        mButtonStart = (Button) findViewById(R.id.touch_conf_start);
        mButtonStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(TAG, "Loading touch configuration from " + mFwPath.getText().toString());
                SystemProperties.set("sys.touch.fw.path", mFwPath.getText().toString());
                /* Tell the init process to (re)load the touch firmware */
                SystemProperties.set("sys.touch.fw.reload", "on");
                mButtonSkip.setText("Next");
            }
        });
    }
}
