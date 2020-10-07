package org.maktab.musicplayer.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import org.maktab.musicplayer.R;
import org.maktab.musicplayer.model.Music;
import org.maktab.musicplayer.model.Repeat;
import org.maktab.musicplayer.repository.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Random;


public class PlayFragment extends Fragment {
    public static final String TAG = "bashir1_PF";
    public static final String TAG2 = "bashir2_PF";
    public static final String ARG_MUSIC_LIST = "argMusicList";
    public static final String ARG_MUSIC_INDEX = "argMusicIndex";

    private SeekBar mSeekBarMusic;
    private ImageView mImageViewAlbumArt;
    private ImageButton mImageButtonPlay;
    private ImageButton mImageButtonNext;
    private ImageButton mImageButtonPrevious;
    private ImageButton mImageButtonRepeat;
    private ImageButton mImageButtonShuffle;
    private TextView mTextViewTitle;
    private TextView mTextViewAlbum;
    private TextView mTextViewArtist;
    private TextView mTextViewTimeLeft;
    private TextView mTextViewTimeDuration;

    private MusicRepository mRepository;
    private MediaPlayer mMediaPlayer;

    private double startTime = 0;
    private double finalTime = 0;
    public static int oneTimeOnly;
    private Handler mHandler = new Handler();

    private List<Music> mMusicList;
    private int mCurrentMusicIndex;



    private boolean isPlaying = false;
    private boolean isShuffleMode = false;

    private Repeat mRepeatMode = Repeat.OFF;

    public PlayFragment() {
        // Required empty public constructor
    }


    public static PlayFragment newInstance(List<Music> musicList, int musicIndex) {
        PlayFragment fragment = new PlayFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MUSIC_LIST, (Serializable) musicList);
        args.putInt(ARG_MUSIC_INDEX, musicIndex);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            mMusicList = (List<Music>) getArguments().getSerializable(ARG_MUSIC_LIST);
            mCurrentMusicIndex = getArguments().getInt(ARG_MUSIC_INDEX);
        }
        mRepository = MusicRepository.getInstance(getActivity());
        mMediaPlayer = mRepository.getMediaPlayer();
        init_music(mMusicList.get(mCurrentMusicIndex));
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

    private void updateUI() {
        setImage(mMusicList.get(mCurrentMusicIndex));
        setText(mMusicList.get(mCurrentMusicIndex));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        changePlayState();
        updateUI();
    }



    private void findViews(View view) {
        mSeekBarMusic = view.findViewById(R.id.seekBarL);
        mImageViewAlbumArt = view.findViewById(R.id.imageViewAlbumArt);
        mImageButtonPlay = view.findViewById(R.id.imageViewPlay);
        mImageButtonNext = view.findViewById(R.id.imageViewNext);
        mImageButtonPrevious = view.findViewById(R.id.imageViewPrevious);
        mImageButtonRepeat = view.findViewById(R.id.imageViewRepeat);
        mImageButtonShuffle = view.findViewById(R.id.imageViewShuffle);
        mTextViewTitle = view.findViewById(R.id.textViewTitle);
        mTextViewAlbum = view.findViewById(R.id.textViewAlbum);
        mTextViewArtist = view.findViewById(R.id.textViewArtist);
        mTextViewTimeLeft = view.findViewById(R.id.textViewTimeLeft);
        mTextViewTimeDuration = view.findViewById(R.id.textViewTimeDuration);
    }

    private void setImage(Music music) {
        try {
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(getActivity(), Uri.parse(music.getUri()));
            byte[] data = mmr.getEmbeddedPicture();
            if (data != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                mImageViewAlbumArt.setImageBitmap(bitmap);
            } else {
                mImageViewAlbumArt.setImageDrawable(getResources().getDrawable(R.drawable.album_art));
            }
        } catch (Exception e) {
            //Log.d("bashir",e.getMessage());
        }

    }

    private void setText(Music music) {
        mTextViewTitle.setText(music.getTitle());
        mTextViewAlbum.setText(music.getAlbum());
        mTextViewArtist.setText(music.getArtist());
        mTextViewTimeLeft.setText("0:00");
    }

    private String getTime(double milliSecond) {
        int seconds = (int) (milliSecond/1000);
        int minute = seconds/60;
        int second = seconds%60;
        String time;
        if(second<10){
            time = minute +":0"+second;
        }else {
            time = minute +":"+second;
        }
        return time;
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
                changePlayState();
            }
        });

        mImageButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePlayState();
                mCurrentMusicIndex = goToNext(false);
                init_music(mMusicList.get(mCurrentMusicIndex));
                updateUI();
                changePlayState();
            }
        });

        mImageButtonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePlayState();
                mCurrentMusicIndex = goToPrevious();
                init_music(mMusicList.get(mCurrentMusicIndex));
                updateUI();
                changePlayState();
            }
        });

        mImageButtonRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeRepeatMode();
            }
        });

        mImageButtonShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchShuffleMode();
            }
        });
    }

    private void changePlayState() {
        if (isPlaying) {
            mMediaPlayer.pause();
            mImageButtonPlay.setImageResource(R.mipmap.play);
            isPlaying = false;
        } else {
            mMediaPlayer.start();
            mHandler.postDelayed(UpdateSongTime, 100);
            mImageButtonPlay.setImageResource(R.mipmap.pause);
            isPlaying = true;
        }
    }

    private void init_music(Music music) {
        oneTimeOnly = 0;
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            mMediaPlayer.setDataSource(getActivity(), Uri.parse(music.getUri()));
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (IllegalStateException e){
            e.printStackTrace();
        }
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                finalTime = mMediaPlayer.getDuration();
                String duration =getTime(finalTime);
                mTextViewTimeDuration.setText(duration);
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
            try {
                if (isPlaying) {
                    startTime = mMediaPlayer.getCurrentPosition();
                    String time =getTime(startTime);
                    mTextViewTimeLeft.setText(time);
                    mSeekBarMusic.setProgress((int) startTime);
                    if(startTime>=finalTime-200 && startTime<=finalTime){
                        mHandler.postDelayed(this, 10);
                        if(startTime>=finalTime-20 && startTime<=finalTime){
                            changePlayState();
                            mCurrentMusicIndex = goToNext(true);
                            if(mCurrentMusicIndex!=-1){
                                init_music(mMusicList.get(mCurrentMusicIndex));
                                updateUI();
                                changePlayState();
                            }
                        }
                    }else {
                        mHandler.postDelayed(this, 100);
                    }
                }
            }catch (Exception e){
            }
        }
    };


    private int goToNext(boolean isAutomatic) {
        int nextMusicIndex;
        if (mCurrentMusicIndex == mMusicList.size() - 1) {
            nextMusicIndex = 0;
        } else {
            nextMusicIndex = mCurrentMusicIndex + 1;
        }

        if (isShuffleMode) {
            Random rand = new Random();
            do {
                nextMusicIndex = rand.nextInt(mMusicList.size());
            } while (nextMusicIndex == mCurrentMusicIndex);
        }

        if (isAutomatic) {
            switch (mRepeatMode) {
                case OFF:
                    if (mCurrentMusicIndex == mMusicList.size() - 1 && !isShuffleMode) {
                        return -1;
                    }
                    break;
                case ALL:
                    break;
                case ONE:
                    nextMusicIndex = mCurrentMusicIndex;
                    break;
            }
        }
        return nextMusicIndex;
    }

    private int goToPrevious() {
        int previousMusicIndex;
        if (mCurrentMusicIndex == 0) {
            previousMusicIndex = mMusicList.size() - 1;
        } else {
            previousMusicIndex = mCurrentMusicIndex - 1;
        }
        if (isShuffleMode) {
            Random rand = new Random();
            do {
                previousMusicIndex = rand.nextInt(mMusicList.size());
            } while (previousMusicIndex == mCurrentMusicIndex);
        }
        return previousMusicIndex;
    }

    private void changeRepeatMode() {
        switch (mRepeatMode) {
            case OFF:
                mRepeatMode = Repeat.ALL;
                mImageButtonRepeat.setImageDrawable(getResources().getDrawable(R.drawable.ic_repeat_blue));
                break;
            case ALL:
                mRepeatMode = Repeat.ONE;
                mImageButtonRepeat.setImageDrawable(getResources().getDrawable(R.drawable.ic_repeat1));
                break;
            case ONE:
                mRepeatMode = Repeat.OFF;
                mImageButtonRepeat.setImageDrawable(getResources().getDrawable(R.drawable.ic_repeat));
                break;
        }
    }

    private void switchShuffleMode() {
        if (isShuffleMode) {
            mImageButtonShuffle.setImageDrawable(getResources().getDrawable(R.drawable.ic_shuffle));
        } else {
            mImageButtonShuffle.setImageDrawable(getResources().getDrawable(R.drawable.ic_shuffle_blue));
        }
        isShuffleMode = !isShuffleMode;
    }

}