package com.boundarydevices.platformtesting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestEthernet extends Activity {

    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private ProgressBar mEthProgress;
    private TextView mEthText;
    private DownloadFilesTask mDlTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_ethernet);

        mContext = this;

        setTitle("Platform Testing - Ethernet");

        /* Initialize the result */
        TestResults.addResult(TAG, TestResults.TEST_RESULT_FAILED);

        mEthText = (TextView) findViewById(R.id.eth_text);
        mEthText.setText("Downloading a 10MB file...");
        mEthProgress = (ProgressBar) findViewById(R.id.eth_progress);

        final Button button_eth_skip = (Button) findViewById(R.id.button_eth_skip);
        button_eth_skip.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(TAG, "button_eth_skip");
                mDlTask.cancel(true);
                Intent intent = new Intent(mContext, TestAudio.class);
                startActivity(intent);
            }
        });

        /* Download a file a roughly 10MB */
        mDlTask = new DownloadFilesTask();
        mDlTask.execute("https://storage.googleapis.com/boundarydevices.com/bdsl_ethernet_debugging.avi");
    }

    private class DownloadFilesTask extends AsyncTask<String, Integer, String> {
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                output = new FileOutputStream(mContext.getFilesDir() + "/file.temp");

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
            Log.v(TAG, "Ethernet download progress: " + progress[0].toString());
            mEthProgress.setProgress(progress[0]);
        }

        protected void onPostExecute(String result) {
            if (result == null) {
                Log.v(TAG, "Download complete!");
                mEthText.setText("Download complete!");
                TestResults.addResult(TAG, TestResults.TEST_RESULT_SUCCESS);
            } else {
                Log.v(TAG, "Download failed: " + result);
                mEthText.setText("Download failed: " + result);
            }
            Intent intent = new Intent(mContext, TestAudio.class);
            startActivity(intent);
        }
    }
}
