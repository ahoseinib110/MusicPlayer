package org.maktab.musicplayer.model;

import android.net.Uri;

public class Music {
    String mTitle;
    String mPath;
    Uri mUri;
    String mAlbum;
    String mArtist;
    String mDuration;
    String mAlbumArt;
    Long mId;
    Long mAlbumID;
    Long mArtistId;


    public Music(Uri uri,Long id,Long albumID,Long artistID,String title,String album,String artist,String path,String duration) {
        mUri = uri;
        mId = id;
        mAlbumID = albumID;
        mArtistId = artistID;
        mTitle = title;
        mAlbum=album;
        mArtist = artist;
        mPath = path;
        mDuration = duration;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Long getAlbumID() {
        return mAlbumID;
    }

    public void setAlbumID(Long albumID) {
        mAlbumID = albumID;
    }

    public Long getArtistId() {
        return mArtistId;
    }

    public void setArtistId(Long artistId) {
        mArtistId = artistId;
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
