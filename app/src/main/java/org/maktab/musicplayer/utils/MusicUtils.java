package org.maktab.musicplayer.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import org.maktab.musicplayer.model.Audio;

import java.util.ArrayList;
import java.util.List;

public class MusicUtils {
    // Method to read all the audio/MP3 files.
    public static List<Audio> getAllAudioFromDevice(final Context context) {
        final List<Audio> tempAudioList = new ArrayList<>();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.AudioColumns.DATA,MediaStore.Audio.AudioColumns.TITLE ,MediaStore.Audio.AudioColumns.ALBUM, MediaStore.Audio.ArtistColumns.ARTIST,};
        Cursor c = context.getContentResolver().query(uri, projection, MediaStore.Audio.Media.DATA + " like ? ", new String[]{"%mp3%"}, null);

        if (c != null) {
            while (c.moveToNext()) {
                // Create a model object.
                Audio audioModel = new Audio();

                String path = c.getString(0);   // Retrieve path.
                String name = c.getString(1);   // Retrieve name.
                String album = c.getString(2);  // Retrieve album name.
                String artist = c.getString(3); // Retrieve artist name.

                // Set data to the model object.
                audioModel.setaName(name);
                audioModel.setaAlbum(album);
                audioModel.setaArtist(artist);
                audioModel.setaPath(path);

                Log.e("Name :" + name, " Album :" + album);
                Log.e("Path :" + path, " Artist :" + artist);

                // Add the model object to the list .
                tempAudioList.add(audioModel);
            }
            c.close();
        }

        // Return the list.
        return tempAudioList;
    }
}
