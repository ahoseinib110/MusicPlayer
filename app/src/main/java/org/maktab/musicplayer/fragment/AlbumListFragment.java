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
import org.maktab.musicplayer.repository.MusicRepository;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlbumListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class AlbumListFragment extends Fragment {
    private RecyclerView mRecyclerViewAlbumList;
    private MusicRepository mMusicRepository;
    private AlbumListFragment.AlbumListAdapter mAdapter;
    private AlbumListFragment.CallBack mCallBack;
    private List<List<Music>> mAlbumsMusicList;

    public static AlbumListFragment newInstance() {
        AlbumListFragment fragment = new AlbumListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        mMusicRepository = MusicRepository.getInstance(getActivity());
        mAlbumsMusicList  = mMusicRepository.getAlbumsMusicList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_album_list, container, false);
        findViews(view);
        initUI();
        return view;
    }

    private void findViews(View view) {
        mRecyclerViewAlbumList = view.findViewById(R.id.recyclerViewAlbumList);
    }

    private void initUI() {
        mRecyclerViewAlbumList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new AlbumListFragment.AlbumListAdapter(mAlbumsMusicList);
        mRecyclerViewAlbumList.setAdapter(mAdapter);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AlbumListFragment.CallBack) {
            mCallBack = (AlbumListFragment.CallBack) context;
        } else {

        }
    }

    public class AlbumListHolder extends RecyclerView.ViewHolder {
        private List<Music> mMusicList;
        private ImageView mImageAlbum;
        private TextView mTextAlbumName;
        private View mView;

        public AlbumListHolder(@NonNull View itemView) {
            super(itemView);
            findViews(itemView);
        }

        private void findViews(@NonNull View itemView) {
            mImageAlbum = itemView.findViewById(R.id.imageAlbum);
            mTextAlbumName = itemView.findViewById(R.id.textAlbumName);
            mView = itemView.findViewById(R.id.album_row);
        }

        public void onBind(List<Music> musicList) {
            mMusicList = musicList;
            mTextAlbumName.setText(mMusicList.get(0).getAlbum());
            setOnClickListener();
            try {
                MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                mmr.setDataSource(getActivity(), Uri.parse(mMusicList.get(0).getUri()));
                byte [] data = mmr.getEmbeddedPicture();
                if (data != null){
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    mImageAlbum.setImageBitmap(bitmap);
                }else {
                    mImageAlbum.setImageDrawable(getResources().getDrawable(R.drawable.album_art));
                }
            }catch (Exception e){
                //Log.d("bashir",e.getMessage());
            }
        }

        private void setOnClickListener() {
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("bashir", "1: " + mMusicList.get(0).getTitle());
                    mCallBack.startAlbumMusicActivity(mMusicList);
                }
            });
        }

    }


    public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListFragment.AlbumListHolder> {
        List<List<Music>> mAlbumsMusics;

        public AlbumListAdapter(List<List<Music>> AlbumsMusics) {
            mAlbumsMusics = AlbumsMusics;
        }

        public List<List<Music>> getAlbumsMusics() {
            return mAlbumsMusics;
        }

        public void setAlbumsMusics(List<List<Music>> AlbumsMusics) {
            mAlbumsMusics = AlbumsMusics;
        }

        @NonNull
        @Override
        public AlbumListFragment.AlbumListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity().getApplicationContext());
            View view = inflater.inflate(R.layout.list_row_album, parent, false);
            return new AlbumListFragment.AlbumListHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AlbumListFragment.AlbumListHolder holder, int position) {
            holder.onBind(mAlbumsMusics.get(position));
        }

        @Override
        public int getItemCount() {
            return mAlbumsMusics.size();
        }
    }

    public interface CallBack {
        public void startAlbumMusicActivity(List<Music> musicList);
    }
}