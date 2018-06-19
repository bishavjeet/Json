package com.example.devil.mvplayer.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.devil.mvplayer.Model.LdPreferences;
import com.example.devil.mvplayer.Model.PlaylistBean;
import com.example.devil.mvplayer.Model.SubCategoryGetter;
import com.example.devil.mvplayer.R;
import com.example.devil.mvplayer.Utils.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by whizkraft on 2/5/18.
 */

public class GetPlayLIstAdapter extends RecyclerView.Adapter<GetPlayLIstAdapter.ViewHolder> {
    ArrayList<PlaylistBean> playlists;
    Activity activity;
    View view;
    OnRecyclerViewItemClickListener catInterface;
    public GetPlayLIstAdapter(Activity activity , ArrayList<PlaylistBean> playlists, OnRecyclerViewItemClickListener catInterface){
        this.playlists= playlists;
        this.activity = activity;
        this.catInterface = catInterface;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.getplaylistadapter, null);
        ButterKnife.bind(this,view);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final PlaylistBean playlist = playlists.get(position);
        holder.textPlayListName.setText(playlist.getPlaylistname());


            holder.textTotalSongs.setText("Songs (" +playlist.getPlaylistcount()+" )");
       /* if (playlist.getContentIds().getAUDIO().size()<=0){

            holder.textTotalSongs.setText("Songs (0)");
        }
        else {
            holder.textTotalSongs.setText("Songs (" + playlist.getContentIds().getAUDIO().size() + ")");

        }*/
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LdPreferences.putString(activity,"title_text",playlist.getPlaylistname());
                LdPreferences.putString(activity,"playlistid",playlist.getPlaylistid()+"");
                catInterface.onRecyclerViewItemClicked(playlist.getPlaylistid()+"",position);
            }
        });
    }
    @Override
    public int getItemCount() {
        return playlists.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtplayListName)
        TextView textPlayListName;
        @BindView(R.id.txtTotalsongs)
        TextView textTotalSongs;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

}
