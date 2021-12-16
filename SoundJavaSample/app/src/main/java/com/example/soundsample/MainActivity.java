package com.example.soundsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MainSoundPlayer soundPlayer = new MainSoundPlayer(this);
        getLifecycle().addObserver(soundPlayer);
        // 効果音の再生/停止処理
        findViewById(R.id.btnCurtainSoundPlay).setOnClickListener(v -> soundPlayer.playCurtainSound());
        findViewById(R.id.btnCurtainSoundStop).setOnClickListener(v -> soundPlayer.stopCurtainSound());

        findViewById(R.id.btnWindowoundPlay).setOnClickListener(v -> soundPlayer.playWindowSound());
        findViewById(R.id.btnWindowSoundStop).setOnClickListener(v -> soundPlayer.stopWindowSound());
    }
}
