package com.boundarydevices.suapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.DataOutputStream;
import java.io.IOException;

public class MainActivity extends Activity {

    private String TAG = "BoundarySuApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void doSu(View view) {
        /* The following snippet is inspired from the following blog post:
         * http://www.stealthcopter.com/blog/2010/01/android-requesting-root-access-in-your-app/
         */
        Process p;
        try {
            // Preform su to get root privledges
            p = Runtime.getRuntime().exec("su");

            // Attempt to write a file to a root-only
            DataOutputStream os = new DataOutputStream(p.getOutputStream());
            os.writeBytes("echo \"Yeah I'm root!\" > /cache/suapp.txt\n");

            // Close the terminal
            os.writeBytes("exit\n");
            os.flush();
            try {
                p.waitFor();
                if (p.exitValue() == 0) {
                    Log.v(TAG, "root => exit " + p.exitValue());
                }
                else {
                    Log.v(TAG, "not root => exit " + p.exitValue());
                }
            } catch (InterruptedException e) {
                Log.v(TAG, "not root");
            }
        } catch (IOException e) {
            Log.v(TAG, "not root");
        }
    }
}
