package com.boundarydevices.platformtesting;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class TestResults {

    static final String TAG = "TestResults";
    static final String TEST_RESULT_SUCCESS = "success";
    static final String TEST_RESULT_FAILED = "failed";
    static Map<String, String> mResults = new HashMap<String,String>();

    static void addResult(String name, String result) {
        /* Check if result for this test doesn't already exist */
        if (mResults.containsKey(name)) {
            Log.v(TAG, "Previous results for " + name + " was " + mResults.get(name));
        }
        Log.v(TAG, "Set result for " + name + " to " + result);
        mResults.put(name, result);
    }

    static int getNbResults() {
        return mResults.size();
    }

    static Map<String, String> getResults() {
        return mResults;
    }
}
