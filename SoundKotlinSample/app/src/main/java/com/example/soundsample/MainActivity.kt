package com.example.soundsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val soundPlayer = MainSoundPlayer(this)
        lifecycle.addObserver(soundPlayer)
        // 効果音の再生/停止処理
        findViewById<View>(R.id.btnCurtainSoundPlay).setOnClickListener { soundPlayer.playCurtainSound() }
        findViewById<View>(R.id.btnCurtainSoundStop).setOnClickListener { soundPlayer.stopCurtainSound() }
        findViewById<View>(R.id.btnWindowoundPlay).setOnClickListener { soundPlayer.playWindowSound() }
        findViewById<View>(R.id.btnWindowSoundStop).setOnClickListener { soundPlayer.stopWindowSound() }
    }
}