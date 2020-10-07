package org.maktab.musicplayer.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import org.maktab.musicplayer.R;
import org.maktab.musicplayer.repository.MusicRepository;

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Intent intent = new Intent(this,MusicActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MusicRepository musicRepository = MusicRepository.getInstance(this);
        musicRepository.getMediaPlayer().release();
    }
}