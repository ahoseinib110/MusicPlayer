package org.maktab.musicplayer.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.maktab.musicplayer.R;
import org.maktab.musicplayer.fragment.PlayFragment;
import org.maktab.musicplayer.model.Music;
import org.maktab.musicplayer.repository.MusicRepository;

import java.io.Serializable;
import java.util.List;

public class PlayActivity extends AppCompatActivity {
    public static final String EXTRA_MUSIC_LIST = "org.maktab.musicplayer.activity.PlayActvity.extraMusicList";
    public static final String EXTRA_MUSIC_INDEX = "org.maktab.musicplayer.activity.PlayActvity.extraMusicIndex";
    private List<Music> mMusicList;
    private int mMusicIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Intent intent = getIntent();
        mMusicList = (List<Music>) intent.getSerializableExtra(EXTRA_MUSIC_LIST);
        mMusicIndex = intent.getIntExtra(EXTRA_MUSIC_INDEX,0);
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PlayFragment.newInstance(mMusicList,mMusicIndex))
                .commit();
    }

    public static Intent newIntent(Context context,List<Music> musicList,int musicIndex) {
        Intent intent = new Intent(context, PlayActivity.class);
        intent.putExtra(EXTRA_MUSIC_LIST, (Serializable) musicList);
        intent.putExtra(EXTRA_MUSIC_INDEX, musicIndex);
        return intent;
    }

    @Override
    public void onBackPressed() {
        MusicRepository mMusicRepository = MusicRepository.getInstance(this);
        mMusicRepository.getMediaPlayer().pause();
        Log.d("bashir","back");
        super.onBackPressed();
    }

}