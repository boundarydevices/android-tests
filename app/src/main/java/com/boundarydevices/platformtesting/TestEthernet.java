package com.boundarydevices.platformtesting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TestEthernet extends Activity {

    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private TextView mEthText;
    private ConnectivityManager mConnMgr;
    private Button mEthYes;
    private Button mEthNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_ethernet);

        mContext = this;

        setTitle("Platform Testing - Ethernet");

        /* Initialize the result */
        TestResults.addResult(TAG, TestResults.TEST_RESULT_FAILED);

        mConnMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        mEthText = (TextView) findViewById(R.id.eth_text);
        mEthText.setText("Please connect the platform to the Network...\n");

        mEthYes = (Button) findViewById(R.id.button_eth_yes);
        mEthYes.setVisibility(View.INVISIBLE);
        mEthYes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(TAG, "button_eth_yes");
                TestResults.addResult(TAG, TestResults.TEST_RESULT_SUCCESS);
                Intent intent = new Intent(mContext, TestAudio.class);
                startActivity(intent);
            }
        });

        mEthNo = (Button) findViewById(R.id.button_eth_no);
        mEthNo.setVisibility(View.INVISIBLE);
        mEthNo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(TAG, "button_eth_no");
                Intent intent = new Intent(mContext, TestAudio.class);
                startActivity(intent);
            }
        });

        final Button button_eth_skip = (Button) findViewById(R.id.button_eth_skip);
        button_eth_skip.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(TAG, "button_eth_skip");
                Intent intent = new Intent(mContext, TestAudio.class);
                startActivity(intent);
            }
        });

        new WaitingTask().execute();
    }

    private class WaitingTask extends AsyncTask<Void, Integer, Void> {
        protected Void doInBackground(Void... values) {
            for (;;) {
                if (mConnMgr.getActiveNetworkInfo() != null)
                    break;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        protected void onProgressUpdate(Integer... values) {
        }

        protected void onPostExecute(Void result) {
            mEthText.setText("Ethernet settings:\n\n");
            Network network = mConnMgr.getAllNetworks()[0];
            mEthText.append("MAC:  " + mConnMgr.getNetworkInfo(network).getExtraInfo().toString() + "\n");
            mEthText.append("IP:  " + mConnMgr.getLinkProperties(network).getLinkAddresses().toString() + "\n");
            mEthText.append("DNS:  " + mConnMgr.getLinkProperties(network).getDnsServers().toString() + "\n");
            mEthText.append("Routes:  " + mConnMgr.getLinkProperties(network).getRoutes().toString() + "\n");
            mEthText.append("Status:  " + mConnMgr.getNetworkInfo(network).getState().toString() + "\n");
            mEthText.append("\nIs this correct?\n");

            mEthYes.setVisibility(View.VISIBLE);
            mEthNo.setVisibility(View.VISIBLE);
        }
    }
}
