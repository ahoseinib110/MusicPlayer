package org.maktab.musicplayer.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.maktab.musicplayer.R;
import org.maktab.musicplayer.fragment.PlayFragment;
import org.maktab.musicplayer.model.Audio;
import org.maktab.musicplayer.utils.MusicUtils;

import java.io.IOException;
import java.util.ArrayList;

public class MusicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_music);
        FragmentManager fragmentManager = getSupportFragmentManager();
        //fragmentManager.beginTransaction().add(R.id.fragment_container,new PlayFragment()).commit();

        ArrayList<Audio> mSongList = MusicUtils.getSongList(getApplicationContext());
        //Uri myUri1 = Uri.parse("file:///sdcard/Samsung/Music/Over_the_Horizon.mp3");
        // Even you can refer resource in res/raw directory
        //Uri myUri = Uri.parse("android.resource://com.prgguru.example/" + R.raw.hosannatamil);

        MediaPlayer mMediaPlayer = new MediaPlayer();
        try {
            //mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            Log.d("bashir","uri " +mSongList.get(0).getUri().toString());
            mMediaPlayer.setDataSource(getApplicationContext(), mSongList.get(0).getUri());
            //mMediaPlayer.setDataSource(getApplicationContext(), Uri.parse("content://media/external/audio/media/10"));
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IllegalArgumentException | IllegalStateException | IOException e) {
            e.printStackTrace();
        }














        /*MediaPlayer mPlayer = new MediaPlayer();
        mPlayer.reset();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            Log.d("bashir","path: "+mSongList.get(0).getPath());
            mPlayer.setDataSource(getApplicationContext(), Uri.parse(mSongList.get(0).getPath()));
        } catch (IllegalArgumentException e) {
            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (SecurityException e) {
            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IllegalStateException e) {
            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mPlayer.prepare();
        } catch (IllegalStateException e) {
            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        }
        mPlayer.start();*/

    }
}