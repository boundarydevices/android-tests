package com.boundarydevices.platformtesting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TestAudio extends Activity implements Recorder.OnStateChangedListener {

    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    Recorder mRecorder;
    private ProgressBar mAudioProgress;
    private TextView mAudioText;
    private Button mAudioNo;
    private Button mAudioYes;

    static final int BITRATE_3GPP = 12200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_audio);

        mContext = this;

        setTitle("Platform Testing - Audio");

        /* Initialize the result */
        TestResults.addResult(TAG, TestResults.TEST_RESULT_FAILED);

        mRecorder = new Recorder();
        mRecorder.setOnStateChangedListener(this);

        mAudioText = (TextView) findViewById(R.id.audio_text);
        mAudioText.setText("This test will record the microphone during 5s and then play back the recorded track\n");
        mAudioText.append("Click on Start when ready to record");

        mAudioProgress = (ProgressBar) findViewById(R.id.audio_progress);
        mAudioProgress.setVisibility(View.INVISIBLE);

        final Button button_audio_start = (Button) findViewById(R.id.button_audio_start);
        button_audio_start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(TAG, "button_audio_start");
                stopAudioPlayback();
                mAudioProgress.setVisibility(View.VISIBLE);
                mAudioText.setText("Recording for 5 seconds...");
                mRecorder.startRecording(MediaRecorder.OutputFormat.THREE_GPP, ".3gpp", mContext);
                new RecordingTask().execute();
            }
        });

        mAudioYes = (Button) findViewById(R.id.button_audio_yes);
        mAudioYes.setVisibility(View.INVISIBLE);
        mAudioYes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(TAG, "button_audio_yes");
                TestResults.addResult(TAG, TestResults.TEST_RESULT_SUCCESS);
                Intent intent = new Intent(mContext, TestUSB.class);
                startActivity(intent);
            }
        });

        mAudioNo = (Button) findViewById(R.id.button_audio_no);
        mAudioNo.setVisibility(View.INVISIBLE);
        mAudioNo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(TAG, "button_audio_no");
                Intent intent = new Intent(mContext, TestUSB.class);
                startActivity(intent);
            }
        });
    }

    private class RecordingTask extends AsyncTask<Void, Integer, Void> {
        protected Void doInBackground(Void... values) {
            int count = 5;
            for (int i = 0; i <= count; i++) {
                publishProgress(i);
                if (i == count)
                    return null;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        protected void onProgressUpdate(Integer... values) {
            mAudioProgress.setProgress(20 * values[0]);
        }

        protected void onPostExecute(Void result) {
            mRecorder.stopRecording();
            mAudioText.setText("Playback the recorded track...");
            new PlaybackTask().execute();
            mRecorder.startPlayback();
        }
    }

    private class PlaybackTask extends AsyncTask<Void, Integer, Void> {
        protected Void doInBackground(Void... values) {
            int count = 5;
            for (int i = 0; i <= count; i++) {
                publishProgress(i);
                if (i == count)
                    return null;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        protected void onProgressUpdate(Integer... values) {
            mAudioProgress.setProgress(20 * values[0]);
        }

        protected void onPostExecute(Void result) {
            mAudioProgress.setVisibility(View.INVISIBLE);
            mAudioText.setText("Did the audio test succeed?");
            mAudioYes.setVisibility(View.VISIBLE);
            mAudioNo.setVisibility(View.VISIBLE);
        }
    }
    /*
     * Make sure we're not recording music playing in the background, ask
     * the MediaPlaybackService to pause playback.
     */
    private void stopAudioPlayback() {
        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
    }

    /*
     * Called when Recorder changed it's state.
     */
    public void onStateChanged(int state) {
        Log.v(TAG, "onStateChanged " + state);
    }

    /*
     * Called when MediaPlayer encounters an error.
     */
    public void onError(int error) {
        String message = null;
        switch (error) {
            case Recorder.SDCARD_ACCESS_ERROR:
                Log.e(TAG, "SDCARD_ACCESS_ERROR");
                break;
            case Recorder.IN_CALL_RECORD_ERROR:
                Log.e(TAG, "IN_CALL_RECORD_ERROR");
            case Recorder.INTERNAL_ERROR:
                Log.e(TAG, "INTERNAL_ERROR");
                break;
            default:
                Log.e(TAG, "onError " + error);
        }
    }
}
