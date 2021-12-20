package com.example.soundcomposesample

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 * Sound class used in MainActivity
 */
class MainSoundPlayer(context: Context) : DefaultLifecycleObserver {
    private var soundPool: SoundPool
    private val curtainSoundId: Int
    private var curtainStreamId = 0
    private val windowSoundId: Int
    private var windowStreamId = 0

    // Play sound effects
    fun playCurtainSound() {
        curtainStreamId = soundPool.play(
            curtainSoundId, LEFT_VOLUME_VALUE, RIGHT_VOLUME_VALUE,
            PRIORITY_1, SOUND_LOOP_MODE_NO_LOOP, SOUND_PLAY_BACK_RATE
        )
    }

    fun playWindowSound() {
        windowStreamId = soundPool.play(
            windowSoundId, LEFT_VOLUME_VALUE, RIGHT_VOLUME_VALUE,
            PRIORITY_1, SOUND_LOOP_MODE_NO_LOOP, SOUND_PLAY_BACK_RATE
        )
    }

    // Stop sound effects
    fun stopCurtainSound() {
        soundPool.stop(curtainStreamId)
    }

    fun stopWindowSound() {
        soundPool.stop(windowStreamId)
    }

    // Called when the screen is displayed
    override fun onResume(owner: LifecycleOwner) {
        Log.d(LOG_TAG, "call autoResume")
        soundPool.autoResume() // Plays all audio files that were active when soundPool.autoPause () was done.
    }

    // Called when the screen is hidden
    override fun onPause(owner: LifecycleOwner) {
        Log.d(LOG_TAG, "call autoPause")
        soundPool.autoPause() // Stop all playing audio files.
    }

    // Called at the end of the screen
    override fun onDestroy(owner: LifecycleOwner) {
        Log.d(LOG_TAG, "call release soundPool: $soundPool")
        soundPool.release() // Free all memory and native resources used by SoundPool.
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
        val maxStreams = 2 // stream size
        soundPool = SoundPool.Builder()
            .setAudioAttributes(audioAttributes)
            .setMaxStreams(maxStreams)
            .build()
        curtainSoundId = soundPool.load(context, R.raw.curtain1, PRIORITY_1)
        windowSoundId = soundPool.load(context, R.raw.small_window, PRIORITY_1)
    }
}