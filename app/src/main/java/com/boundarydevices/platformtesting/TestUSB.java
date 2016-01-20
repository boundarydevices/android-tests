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

public class TestUSB extends Activity {

    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private BroadcastReceiver mSDCardMountEventReceiver = null;
    private boolean mUsbMounted = false;
    private TextView mUsbText;
    private Button mUsbYes;
    private Button mUsbNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_usb);

        mContext = this;

        setTitle("Platform Testing - USB");

        /* Initialize the result */
        TestResults.addResult(TAG, TestResults.TEST_RESULT_FAILED);

        mUsbText = (TextView) findViewById(R.id.usb_text);
        mUsbText.setText("Please insert a FAT32 formatted USB drive");

        registerExternalStorageListener();

        final Button button_usb_skip = (Button) findViewById(R.id.button_usb_skip);
        button_usb_skip.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(TAG, "button_usb_skip");
                Intent intent = new Intent(mContext, TestSdcard.class);
                startActivity(intent);
            }
        });

        mUsbYes = (Button) findViewById(R.id.button_usb_yes);
        mUsbYes.setVisibility(View.INVISIBLE);
        mUsbYes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(TAG, "button_usb_yes");
                TestResults.addResult(TAG, TestResults.TEST_RESULT_SUCCESS);
                Intent intent = new Intent(mContext, TestSdcard.class);
                startActivity(intent);
            }
        });

        mUsbNo = (Button) findViewById(R.id.button_usb_no);
        mUsbNo.setVisibility(View.INVISIBLE);
        mUsbNo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(TAG, "button_usb_no");
                Intent intent = new Intent(mContext, TestSdcard.class);
                startActivity(intent);
            }
        });

        new WaitingTask().execute();
    }

    private class WaitingTask extends AsyncTask<Void, Integer, Void> {
        protected Void doInBackground(Void... values) {
            for (;;) {
                if (mUsbMounted == true)
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
            mUsbText.setText("Found USB drive with following content\n");
            File f = new File("/storage/udisk");
            if (f.exists()) {
                File file[] = f.listFiles();
                Log.d(TAG, "Size: " + file.length);
                for (int i = 0; i < file.length; i++) {
                    Log.d(TAG, "FileName:" + file[i].getName());
                    mUsbText.append(file[i].getName() + "\n");
                    /* In order not to have the display filled with name */
                    if (i >= 10) {
                        mUsbText.append("... (skip the other files)\n");
                    }
                }
                mUsbText.append("\nIs it correct?");
                mUsbYes.setVisibility(View.VISIBLE);
                mUsbNo.setVisibility(View.VISIBLE);
            } else {
                Log.e(TAG, "/storage/udisk/ doesn't exist??");
            }
        }
    }

    @Override
    public void onDestroy() {
        if (mSDCardMountEventReceiver != null) {
            unregisterReceiver(mSDCardMountEventReceiver);
            mSDCardMountEventReceiver = null;
        }
        super.onDestroy();
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
                        if (intent.getDataString().equals("file:///storage/udisk"))
                            mUsbMounted = true;
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
