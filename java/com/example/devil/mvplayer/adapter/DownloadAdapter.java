package com.example.devil.mvplayer.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.devil.mvplayer.Model.Config;
import com.example.devil.mvplayer.Model.LdPreferences;
import com.example.devil.mvplayer.Model.SubCategoryGetter;
import com.example.devil.mvplayer.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by whizkraft on 14/12/17.
 */

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.ViewHolder> {
    List<SubCategoryGetter> playlists;
    Activity activity;
    View view;
    OnRecyclerViewItemClickListener catInterface;
    public DownloadAdapter(Activity activity , List<SubCategoryGetter> playlists, OnRecyclerViewItemClickListener catInterface){
        this.playlists= playlists;
        this.activity = activity;
        this.catInterface = catInterface;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.downloadadapter, null);
        ButterKnife.bind(this,view);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
       final SubCategoryGetter contentDetail = playlists.get(position);

        holder.textSongTitle.setText(contentDetail.getSubname());
        holder.textArtistName.setText(contentDetail.getArtist());
        Glide.with(activity).load(Config.baseurl+contentDetail.getImageurl())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image_title);


       /* if (DownloadPresenter.locallist1.contains(contentDetail.getItemid())){

            holder.image_download.setImageResource(R.drawable.ic_icon_downloadtick);
        }
        else {

            holder.image_download.setImageResource(R.drawable.ic_icon_downwhite);
        }*/
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    LdPreferences.putString(activity,"player","audio");
                    Log.e("postion   ", " " + contentDetail.getSongurl());
                    LdPreferences.putString(activity, "click", "view");
                    LdPreferences.putString(activity,"play_path",contentDetail.getSongid()+".mp4");
                    catInterface.onRecyclerViewItemClicked("",position);



            }
        });
       /* holder.image_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity, "down", Toast.LENGTH_SHORT).show();
                CSPreferences.putString(activity,"click","down");
                CSPreferences.putString(activity,"download_postion", String.valueOf(position));
                catInterface.onCatgoryClick(position);

            }
        });*/


    }
    @Override
    public int getItemCount() {
        return playlists.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_artistname)
        TextView textArtistName;
        @BindView(R.id.txt_songtitle)
        TextView textSongTitle;
        @BindView(R.id.img_title) ImageView image_title;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

}

