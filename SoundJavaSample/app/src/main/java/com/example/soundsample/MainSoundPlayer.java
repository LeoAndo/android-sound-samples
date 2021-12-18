package com.example.soundsample;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

/**
 * MainActivityで使用するサウンドクラス.
 */
public class MainSoundPlayer implements DefaultLifecycleObserver {
    private final SoundPool soundPool;

    private final int curtainSoundId;
    private int curtainStreamId;
    private final int windowSoundId;
    private int windowStreamId;

    private static final String LOG_TAG = MainSoundPlayer.class.getSimpleName();
    private static final float LEFT_VOLUME_VALUE = 1.0f; // left volume value (range = 0.0 to 1.0)
    private static final float RIGHT_VOLUME_VALUE = 1.0f;// right volume value (range = 0.0 to 1.0)
    private static final int SOUND_LOOP_MODE_NO_LOOP = 0; // loop mode (0 = no loop, -1 = loop forever)
    private static final float SOUND_PLAY_BACK_RATE = 1.0f; // playback rate (1.0 = normal playback, range 0.5 to 2.0)
    private static final int PRIORITY_1 = 1;

    public MainSoundPlayer(Context context) {
        final AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        final int maxStreams = 2; // ストリーム数
        soundPool = new SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                .setMaxStreams(maxStreams)
                .build();
        curtainSoundId = soundPool.load(context, R.raw.curtain1, PRIORITY_1);
        windowSoundId = soundPool.load(context, R.raw.small_window, PRIORITY_1);
    }

    // 効果音を再生する
    public void playCurtainSound() {
        curtainStreamId = soundPool.play(
                curtainSoundId, LEFT_VOLUME_VALUE, RIGHT_VOLUME_VALUE,
                PRIORITY_1, SOUND_LOOP_MODE_NO_LOOP, SOUND_PLAY_BACK_RATE);
    }

    public void playWindowSound() {
        windowStreamId = soundPool.play(
                windowSoundId, LEFT_VOLUME_VALUE, RIGHT_VOLUME_VALUE,
                PRIORITY_1, SOUND_LOOP_MODE_NO_LOOP, SOUND_PLAY_BACK_RATE);
    }

    // 効果音を停止する
    public void stopCurtainSound() {
        soundPool.stop(curtainStreamId);
    }

    public void stopWindowSound() {
        soundPool.stop(windowStreamId);
    }

    // 画面表示時に呼ばれる
    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        Log.d(LOG_TAG, "call autoResume");
        if (soundPool == null) return;
        soundPool.autoResume(); // soundPool.autoPause()したときにアクティブだったすべての音声ファイルを再生する.
    }

    // 画面非表示時に呼ばれる
    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        Log.d(LOG_TAG, "call autoPause");
        if (soundPool == null) return;
        soundPool.autoPause(); // 全ての再生中の音声ファイルを停止する.
    }

    // 画面終了時に呼ばれる
    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        Log.d(LOG_TAG, "call release soundPool: " + soundPool);
        if (soundPool == null) return;
        soundPool.release(); // SoundPoolによって使用されているすべてのメモリとネイティブリソースを解放する.
    }
}
