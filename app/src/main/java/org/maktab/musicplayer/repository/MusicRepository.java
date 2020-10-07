package org.maktab.musicplayer.repository;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;


import org.maktab.musicplayer.model.Music;
import org.maktab.musicplayer.utils.MusicUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MusicRepository {
    public static final String TAG = "AudioRepository";
    private static MusicRepository sInstance;

    private Context mContext;
    private static List<Music> mMusicList;
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



    private MusicRepository(Context context) {
        mMediaPlayer = new MediaPlayer();
        mContext = context.getApplicationContext();
        mMusicList = MusicUtils.getAudioList(context);
    }

    public List<List<Music>> getArtistsMusicList() {
        Map<Long, List<Music>> artistsMap = new LinkedHashMap<>();
        for(Music music:mMusicList){
            if(artistsMap.containsKey(music.getArtistId())){
                List<Music> musicList = artistsMap.get(music.getArtistId());
                musicList.add(music);
                artistsMap.put(music.getArtistId(),musicList);
            }else {
                List<Music> musicList = new ArrayList<>();
                musicList.add(music);
                artistsMap.put(music.getArtistId(),musicList);
            }
        }
        return new ArrayList<List<Music>>(artistsMap.values());
    }

    public List<List<Music>> getAlbumsMusicList() {
        Map<Long, List<Music>> albumsMap = new LinkedHashMap<>();
        for(Music music:mMusicList){
            if(albumsMap.containsKey(music.getAlbumID())){
                List<Music> musicList = albumsMap.get(music.getAlbumID());
                musicList.add(music);
                albumsMap.put(music.getAlbumID(),musicList);
            }else {
                List<Music> musicList = new ArrayList<>();
                musicList.add(music);
                albumsMap.put(music.getAlbumID(),musicList);
            }
        }
        return new ArrayList<List<Music>>(albumsMap.values());
    }

    public MediaPlayer getMediaPlayer(){
        return mMediaPlayer;
    }

}


