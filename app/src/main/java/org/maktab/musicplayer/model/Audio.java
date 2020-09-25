package org.maktab.musicplayer.model;

import android.net.Uri;

public class Audio {
    String mPath;
    String mName;
    String mAlbum;
    String mArtist;
    String mDuration;
    String mAlbumArt;
    Uri mUri;
    Long mId;

    public Audio() {
    }

    public Audio(String name, String artist, Uri uri,String path) {
        mName = name;
        mArtist = artist;
        mUri = uri;
        mPath = path;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getAlbum() {
        return mAlbum;
    }

    public void setAlbum(String album) {
        mAlbum = album;
    }

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String artist) {
        mArtist = artist;
    }

    public String getDuration() {
        return mDuration;
    }

    public void setDuration(String duration) {
        mDuration = duration;
    }

    public String getAlbumArt() {
        return mAlbumArt;
    }

    public void setAlbumArt(String albumArt) {
        mAlbumArt = albumArt;
    }

    public Uri getUri() {
        return mUri;
    }

    public void setUri(Uri uri) {
        mUri = uri;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }
}
