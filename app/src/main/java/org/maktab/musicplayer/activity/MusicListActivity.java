package org.maktab.musicplayer.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.maktab.musicplayer.R;
import org.maktab.musicplayer.fragment.MusicListFragment;
import org.maktab.musicplayer.model.Music;

import java.io.Serializable;
import java.util.List;

public class MusicListActivity extends AppCompatActivity implements MusicListFragment.CallBack {

    public static final String EXTRA_MUSIC_LIST = "org.maktab.musicplayer.activity.MusicListActivity.extraMusicList";
private  List<Music> mMusicList;
    public static Intent newIntent(Context context, List<Music> musicList){
        Intent intent = new Intent(context,MusicListActivity.class);
        intent.putExtra(EXTRA_MUSIC_LIST, (Serializable) musicList);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);
        Intent intent = getIntent();
        mMusicList  = (List<Music>) intent.getSerializableExtra(EXTRA_MUSIC_LIST);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.musicListFragmentContainer,MusicListFragment.newInstance(mMusicList))
                .commit();
    }

    @Override
    public void startPlayActivity(List<Music> musicList,int musicIndex) {
        Intent intent = PlayActivity.newIntent(this, musicList,musicIndex);
        startActivity(intent);
    }
}