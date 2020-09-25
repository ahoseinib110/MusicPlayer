package org.maktab.musicplayer.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.media.MediaPlayer;
import android.os.Bundle;

import org.maktab.musicplayer.R;
import org.maktab.musicplayer.fragment.PlayFragment;

public class MusicActivity extends AppCompatActivity {

    private ViewPager2 mViewPagerMusic;
    private MusicPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        //FragmentManager fragmentManager = getSupportFragmentManager();
        //fragmentManager.beginTransaction().add(R.id.fragment_container,new PlayFragment()).commit();
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
                    return PlayFragment.newInstance();
                case 1:
                    return PlayFragment.newInstance();
                case 2:
                    return PlayFragment.newInstance();
            }
            return null;
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}