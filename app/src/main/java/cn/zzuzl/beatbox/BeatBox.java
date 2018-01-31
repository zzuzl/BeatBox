package cn.zzuzl.beatbox;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanglei53 on 2018/1/30.
 */

public class BeatBox {
    private static final String TAG = BeatBox.class.getName();
    private static final String FOLDER = "sample_sounds";
    private static final int MAX_SOUNDS = 2;

    private AssetManager mAssetManager;
    private final List<Sound> mSounds = new ArrayList<Sound>();
    private final SoundPool mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);

    public BeatBox(Context context) {
        mAssetManager = context.getAssets();
        loadSounds();
    }

    private void loadSounds() {
        String[] soundNames = null;

        try {
            soundNames = mAssetManager.list(FOLDER);
            Log.i(TAG, "files:" + soundNames.length);
        } catch (Exception e) {
            Log.e(TAG, "loadSounds:", e);
        }

        if (soundNames != null) {
            for (String name : soundNames) {
                Sound sound = new Sound(FOLDER + "/" + name);
                load(sound);
                mSounds.add(sound);
            }
        }
    }

    public List<Sound> getSounds() {
        return mSounds;
    }

    /**
     * 加载音频
     *
     * @param sound
     */
    private void load(Sound sound) {
        try {
            AssetFileDescriptor openFd = mAssetManager.openFd(sound.getPath());
            int load = mSoundPool.load(openFd, 1);
            sound.setLoadId(load);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 播放音频
     *
     * @param sound
     */
    public void play(Sound sound) {
        mSoundPool.play(sound.getLoadId(), 1.0f, 1.0f, 1, 0, 1.0f);
    }

    /**
     * 释放音频
     */
    public void release() {
        mSoundPool.release();
    }
}
