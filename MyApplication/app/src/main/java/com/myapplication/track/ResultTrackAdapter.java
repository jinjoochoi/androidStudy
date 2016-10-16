package com.myapplication.track;

/**
 * Created by choijinjoo on 2016. 3. 13..
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.myapplication.track.api.Model.Track2;
import com.myapplication.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by choijinjoo on 2016. 3. 12..
 */
public class ResultTrackAdapter extends RecyclerView.Adapter<ResultTrackAdapter.ViewHolder> {
    protected final Context mContext;
    ArrayList<Track2> mItem;


    public ResultTrackAdapter(Context context) {
        mContext = context;
        mItem = new ArrayList<>();
    }
    public void addItem(Track2 item){
        mItem.add(item);
    }
    public void clearItem() {
        int count = mItem.size();
        if (count > 0) {
            mItem.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.row_resulttracks, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Track2 track = mItem.get(position);
        Glide.with(mContext).load(track.getImage().get(1).getText()).into(holder.imageView);
        holder.track.setText(track.getName());
        holder.artist.setText(track.getArtist());

    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.image)
        ImageView imageView;
        @Bind(R.id.track)
        TextView track;
        @Bind(R.id.artist)
        TextView artist;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

