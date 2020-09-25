package org.maktab.musicplayer.fragment;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;

import org.maktab.musicplayer.R;
import org.maktab.musicplayer.model.Music;
import org.maktab.musicplayer.repository.*;

import java.io.IOException;
import java.util.List;


public class PlayFragment extends Fragment {
    public static final String TAG = "bashir1_PF";
    public static final String TAG2 = "bashir2_PF";
    private SeekBar mSeekBarMusic;
    private ImageButton mImageButtonPlay;

    private MusicRepository mRepository;
    MediaPlayer mMediaPlayer;

    private double startTime = 0;
    private double finalTime = 0;
    public static int oneTimeOnly;
    private Handler mHandler = new Handler();

    private Music mMusic;

    private boolean isPlaying=false;

    public PlayFragment() {
        // Required empty public constructor
    }


    public static PlayFragment newInstance() {
        PlayFragment fragment = new PlayFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {

        }
        mRepository = MusicRepository.getInstance(getContext());
        mMediaPlayer = mRepository.getMediaPlayer();
        List<Music> musicList = mRepository.getMusicList();

        mMusic = musicList.get(0);
        //Log.d(TAG,"assets " + mMusic.getAssetPath());
        String s ="";
        for (Music music : musicList) {
            s+=(" "+ music.getName());
        }
        Log.d(TAG,"songs "+ s);
        init_music(musicList.get(0));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_play, container, false);

        findViews(view);
        seekBarChangeListener();
        setOnClickListener();
        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mRepository.getMediaPlayer().release();
    }

    private void findViews(View view) {
        mSeekBarMusic = view.findViewById(R.id.seekBarL);
        mImageButtonPlay = view.findViewById(R.id.play);

    }


    private void seekBarChangeListener() {
        mSeekBarMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mMediaPlayer.seekTo(seekBar.getProgress());
            }
        });
    }

    private void setOnClickListener() {
        mImageButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPlaying){
                    mMediaPlayer.pause();
                    mImageButtonPlay.setImageResource(R.mipmap.play);
                    isPlaying = false;
                }else {
                    mMediaPlayer.start();
                    mHandler.postDelayed(UpdateSongTime, 100);
                    mImageButtonPlay.setImageResource(R.mipmap.pause);
                    isPlaying = true;
                }
            }
        });
    }

    private void init_music(Music music) {
        oneTimeOnly = 0;
        mMediaPlayer.reset();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            //AssetFileDescriptor afd = getActivity().getAssets().openFd(music.getAssetPath());
            //mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            Log.d(TAG,""+getActivity().getExternalFilesDir(null));
            Log.d(TAG,""+getActivity().getFilesDir());
            Log.d(TAG,""+ music.getUri().toString());

            mMediaPlayer.setDataSource(getActivity(), mMusic.getUri());
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                finalTime = mMediaPlayer.getDuration();
                Log.d(TAG,"duration: "+finalTime);
                startTime = mMediaPlayer.getCurrentPosition();

                if (oneTimeOnly == 0) {
                    mSeekBarMusic.setMax((int) finalTime);
                    oneTimeOnly = 1;
                }
                mSeekBarMusic.setProgress((int) startTime);
            }
        });
    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            if(isPlaying){
                startTime = mMediaPlayer.getCurrentPosition();
                mSeekBarMusic.setProgress((int) startTime);
                mHandler.postDelayed(this, 100);
            }
        }
    };

}