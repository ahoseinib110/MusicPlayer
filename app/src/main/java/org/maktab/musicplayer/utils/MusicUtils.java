package org.maktab.musicplayer.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import org.maktab.musicplayer.model.Audio;

import java.util.ArrayList;

public class MusicUtils {
    // Method to read all the audio/MP3 files.
/*    public static List<Audio> getAllAudioFromDevice(final Context context) {
        final List<Audio> tempAudioList = new ArrayList<>();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        Log.d("bashir","uri " + uri.toString());
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
    }*/


    public static ArrayList<Audio> getPlayList(Context context) {


        String sortOrder = null;
        ArrayList<Audio> songList = new ArrayList<>();
        try {
            String[] proj = {MediaStore.Audio.Media._ID,
                    MediaStore.Audio.Media.TITLE,
                    MediaStore.Audio.Media.ARTIST,
                    MediaStore.Audio.Media.DURATION,
                    MediaStore.Audio.Media.DATA,
                    MediaStore.Audio.Media.ALBUM,
                    MediaStore.Audio.Media.ALBUM_ID
            };

            String selection = null;

            Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            String[] selectionArgs = {uri.toString()};


            //Cursor audioCursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, proj, selection, selectionArgs, sortOrder);
            Cursor audioCursor = context.getContentResolver().query(uri, proj, MediaStore.Audio.Media.DATA + " like ? ", new String[]{"%mp3%"}, null);

            if (audioCursor != null) {
                if (audioCursor.moveToFirst()) {
                    do {
                        int audioTitle = audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
                        int audioartist = audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);
                        int audioduration = audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);
                        int audiodata = audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
                        int audioalbum = audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM);
                        int audioalbumid = audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID);
                        int song_id = audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);


                        Audio info = new Audio();
                        info.setUri(Uri.parse(audioCursor.getString(audiodata)));
                        info.setName(audioCursor.getString(audioTitle));
                        info.setDuration((audioCursor.getString(audioduration)));
                        info.setArtist(audioCursor.getString(audioartist));
                        info.setAlbum(audioCursor.getString(audioalbum));
                        info.setId(audioCursor.getLong(song_id));
                        //info.setAlbumArt((ContentUris.withAppendedId(albumArtUri, audioCursor.getLong(audioalbumid))).toString());
                        songList.add(info);


                    } while (audioCursor.moveToNext());
                }
            }
            assert audioCursor != null;
            audioCursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return songList;
    }



    public static ArrayList<Audio> getSongList(Context context) {
        ArrayList<Audio> mSongList = new ArrayList<>();
        Uri sArtworkUri = Uri.parse("content://media/external/audio/media");
        //retrieve item_song info
        ContentResolver musicResolver = context.getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Log.d("bashir","main uri "+musicUri.toString());
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);
        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int albumID = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.ALBUM_ID);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            int songLink = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DATA);
            //add songs to list
            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                Uri thisSongLink = Uri.parse(musicCursor.getString(songLink));
                long some = musicCursor.getLong(albumID);
                //Uri uri = ContentUris.withAppendedId(sArtworkUri, some);
                Uri uri = ContentUris.withAppendedId(musicUri, idColumn);
                mSongList.add(new Audio(thisTitle, thisArtist, uri,thisSongLink.toString()));
            }
            while (musicCursor.moveToNext());
        }
        assert musicCursor != null;
        musicCursor.close();
        return mSongList;
    }
}
