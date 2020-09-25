package org.maktab.musicplayer.repository;

import android.content.Context;
import android.media.MediaPlayer;


import org.maktab.musicplayer.model.Music;
import org.maktab.musicplayer.utils.MusicUtils;

import java.util.List;

public class MusicRepository {
    public static final String TAG = "AudioRepository";
    private static MusicRepository sInstance;

    private Context mContext;
    private List<Music> mMusicList;
    private MediaPlayer mMediaPlayer;

    public static MusicRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new MusicRepository(context);
        return sInstance;
    }

    public List<Music> getMusicList() {
        return mMusicList;
    }

    public void setMusicList(List<Music> musicList) {
        mMusicList = musicList;
    }

    public MediaPlayer getMediaPlayer() {
        return mMediaPlayer;
    }

    private MusicRepository(Context context) {
        mContext = context.getApplicationContext();
        mMediaPlayer = new MediaPlayer();
        mMusicList = MusicUtils.getAudioList(context);
    }

}
