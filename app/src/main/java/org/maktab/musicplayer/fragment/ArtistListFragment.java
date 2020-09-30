package org.maktab.musicplayer.fragment;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ArtistListFragment extends Fragment {
    private RecyclerView mRecyclerViewArtistList;
    private MusicRepository mMusicRepository;
    private ArtistListFragment.ArtistListAdapter mAdapter;
    private ArtistListFragment.CallBack mCallBack;
    private List<List<Music>> mArtistsMusicList;

    public static ArtistListFragment newInstance() {
        ArtistListFragment fragment = new ArtistListFragment();
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
        Map<Long, List<Music>> artistsMap = mMusicRepository.getArtistMap();
        mArtistsMusicList = new ArrayList<List<Music>>(artistsMap.values());
        /*Map<Long, List<Music>> artistsMusic = MusicRepository.getArtistList();
        for (Map.Entry am : artistsMusic.entrySet()) {
            Log.d("bashir", "key***************************** " + am.getKey().toString());
            List<Music> musics = (List<Music>) am.getValue();
            Log.d("bashir", "list size " + musics.size());
            //for(Music music:musics){
            //    Log.d("bashir",music.getArtist());
            //}
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_artist_list, container, false);
        findViews(view);
        initUI();
        return view;
    }

    private void findViews(View view) {
        mRecyclerViewArtistList = view.findViewById(R.id.recyclerViewArtistList);
    }

    private void initUI() {
        mRecyclerViewArtistList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new ArtistListFragment.ArtistListAdapter(mArtistsMusicList);
        mRecyclerViewArtistList.setAdapter(mAdapter);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ArtistListFragment.CallBack) {
            mCallBack = (ArtistListFragment.CallBack) context;
        } else {

        }
    }

    public class ArtistListHolder extends RecyclerView.ViewHolder {
        private List<Music> mMusicList;
        private ImageView mImageArtist;
        private TextView mTextArtistName;
        private View mView;

        public ArtistListHolder(@NonNull View itemView) {
            super(itemView);
            findViews(itemView);
        }

        private void findViews(@NonNull View itemView) {
            mImageArtist = itemView.findViewById(R.id.imageArtist);
            mTextArtistName = itemView.findViewById(R.id.textArtistName);
            mView = itemView.findViewById(R.id.artist_row);
        }

        public void onBind(List<Music> musicList) {
            mMusicList = musicList;
            mTextArtistName.setText(mMusicList.get(0).getArtist());
            setOnClickListener();
        }

        private void setOnClickListener() {
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("bashir", "1: " + mMusicList.get(0).getTitle());
                    mCallBack.startArtistMusicActivity(mMusicList);
                }
            });
        }

    }


    public class ArtistListAdapter extends RecyclerView.Adapter<ArtistListFragment.ArtistListHolder> {
        List<List<Music>> mArtistsMusics;

        public ArtistListAdapter(List<List<Music>> artistsMusics) {
            mArtistsMusics = artistsMusics;
        }

        public List<List<Music>> getArtistsMusics() {
            return mArtistsMusics;
        }

        public void setArtistsMusics(List<List<Music>> artistsMusics) {
            mArtistsMusics = artistsMusics;
        }

        @NonNull
        @Override
        public ArtistListFragment.ArtistListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity().getApplicationContext());
            View view = inflater.inflate(R.layout.list_row_artist, parent, false);
            return new ArtistListFragment.ArtistListHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ArtistListFragment.ArtistListHolder holder, int position) {
            holder.onBind(mArtistsMusics.get(position));
        }

        @Override
        public int getItemCount() {
            return mArtistsMusics.size();
        }
    }

    public interface CallBack {
        public void startArtistMusicActivity(List<Music> musicList);
    }
}