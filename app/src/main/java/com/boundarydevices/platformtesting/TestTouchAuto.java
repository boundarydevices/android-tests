package com.boundarydevices.platformtesting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemProperties;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TestTouchAuto extends Activity {

    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private TextView mTouchAutoText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_touch_auto);

        mContext = this;

        setTitle("Platform Testing - Automatic Touch Test");

        /* Initialize the result */
        TestResults.addResult(TAG, TestResults.TEST_RESULT_FAILED);

        mTouchAutoText = (TextView) findViewById(R.id.touch_auto_text);
        mTouchAutoText.setText("Click on start button when ready.");

        // Making sure the su daemon is running
        SystemProperties.set("persist.sys.root_access", "3");

        final Button touch_auto_skip = (Button) findViewById(R.id.touch_auto_skip);
        touch_auto_skip.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(TAG, "touch_auto_skip");
                TestResults.addResult(TAG, TestResults.TEST_RESULT_SKIPPED);
                Intent intent = new Intent(mContext, TestAccelerometer.class);
                startActivity(intent);
            }
        });

        final Button touch_auto_start = (Button) findViewById(R.id.touch_auto_start);
        touch_auto_start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(TAG, "touch_auto_start");
                mTouchAutoText.setText("Starting mxt-app test...");
                try {
                    // Preform su to get root privileges
                    Process p = Runtime.getRuntime().exec("su");

                    // Get stdin/stdout of that new process
                    DataOutputStream osOut = new DataOutputStream(p.getOutputStream());
                    DataInputStream osIn = new DataInputStream(p.getInputStream());

                    // Execute command
                    osOut.writeBytes("mxt-app -t 2>&1\n");
                    osOut.flush();

                    // give the tool a second to execute
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Read the return value
                    byte[] buffer = new byte[64];
                    int read;
                    String out = new String();
                    while(true) {
                        read = osIn.read(buffer);
                        out += new String(buffer, 0, read);
                        if (read < 64) {
                            break;
                        }
                    }
                    Log.v(TAG, "mxt-app output:\n" + out);
                    mTouchAutoText.append("\n\n" + out);

                    // Close the terminal
                    osOut.writeBytes("exit\n");
                    osOut.flush();

                    try {
                        p.waitFor();
                        if (p.exitValue() == 0) {
                            Log.v(TAG, "root => exit " + p.exitValue());
                        } else {
                            Log.v(TAG, "not root => exit " + p.exitValue());
                        }
                    } catch (InterruptedException e) {
                        Log.v(TAG, "not root");
                    }
                } catch (IOException e) {
                    Log.v(TAG, "not root");
                }
                if (mTouchAutoText.getText().toString().contains("PASS")) {
                    TestResults.addResult(TAG, TestResults.TEST_RESULT_SUCCESS);
                    mTouchAutoText.append("\n=> PASSED");
                } else {
                    mTouchAutoText.append("\n=> FAILED");
                }

                Intent intent = new Intent(mContext, TestAccelerometer.class);
                startActivity(intent);
            }
        });
    }
}
