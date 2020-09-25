package org.maktab.musicplayer.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.maktab.musicplayer.R;
import org.maktab.musicplayer.fragment.PlayFragment;
import org.maktab.musicplayer.model.Music;

public class PlayActivity extends AppCompatActivity {

    public static final String EXTRA_MUSIC = "org.maktab.musicplayer.activity.PlayActvity.extraMusic";
    private Music mMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Intent intent = getIntent();
        mMusic = (Music) intent.getSerializableExtra(EXTRA_MUSIC);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Log.d("bashir","4: "+mMusic.getTitle());
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, PlayFragment.newInstance(mMusic))
                .commit();
    }

    public static Intent newIntent(Context context, Music music) {
        Intent intent = new Intent(context, PlayActivity.class);
        intent.putExtra(EXTRA_MUSIC, music);
        Log.d("bashir","3: "+music.getTitle());
        return intent;
    }
}