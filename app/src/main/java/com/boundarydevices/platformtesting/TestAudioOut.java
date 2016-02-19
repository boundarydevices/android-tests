package com.boundarydevices.platformtesting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TestAudioOut extends Activity {

    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private TextView mAudioOutText;
    private boolean mRighTest = false;
    private Button mAudioOutNo;
    private Button mAudioOutYes;
    private Button mAudioOutSkip;
    private AudioTrack mTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_audio_out);

        mContext = this;

        setTitle("Platform Testing - Audio Out only");

        /* Initialize the result */
        TestResults.addResult(TAG, TestResults.TEST_RESULT_FAILED);

        mAudioOutText = (TextView) findViewById(R.id.audio_out_text);
        mAudioOutText.setText("Is a tone playing on the audio left output?\n");

        mAudioOutSkip = (Button) findViewById(R.id.button_audio_out_skip);
        mAudioOutSkip.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mTrack.stop();
                mTrack.release();
                Log.v(TAG, "button_audio_skip");
                TestResults.addResult(TAG, TestResults.TEST_RESULT_SKIPPED);
                Intent intent = new Intent(mContext, TestUSB.class);
                startActivity(intent);
            }
        });

        mAudioOutYes = (Button) findViewById(R.id.button_audio_out_yes);
        mAudioOutYes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(TAG, "button_audio_yes");
                mTrack.stop();
                mTrack.release();
                if (mRighTest == false) {
                    mRighTest = true;
                    mTrack = generateTone(2000.0, 10, false, true);
                    mTrack.play();
                    mAudioOutText.setText("Is a tone playing on the audio right output?\n");
                } else {
                    TestResults.addResult(TAG, TestResults.TEST_RESULT_SUCCESS);
                    Intent intent = new Intent(mContext, TestUSB.class);
                    startActivity(intent);
                }
            }
        });

        mAudioOutNo = (Button) findViewById(R.id.button_audio_out_no);
        mAudioOutNo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(TAG, "button_audio_no");
                mTrack.stop();
                mTrack.release();
                Intent intent = new Intent(mContext, TestUSB.class);
                startActivity(intent);
            }
        });

        mTrack = generateTone(2000.0, 10, true, false);
        mTrack.play();
    }

    private AudioTrack generateTone(double freqHz, int duration, boolean left, boolean right)
    {
        int count = (int)(44100.0 * 2.0 * duration) & ~1;
        short[] samples = new short[count];
        for(int i = 0; i < count; i += 2){
            short sample = (short)(Math.sin(2 * Math.PI * i / (44100.0 / freqHz)) * 0x7FFF);
            if (left)
                samples[i + 0] = sample;
            if (right)
                samples[i + 1] = sample;
        }
        AudioTrack track = new AudioTrack(AudioManager.STREAM_MUSIC, 44100,
                AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT,
                count * (Short.SIZE / 8), AudioTrack.MODE_STATIC);

        track.write(samples, 0, count);
        // Turning the volume down a little bit...
        track.setVolume(0.7f);
        return track;
    }
}
