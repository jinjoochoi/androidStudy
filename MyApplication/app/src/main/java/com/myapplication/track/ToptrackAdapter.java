package com.myapplication.track;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jakewharton.rxrelay.PublishRelay;
import com.myapplication.Model.RealmTrack;
import com.myapplication.R;
import com.myapplication.base.BaseAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by choijinjoo on 2016. 3. 12..
 */
public class ToptrackAdapter extends BaseAdapter<RealmTrack, ToptrackAdapter.ViewHolder> {

    PublishRelay<RealmTrack> trackClickedRelay;

    public ToptrackAdapter(Context context, ArrayList<RealmTrack> data, PublishRelay<RealmTrack> trackClickedRelay) {
        super(context, data);
        this.trackClickedRelay = trackClickedRelay;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.col_toptracks, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final RealmTrack track = mItems.get(position);
        Glide.with(mContext).load(track.getImage().get(1).getText()).fitCenter().into(holder.imageView);
        holder.track.setText(track.getName());
        holder.artist.setText(track.getArtist().getName());

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView imageView;
        @BindView(R.id.track)
        TextView track;
        @BindView(R.id.artist)
        TextView artist;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        @OnClick(R.id.containerTrack) void onTrackClicked(){
            trackClickedRelay.call(mItems.get(getAdapterPosition()));
        }
    }
}
