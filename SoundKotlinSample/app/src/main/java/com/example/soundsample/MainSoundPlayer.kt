package com.example.soundsample

import android.content.Context
import androidx.lifecycle.DefaultLifecycleObserver
import android.media.SoundPool
import androidx.lifecycle.LifecycleOwner
import android.media.AudioAttributes
import android.util.Log

/**
 * MainActivityで使用するサウンドクラス.
 */
class MainSoundPlayer(context: Context?) : DefaultLifecycleObserver {
    private val soundPool: SoundPool?
    private val curtainSoundId: Int
    private var curtainStreamId = 0
    private val windowSoundId: Int
    private var windowStreamId = 0

    // 音声を再生する
    fun playCurtainSound() {
        curtainStreamId = soundPool!!.play(
            curtainSoundId, LEFT_VOLUME_VALUE, RIGHT_VOLUME_VALUE,
            PRIORITY_1, SOUND_LOOP_MODE_NO_LOOP, SOUND_PLAY_BACK_RATE
        )
    }

    fun playWindowSound() {
        windowStreamId = soundPool!!.play(
            windowSoundId, LEFT_VOLUME_VALUE, RIGHT_VOLUME_VALUE,
            PRIORITY_1, SOUND_LOOP_MODE_NO_LOOP, SOUND_PLAY_BACK_RATE
        )
    }

    // 音声を停止する
    fun stopCurtainSound() {
        soundPool!!.stop(curtainStreamId)
    }

    fun stopWindowSound() {
        soundPool!!.stop(windowStreamId)
    }

    // 画面表示時に呼ばれる
    override fun onResume(owner: LifecycleOwner) {
        Log.d(LOG_TAG, "call autoResume")
        if (soundPool == null) return
        soundPool.autoResume() // soundPool.autoPause()したときにアクティブだったすべての音声ファイルを再生する.
    }

    // 画面非表示時に呼ばれる
    override fun onPause(owner: LifecycleOwner) {
        Log.d(LOG_TAG, "call autoPause")
        if (soundPool == null) return
        soundPool.autoPause() // 全ての再生中の音声ファイルを停止する.
    }

    // 画面終了時に呼ばれる
    override fun onDestroy(owner: LifecycleOwner) {
        Log.d(LOG_TAG, "call release soundPool: $soundPool")
        if (soundPool == null) return
        soundPool.release() // SoundPoolによって使用されているすべてのメモリとネイティブリソースを解放する.
    }

    companion object {
        private val LOG_TAG = MainSoundPlayer::class.java.simpleName
        private const val LEFT_VOLUME_VALUE = 1.0f // left volume value (range = 0.0 to 1.0)
        private const val RIGHT_VOLUME_VALUE = 1.0f // right volume value (range = 0.0 to 1.0)
        private const val SOUND_LOOP_MODE_NO_LOOP = 0 // loop mode (0 = no loop, -1 = loop forever)
        private const val SOUND_PLAY_BACK_RATE =
            1.0f // playback rate (1.0 = normal playback, range 0.5 to 2.0)
        private const val PRIORITY_1 = 1
    }

    init {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()
        val maxStreams = 2 // ストリーム数
        soundPool = SoundPool.Builder()
            .setAudioAttributes(audioAttributes)
            .setMaxStreams(maxStreams)
            .build()
        curtainSoundId = soundPool.load(context, R.raw.curtain1, PRIORITY_1)
        windowSoundId = soundPool.load(context, R.raw.small_window, PRIORITY_1)
    }
}