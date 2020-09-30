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

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.maktab.musicplayer.R;
import org.maktab.musicplayer.fragment.AlbumListFragment;
import org.maktab.musicplayer.fragment.ArtistListFragment;
import org.maktab.musicplayer.fragment.MusicListFragment;
import org.maktab.musicplayer.fragment.PlayFragment;
import org.maktab.musicplayer.model.Music;
import org.maktab.musicplayer.repository.MusicRepository;

import java.util.List;

public class MusicActivity extends AppCompatActivity implements MusicListFragment.CallBack, ArtistListFragment.CallBack, AlbumListFragment.CallBack {

    private ViewPager2 mViewPagerMusic;
    private TabLayout mTabLayout;
    private MusicPagerAdapter mAdapter;
    private MusicRepository mMusicRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        findViews();
        mMusicRepository = MusicRepository.getInstance(this);
        mAdapter = new MusicPagerAdapter(this);
        mViewPagerMusic.setAdapter(mAdapter);

        new TabLayoutMediator(mTabLayout, mViewPagerMusic , true, new TabLayoutMediator.OnConfigureTabCallback() {
            @Override
            public void onConfigureTab(TabLayout.Tab tab, int position) {
                // position of the current tab and that tab
                switch (position){
                    case 0:tab.setText("Musics");break;
                    case 1:tab.setText("Artists");break;
                    case 2:tab.setText("Albums");break;
                }

            }
        }).attach();


    }

    public void findViews() {
        mViewPagerMusic = findViewById(R.id.viewPagerMusic);
        mTabLayout = findViewById(R.id.tab_layout);
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
                    return MusicListFragment.newInstance(mMusicRepository.getMusicList());
                case 1:
                    return ArtistListFragment.newInstance();
                case 2:
                    return AlbumListFragment.newInstance();
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
        Log.d("bashir", "2: " + music.getTitle());
        Intent intent = PlayActivity.newIntent(this, music);
        startActivity(intent);
    }

    @Override
    public void startArtistMusicActivity(List<Music> musicList) {
        Intent intent = MusicListActivity.newIntent(this, musicList);
        startActivity(intent);
    }

    @Override
    public void startAlbumMusicActivity(List<Music> musicList) {
        Intent intent = MusicListActivity.newIntent(this, musicList);
        startActivity(intent);
    }

}