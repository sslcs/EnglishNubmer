package com.reven.englishnumber.ui;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.SparseIntArray;
import com.reven.englishnumber.R;

/**
 * Created by CS on 2014/10/13 18:06.
 * Fragment for sound
 */
public abstract class SoundActivity extends Activity {
    private AudioManager mAudioManager;
    private SoundPool mSoundPool;
    private SparseIntArray mSoundIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAudioManager();
    }

    protected void initAudioManager() {
        mSoundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        mSoundIds = new SparseIntArray(4);
        loadSound(R.raw.wrong, R.raw.wrong);
        loadSound(R.raw.right, R.raw.right);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    }

    protected void loadSound(int id, int rawId) {
        mSoundIds.put(id, mSoundPool.load(this, rawId, 1));
    }

    protected void delSound(int index) {
        mSoundPool.unload(index);
        mSoundIds.delete(index);
    }

    public void playSound(int id) {
        float volume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        mSoundPool.play(mSoundIds.get(id), volume, volume, 0, 0, 1);
    }
}