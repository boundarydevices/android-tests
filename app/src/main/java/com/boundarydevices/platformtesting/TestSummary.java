package com.boundarydevices.platformtesting;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class TestSummary extends Activity {

    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private TextView mSummaryText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_summary);

        mContext = this;

        setTitle("Platform Testing - Summary");

        mSummaryText = (TextView) findViewById(R.id.summary_text);
        mSummaryText.setText("Test results:\n\n");

        Map<String, String> map = TestResults.getResults();
        Set keys = map.keySet();
        for (Iterator i = keys.iterator(); i.hasNext();) {
            String key = (String) i.next();
            String value = (String) map.get(key);
            mSummaryText.append(key + " = " + value + "\n");
        }
    }
}
