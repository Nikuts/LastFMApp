package com.nikkuts.lastfmapp.adaptors;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nikkuts.lastfmapp.R;
import com.nikkuts.lastfmapp.gson.albuminfo.Track;

import org.w3c.dom.Text;

import java.util.List;

public class TracksAdaptor extends RecyclerView.Adapter<TracksAdaptor.TracksViewHolder> {


    public void setAlbums(List<Track> tracks){
        this.mTracks = tracks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TracksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.track, parent, false);
        TracksViewHolder viewHolder = new TracksViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TracksViewHolder holder, int position) {
        holder.mTrackName.setText(mTracks.get(position).getName());
        holder.mTrackNum.setText(String.valueOf(position + 1));
    }

    @Override
    public int getItemCount() {
        if (mTracks != null) {
            return mTracks.size();
        }
        return 0;
    }

    public static class TracksViewHolder extends RecyclerView.ViewHolder {
        public TracksViewHolder(View itemView) {
            super(itemView);
            mTrackName = itemView.findViewById(R.id.track_name);
            mTrackNum = itemView.findViewById(R.id.track_num);
        }

        private TextView mTrackName;
        private TextView mTrackNum;
    }

    private List<Track> mTracks;
}
