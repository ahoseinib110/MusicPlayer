package org.maktab.musicplayer.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

import org.maktab.musicplayer.R;
import org.maktab.musicplayer.fragment.ArtistListFragment;
import org.maktab.musicplayer.fragment.MusicListFragment;
import org.maktab.musicplayer.fragment.PlayFragment;
import org.maktab.musicplayer.model.Music;

import java.util.List;

public class MusicActivity extends AppCompatActivity implements MusicListFragment.CallBack , ArtistListFragment.CallBack {

    private ViewPager2 mViewPagerMusic;
    private MusicPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        findViews();
        mAdapter = new MusicPagerAdapter(this);
        mViewPagerMusic.setAdapter(mAdapter);
    }

    public void findViews() {
        mViewPagerMusic = findViewById(R.id.viewPagerMusic);
    }


    public class MusicPagerAdapter extends FragmentStateAdapter {


        public MusicPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return MusicListFragment.newInstance();
                case 1:
                    return ArtistListFragment.newInstance();
                case 2:
                    return MusicListFragment.newInstance();
            }
            return null;
        }

        @Override
        public int getItemCount() {
            return 3;
        }

    }

    @Override
    public void startPlayActivity(Music music) {
        Log.d("bashir","2: "+music.getTitle());
        Intent intent = PlayActivity.newIntent(this,music);
        startActivity(intent);
    }

    @Override
    public void startArtistMusicActivity(List<Music> musicList) {

    }
}