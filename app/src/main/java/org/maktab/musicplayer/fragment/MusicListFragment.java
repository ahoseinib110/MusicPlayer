package org.maktab.musicplayer.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.maktab.musicplayer.R;
import org.maktab.musicplayer.model.Music;

import java.io.Serializable;
import java.util.List;


public class MusicListFragment extends Fragment {

    public static final String ARG_MUSIC_LIST = "argMusicList";
    private RecyclerView mRecyclerViewMusicList;
    private MusicListAdapter mAdapter;
    private CallBack mCallBack;
    private List<Music> mMusicList;

    public static MusicListFragment newInstance(List<Music> musicList) {
        MusicListFragment fragment = new MusicListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MUSIC_LIST, (Serializable) musicList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMusicList = (List<Music>) getArguments().getSerializable(ARG_MUSIC_LIST);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_music_list, container, false);
        findViews(view);
        initUI();
        return view;
    }

    private void findViews(View view) {
        mRecyclerViewMusicList = view.findViewById(R.id.recyclerViewMusicList);
    }

    private void initUI() {
        mRecyclerViewMusicList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MusicListAdapter(mMusicList);
        mRecyclerViewMusicList.setAdapter(mAdapter);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CallBack) {
            mCallBack = (CallBack) context;
        } else {

        }
    }

    public class MusicListHolder extends RecyclerView.ViewHolder {
        private List<Music> mMusicList;
        private int mMusicIndex;
        private Music mMusic;
        private ImageView mImageMusicCover;
        private TextView mTextMusicTitle;
        private TextView mTextMusicArtist;
        private View mView;

        public MusicListHolder(@NonNull View itemView) {
            super(itemView);
            findViews(itemView);

        }

        private void findViews(@NonNull View itemView) {
            mImageMusicCover = itemView.findViewById(R.id.imageMusicCover);
            mTextMusicTitle = itemView.findViewById(R.id.textMusicTitle);
            mTextMusicArtist = itemView.findViewById(R.id.textMusicArtist);
            mView = itemView.findViewById(R.id.music_row);
        }

        public void onBind(List<Music> musicList,int musicIndex) {
            mMusicList = musicList;
            mMusicIndex= musicIndex;
            mMusic = musicList.get(musicIndex);
            mTextMusicTitle.setText(mMusic.getTitle());
            mTextMusicArtist.setText(mMusic.getArtist());
            setOnClickListener();
            try {
                MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                Log.d("bashir",mMusic.getTitle()+"  "+mMusic.getPath());
                mmr.setDataSource(getActivity(), Uri.parse(mMusic.getUri()));
                byte [] data = mmr.getEmbeddedPicture();
                if (data != null){
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    mImageMusicCover.setImageBitmap(bitmap);
                }else {
                    mImageMusicCover.setImageDrawable(getResources().getDrawable(R.drawable.album_art));
                }
            }catch (Exception e){
                //Log.d("bashir",e.getMessage());
            }
        }

        private void setOnClickListener() {
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("bashir", "1: " + mMusic.getTitle());
                    mCallBack.startPlayActivity(mMusicList,mMusicIndex);
                }
            });
        }

    }


    public class MusicListAdapter extends RecyclerView.Adapter<MusicListHolder> {
        List<Music> mMusicList;

        public MusicListAdapter(List<Music> musicList) {
            mMusicList = musicList;
        }

        public List<Music> getMusicList() {
            return mMusicList;
        }

        public void setMusicList(List<Music> musicList) {
            mMusicList = musicList;
        }

        @NonNull
        @Override
        public MusicListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity().getApplicationContext());
            View view = inflater.inflate(R.layout.list_row_music, parent, false);
            return new MusicListHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MusicListHolder holder, int position) {
            holder.onBind(mMusicList,position);
        }

        @Override
        public int getItemCount() {
            return mMusicList.size();
        }
    }

    public interface CallBack {
        public void startPlayActivity(List<Music> musicList,int musicIndex);
    }
}