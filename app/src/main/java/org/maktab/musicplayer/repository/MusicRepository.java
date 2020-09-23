package org.maktab.musicplayer.repository;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.util.Log;


import org.maktab.musicplayer.model.Music;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicRepository {
    private static final String ASSET_Music_FOLDER = "sample_musics";
    public static final String TAG = "Music";
    private static MusicRepository sInstance;

    public static MusicRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new MusicRepository(context);
        return sInstance;
    }

    private Context mContext;
    private AssetManager mAssetManager;
    private List<Music> mMusics;
    private MediaPlayer mMediaPlayer;

    public List<Music> getMusics() {
        return mMusics;
    }

    public void setMusics(List<Music> musics) {
        mMusics = musics;
    }

    public MediaPlayer getMediaPlayer() {
        return mMediaPlayer;
    }

    private MusicRepository(Context context) {
        mContext = context.getApplicationContext();
        mAssetManager = mContext.getAssets();
        mMediaPlayer = new MediaPlayer();
        mMusics = new ArrayList<>();
        try {
            String[] fileNames = mAssetManager.list(ASSET_Music_FOLDER);
            for (String fileName: fileNames) {
                Log.d(TAG, fileName);
                String assetPath = ASSET_Music_FOLDER + File.separator + fileName;

                Music music = new Music(assetPath);

                AssetFileDescriptor afd = mAssetManager.openFd(assetPath);

                //int soundId = mSoundPool.load(afd, SOUND_PRIORITY);
                //sound.setSoundId(soundId);

                mMusics.add(music);
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

}
