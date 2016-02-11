package com.boundarydevices.platformtesting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        setTitle("Platform Testing - Introduction");

        final TextView textBuild= (TextView) findViewById(R.id.build_id);
        textBuild.append("Board: " + Build.SERIAL + "\n");
        textBuild.append("Build: " + Build.FINGERPRINT);

        final Button button = (Button) findViewById(R.id.button_intro);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(TAG, "Starting the test:\n" + textBuild.getText());
                Intent intent = new Intent(mContext, TouchConfiguration.class);
                startActivity(intent);
            }
        });
    }
}
