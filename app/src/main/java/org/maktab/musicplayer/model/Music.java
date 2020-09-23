package org.maktab.musicplayer.model;

import java.io.File;

public class Music {

    private String mName;
    private String mAssetPath;
    private Integer mMusicId;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getAssetPath() {
        return mAssetPath;
    }

    public void setAssetPath(String assetPath) {
        mAssetPath = assetPath;
    }

    public Integer getMusicId() {
        return mMusicId;
    }

    public void setMusicId(Integer musicId) {
        mMusicId = musicId;
    }

    public Music(String assetPath) {
        //example: assetPath: sample_music/
        mAssetPath = assetPath;
        mName = extractFileName(mAssetPath);
    }

    private String extractFileName(String assetPath) {
        String[] segments = assetPath.split(File.separator);
        String fileNameWithExt = segments[segments.length - 1];
        return fileNameWithExt.substring(0, fileNameWithExt.lastIndexOf("."));
    }
}
