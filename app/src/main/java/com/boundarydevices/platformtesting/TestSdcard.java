package com.boundarydevices.platformtesting;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

public class TestSdcard extends Activity {

    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private BroadcastReceiver mSDCardMountEventReceiver = null;
    private boolean mSdcardMounted = false;
    private TextView mSdcardText;
    private Button mSdcardYes;
    private Button mSdcardNo;
    private WaitingTask mWaitingTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sdcard);

        mContext = this;

        setTitle("Platform Testing - SD Card");

        /* Initialize the result */
        TestResults.addResult(TAG, TestResults.TEST_RESULT_FAILED);

        mSdcardText = (TextView) findViewById(R.id.sdcard_text);
        mSdcardText.setText("Please insert a SD Card");

        final Button button_sdcard_skip = (Button) findViewById(R.id.button_sdcard_skip);
        button_sdcard_skip.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(TAG, "button_sdcard_skip");
                Intent intent = new Intent(mContext, TestSummary.class);
                startActivity(intent);
            }
        });

        mSdcardYes = (Button) findViewById(R.id.button_sdcard_yes);
        mSdcardYes.setVisibility(View.INVISIBLE);
        mSdcardYes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(TAG, "button_sdcard_yes");
                TestResults.addResult(TAG, TestResults.TEST_RESULT_SUCCESS);
                Intent intent = new Intent(mContext, TestSummary.class);
                startActivity(intent);
            }
        });

        mSdcardNo = (Button) findViewById(R.id.button_sdcard_no);
        mSdcardNo.setVisibility(View.INVISIBLE);
        mSdcardNo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(TAG, "button_sdcard_no");
                Intent intent = new Intent(mContext, TestSummary.class);
                startActivity(intent);
            }
        });

        mWaitingTask = new WaitingTask();
    }

    private class WaitingTask extends AsyncTask<Void, Integer, Void> {
        protected Void doInBackground(Void... values) {
            for (;;) {
                if ((mSdcardMounted == true) || isCancelled())
                    break;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        protected void onProgressUpdate(Integer... values) {
        }

        protected void onPostExecute(Void result) {
            mSdcardText.setText("Found SD card with following content\n");
            File f = new File("/storage/extsd");
            if (f.exists()) {
                File file[] = f.listFiles();
                Log.d(TAG, "Size: " + file.length);
                for (int i = 0; i < file.length; i++) {
                    Log.d(TAG, "FileName:" + file[i].getName());
                    mSdcardText.append(file[i].getName() + "\n");
                    /* In order not to have the display filled with name */
                    if (i >= 10) {
                        mSdcardText.append("... (skip the other files)\n");
                    }
                }
                mSdcardText.append("\nIs it correct?");
                mSdcardYes.setVisibility(View.VISIBLE);
                mSdcardNo.setVisibility(View.VISIBLE);
            } else {
                Log.e(TAG, "/storage/extsd/ doesn't exist??");
            }
        }
    }

    @Override
    public void onResume() {
        registerExternalStorageListener();
        mWaitingTask.execute();
        super.onResume();
    }

    @Override
    public void onPause() {
        if (mSDCardMountEventReceiver != null) {
            unregisterReceiver(mSDCardMountEventReceiver);
            mSDCardMountEventReceiver = null;
        }
        mWaitingTask.cancel(true);
        super.onPause();
    }

    private void registerExternalStorageListener() {
        if (mSDCardMountEventReceiver == null) {
            mSDCardMountEventReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if (action.equals(Intent.ACTION_MEDIA_EJECT)) {
                        Log.v(TAG, "ACTION_MEDIA_EJECT (" + intent.getDataString() + ")");
                    } else if (action.equals(Intent.ACTION_MEDIA_MOUNTED)) {
                        Log.v(TAG, "ACTION_MEDIA_MOUNTED (" + intent.getDataString() + ")");
                        if (intent.getDataString().equals("file:///storage/extsd"))
                            mSdcardMounted = true;
                    }
                }
            };
            IntentFilter iFilter = new IntentFilter();
            iFilter.addAction(Intent.ACTION_MEDIA_EJECT);
            iFilter.addAction(Intent.ACTION_MEDIA_MOUNTED);
            iFilter.addDataScheme("file");
            registerReceiver(mSDCardMountEventReceiver, iFilter);
        }
    }
}
