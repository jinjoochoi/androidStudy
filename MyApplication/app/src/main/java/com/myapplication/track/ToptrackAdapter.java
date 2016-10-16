package com.myapplication.track;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.myapplication.track.api.Model.Track;
import com.myapplication.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by choijinjoo on 2016. 3. 12..
 */
public class ToptrackAdapter extends RecyclerView.Adapter<ToptrackAdapter.ViewHolder> {
    protected final Context mContext;
    ArrayList<Track> mItem;


    public ToptrackAdapter(Context context) {
        mContext = context;
        mItem = new ArrayList<>();
    }
    public void addItem(Track item){
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
        View itemView = inflater.inflate(R.layout.col_toptracks, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Track track = mItem.get(position);
        Glide.with(mContext).load(track.getImage().get(1).getText()).fitCenter().into(holder.imageView);
        holder.track.setText(track.getName());
        holder.artist.setText(track.getArtist().getName());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,NewActivity.class);
                mContext.startActivity(intent);
            }
        });

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
            ButterKnife.bind(this,itemView);
        }
    }
}
